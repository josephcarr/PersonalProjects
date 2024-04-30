package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APICoffeeTest {

    @Autowired
    private MockMvc          mvc;

    @Autowired
    private RecipeService    service;

    @Autowired
    private InventoryService iService;

    final Ingredient         chocolate = new Ingredient( "chocolate", 15 );
    final Ingredient         coffee    = new Ingredient( "coffee", 15 );
    final Ingredient         milk      = new Ingredient( "milk", 15 );
    final Ingredient         sugar     = new Ingredient( "sugar", 15 );

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        service.deleteAll();
        iService.deleteAll();

        final Inventory ivt = iService.getInventory();

        ivt.addIngredient( chocolate, 50 );
        ivt.addIngredient( coffee, 50 );
        ivt.addIngredient( sugar, 50 );
        ivt.addIngredient( milk, 50 );
        iService.save( ivt );

        final Recipe recipe = new Recipe();
        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );
        recipe.addIngredient( sugar );
        recipe.addIngredient( milk );
        recipe.addIngredient( coffee );
        recipe.addIngredient( chocolate );
        service.save( recipe );
    }

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Customer" } )
    public void testPurchaseBeverage1 () throws Exception {

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( 60 ) ) )
                .andExpect( status().isOk() ).andExpect( jsonPath( "$.message" ).value( 10 ) );

    }

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Customer" } )
    public void testPurchaseBeverage2 () throws Exception {
        /* Insufficient amount paid */

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( 40 ) ) )
                .andExpect( status().is4xxClientError() )
                .andExpect( jsonPath( "$.message" ).value( "Not enough money paid" ) );

    }

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Customer" } )
    public void testPurchaseBeverage3 () throws Exception {
        /* Insufficient inventory */

        final Inventory ivt = iService.getInventory();
        ivt.removeIngredient( coffee );
        iService.save( ivt );

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( 50 ) ) )
                .andExpect( status().is4xxClientError() )
                .andExpect( jsonPath( "$.message" ).value( "Not enough inventory" ) );

    }

    /**
     * Tests for ability to purchase coffee after being edited - jacarras
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Customer" } )
    public void testPurchaseBeverage4 () throws Exception {
        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( 60 ) ) )
                .andExpect( status().isOk() ).andExpect( jsonPath( "$.message" ).value( 10 ) );

        final String newName = "New Coffee";
        Recipe db1 = service.findByName( name );
        db1.setName( newName );

        service.save( db1 );
        assertEquals( 1, service.count() );

        db1 = service.findByName( newName );
        assertEquals( newName, db1.getName() );

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", newName ) ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( 60 ) ) )
                .andExpect( status().isOk() ).andExpect( jsonPath( "$.message" ).value( 10 ) );

    }

    /**
     * Test method to test the behavior of purchasing coffee with incorrect
     * calls to the api. Accounts for the APICoffeeController.makeCoffee()
     * method. Written by rmmayo.
     */

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Customer" } )
    public void testMakeCoffeeErrors () throws Exception {

        // Test for api call with no URL parameter
        mvc.perform( post( String.format( "/api/v1/makecoffee" ) ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( 50 ) ) )
                .andExpect( status().isNotFound() );

        // Test for api call with a coffee that is not in the database
        final String NotRealCoffee = "NotRealCoffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", NotRealCoffee ) ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( 50 ) ) )
                .andExpect( status().is4xxClientError() )
                .andExpect( jsonPath( "$.message" ).value( "No recipe selected" ) );

        // Test for api call with a null URL parameter
        final String nullCoffee = null;

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", nullCoffee ) ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( 50 ) ) )
                .andExpect( status().is4xxClientError() );
    }

}
