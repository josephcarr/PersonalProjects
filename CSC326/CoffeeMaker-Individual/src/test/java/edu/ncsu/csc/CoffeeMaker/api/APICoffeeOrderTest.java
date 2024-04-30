package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.CoffeeOrder;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.CoffeeOrderService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith ( SpringExtension.class )
public class APICoffeeOrderTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CoffeeOrderService    service;

    @Autowired
    private RecipeService         rService;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        rService.deleteAll();
        service.deleteAll();
    }

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void ensureCoffeeOrder () throws Exception {
        rService.deleteAll();
        service.deleteAll();

        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o = new CoffeeOrder( (long) 1234, "mflores3", r1, false );

        mvc.perform( post( "/api/v1/orders" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( o ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testCoffeeOrderAPI () throws Exception {

        rService.deleteAll();
        service.deleteAll();

        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o = new CoffeeOrder( (long) 1234, "mflores3", r1, false );
        service.save( o );

        mvc.perform( post( "/api/v1/orders" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( o ) ) );

        Assertions.assertEquals( 1, (int) service.count() );

    }

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testCreateCoffeeOrder () throws Exception {

        /*
         * Tests an CoffeeOrder with a duplicate id to make sure it's rejected
         */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no CoffeeOrders in the CoffeeMaker" );
        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );

        service.save( o1 );
        Assertions.assertEquals( 1, (int) service.count() );
        final Recipe r2 = createRecipe( "Mocha", 5, 3, 1, 1, 0 );
        rService.save( r2 );
        // final CoffeeOrder o2 = new CoffeeOrder( o1.getId(), "user2", r2,
        // false );
        o1.setRecipe( r2 );
        mvc.perform( post( "/api/v1/orders" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( o1 ) ) ).andExpect( status().is4xxClientError() );

        Assertions.assertEquals( 1, service.findAll().size(),
                "There should be only one CoffeeOrder in the CoffeeMaker" );
    }

    /**
     * Test for the getCoffeeOrder GET api request that maps to the
     * APICoffeeOrderController.getCoffeeOrder(). Adds a CoffeeOrder to the
     * database then retrieves it through the api.
     */

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testGetCoffeeOrder () throws UnsupportedEncodingException, Exception {

        // Add CoffeeOrder to the database
        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );
        service.save( o1 );

        assertEquals( 1, service.count() );

        // Retrieve the CoffeeOrder in a GET request to the API.
        final String response = mvc.perform( get( "/api/v1/orders/" + o1.getId() ).with( csrf() ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( o1.getId().toString() ) );

    }

    @Test
    @Transactional
    @WithMockUser ( username = "mflores3", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testGetCoffeeOrderList () throws UnsupportedEncodingException, Exception {

        // Add CoffeeOrder to the database
        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );
        service.save( o1 );

        final CoffeeOrder o2 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );
        service.save( o2 );

        assertEquals( 2, service.count() );

        // Ensure the api route throws a bad request with an unauthenticated
        // user
        final String response = mvc.perform( get( "/api/v1/orders/user" + o1.getId() ).with( csrf() ) ).andDo( print() )
                .andExpect( status().isBadRequest() ).andReturn().getResponse().getContentAsString();

        final List<CoffeeOrder> list = service.findByUserName( "mflores3" );
        assertEquals( 2, list.size() );
        assertEquals( "mflores3", list.get( 0 ).getUserName() );
        assertEquals( 1234, list.get( 0 ).getUserID() );

    }

    /**
     * Test for the getCoffeeOrder GET api request that maps to the
     * APICoffeeOrderController.getCoffeeOrder(). Ensures that a CoffeeOrder
     * that has not been made cannot be retrieved by the API, then verifies the
     * error response.
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager" } )
    public void testGetCoffeeOrder2 () throws UnsupportedEncodingException, Exception {
        final String response = mvc.perform( get( "/api/v1/orders/1234" ).with( csrf() ) )
                .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();

        assertTrue( response.contains( "No order found with id 1234" ) );
    }

    /**
     * Test for the deleteCoffeeOrder DELETE api request that maps to the
     * APICoffeeOrderController.deleteCoffeeOrder(). Ensures that an CoffeeOrder
     * can be added and deleted successfully.
     */

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testDeleteCoffeeOrder () throws UnsupportedEncodingException, Exception {
        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );
        assertEquals( 0, service.count() );
        service.save( o1 );

        final String response = mvc.perform( get( "/api/v1/orders/" + o1.getId() ).with( csrf() ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( o1.getId().toString() ) );

        assertEquals( 1, service.count() );

        final String deleteCoffeeOrder = mvc.perform( delete( "/api/v1/orders/" + o1.getId() ).with( csrf() ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        System.out.println( deleteCoffeeOrder );
        assertTrue( deleteCoffeeOrder.contains( o1.getId() + " was deleted successfully" ) );

        assertEquals( 0, service.count() );

    }

    /**
     * Test for the deleteCoffeeOrder DELETE api request that maps to the
     * APICoffeeOrderController.deleteCoffeeOrder(). Ensures that an CoffeeOrder
     * that has not been made cannot be deleted, then verifies the error
     * response.
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testDeleteCoffeeOrder2 () throws UnsupportedEncodingException, Exception {

        assertEquals( 0, service.count() );

        final String response = mvc.perform( delete( "/api/v1/orders/1234" ).with( csrf() ) )
                .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();
        System.out.println( response );
        assertTrue( response.contains( "No order found for id 1234" ) );

    }

    /**
     * tests that CoffeeOrders can be deleted after being edited
     *
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testDeleteCoffeeOrder3 () throws UnsupportedEncodingException, Exception {

        assertEquals( 0, service.count() );

        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );

        service.save( o1 );
        assertEquals( 1, service.count() );

        o1.setUserName( "mflores2" );

        service.save( o1 );
        assertEquals( 1, service.count() );

        final String resp = mvc.perform( delete( "/api/v1/orders/" + o1.getId() ).with( csrf() ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        System.out.println( resp );
        assertEquals( 0, service.count() );
    }

    /**
     * Tests the proper flow of editing an CoffeeOrder through the API.
     *
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testEditCoffeeOrder1 () throws Exception {

        assertEquals( 0, service.count() );

        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );

        service.save( o1 );
        assertEquals( 1, service.count() );

        final Recipe r2 = createRecipe( "Mocha", 5, 3, 1, 1, 0 );
        rService.save( r2 );
        // final CoffeeOrder newCoffeeOrder = new CoffeeOrder( o1.getId(),
        // "mflores3", r2, false );
        o1.setRecipe( r2 );
        final String response = mvc
                .perform( put( "/api/v1/orders" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( o1 ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( o1.getId() + " was updated successfully" ) );

        final CoffeeOrder check = service.findById( o1.getId() );
        assertNotNull( check );
        assertEquals( 1234, check.getUserID() );
        assertEquals( "mflores3", check.getUserName() );
        assertEquals( "Mocha", check.getRecipe().getName() );
        assertFalse( check.getFulfilled() );

    }

    /**
     * Tests the error flow of attempting to edit a CoffeeOrder with a name not
     * in the system.
     *
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testEditCoffeeOrder2 () throws Exception {
        assertEquals( 0, service.count() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );

        service.save( o1 );
        assertEquals( 1, service.count() );

        // New CoffeeOrder with different id
        final CoffeeOrder newCoffeeOrder = new CoffeeOrder( (long) 12345, "mflores3", r1, false );

        final String response = mvc
                .perform( put( "/api/v1/orders" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( newCoffeeOrder ) ) )
                .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();

        assertTrue( response.contains( "No order found for id " + newCoffeeOrder.getId() ) );

        // assert no changes for orginal CoffeeOrder
        final CoffeeOrder check = service.findById( o1.getId() );
        assertNotNull( check );
        assertEquals( 1234, check.getUserID() );
        assertEquals( "mflores3", check.getUserName() );
        assertEquals( "Coffee", check.getRecipe().getName() );
        assertFalse( check.getFulfilled() );

        // assert that a new recipe was not added
        assertTrue( null == service.findById( newCoffeeOrder.getId() ) );
        assertEquals( 1, service.count() );
    }

    private Recipe createRecipe ( final String name, final Integer price, final Integer coffee, final Integer milk,
            final Integer sugar, final Integer chocolate ) {
        final Recipe recipe = new Recipe();
        recipe.setName( name );
        recipe.setPrice( price );
        recipe.addIngredient( new Ingredient( "Chocolate", chocolate ) );
        recipe.addIngredient( new Ingredient( "Coffee", coffee ) );
        recipe.addIngredient( new Ingredient( "Milk", milk ) );
        recipe.addIngredient( new Ingredient( "Sugar", sugar ) );
        return recipe;
    }
}
