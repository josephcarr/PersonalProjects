package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIInventoryTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private InventoryService      service;

    private final Ingredient      coffee = new Ingredient( "coffee", 1 );
    private final Ingredient      milk   = new Ingredient( "milk", 20 );

    /**
     * Sets up the tests.
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        service.deleteAll();
    }

    @Test
    @Transactional
    public void testAddIngredient1 () throws Exception {
        Inventory ivt = service.getInventory();
        Assertions.assertEquals( 0, ivt.getInventory().size() );

        mvc.perform( post( "/api/v1/inventory/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( coffee ) ) );

        ivt = service.getInventory();

        Assertions.assertEquals( 1, ivt.getInventory().size() );
        assertEquals( coffee, ivt.getIngredients().get( 0 ) );

    }

    @Test
    @Transactional
    public void testAddIngredient2 () throws Exception {

        /*
         * Tests a ingredient with a duplicate name to make sure it's rejected
         */

        Inventory ivt = service.getInventory();
        Assertions.assertEquals( 0, ivt.getInventory().size() );

        ivt.addIngredient( coffee, coffee.getAmount() );
        Assertions.assertEquals( 1, ivt.getInventory().size() );
        service.save( ivt );

        mvc.perform( post( "/api/v1/inventory/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( coffee ) ) ).andExpect( status().is4xxClientError() );

        ivt = service.getInventory();

        Assertions.assertEquals( 1, ivt.getInventory().size() );
        assertEquals( coffee, ivt.getIngredients().get( 0 ) );
    }

    @Test
    @Transactional
    public void testGetIngredients () throws Exception {

        final Inventory ivt = service.getInventory();
        Assertions.assertEquals( 0, ivt.getInventory().size() );

        ivt.addIngredient( coffee, coffee.getAmount() );
        ivt.addIngredient( milk, milk.getAmount() );
        Assertions.assertEquals( 2, ivt.getInventory().size() );
        service.save( ivt );

        final String response = mvc.perform( get( "/api/v1/inventory/ingredients" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        assertTrue( response.contains( "coffee" ) );
        assertTrue( response.contains( "milk" ) );
    }

}
