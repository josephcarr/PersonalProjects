package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

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
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIIngredientTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private IngredientService     service;

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
    public void ensureIngredient () throws Exception {
        service.deleteAll();

        final Ingredient i = new Ingredient( "Coffee", 5 );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    public void testIngredientAPI () throws Exception {

        service.deleteAll();

        final Ingredient ingredient = new Ingredient( "Coffee", 5 );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( ingredient ) ) );

        Assertions.assertEquals( 1, (int) service.count() );

    }

    @Test
    @Transactional
    public void testAddIngredient () throws Exception {

        /*
         * Tests a ingredient with a duplicate name to make sure it's rejected
         */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );
        final Ingredient i1 = new Ingredient( "Coffee", 5 );

        service.save( i1 );

        final Ingredient i2 = new Ingredient( "Coffee", 10 );
        i2.setId( i1.getId() );
        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i2 ) ) ).andExpect( status().is4xxClientError() );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one ingredient in the CoffeeMaker" );
    }

    /**
     * Test for the getIngredient GET api request that maps to the
     * APIIngredientController.getIngredient(). Adds a ingredient to the
     * database then retrieves it through the api.
     */

    @Test
    @Transactional
    public void testGetIngredient () throws UnsupportedEncodingException, Exception {

        // Add ingredient to the database
        final Ingredient i1 = new Ingredient( "Coffee", 20 );
        service.save( i1 );

        assertEquals( 1, service.count() );

        // Retrieve the ingredient in a GET request to the API.
        final String response = mvc.perform( get( "/api/v1/ingredients/" + i1.getId() ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "Coffee" ) );

    }

    /**
     * Test for the getIngredient GET api request that maps to the
     * APIIngredientController.getIngredient(). Ensures that a ingredient that
     * has not been added cannot be retrieved by the API, then verifies the
     * error response.
     */

    @Test
    @Transactional
    public void testGetIngredient2 () throws UnsupportedEncodingException, Exception {
        final String response = mvc.perform( get( "/api/v1/ingredients/200" ) ).andExpect( status().isNotFound() )
                .andReturn().getResponse().getContentAsString();

        assertTrue( response.contains( "No ingredient found with id 200" ) );
    }

    /**
     * Test for the deleteIngredient DELETE api request that maps to the
     * APIIngredientController.deleteIngredient(). Ensures that a ingredient can
     * be added and deleted successfully.
     */

    @Test
    @Transactional
    public void testDeleteIngredient () throws UnsupportedEncodingException, Exception {
        final Ingredient i1 = new Ingredient( "Coffee", 5 );
        assertEquals( 0, service.count() );
        service.save( i1 );

        final String response = mvc.perform( get( "/api/v1/ingredients/" + i1.getId() ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "Coffee" ) );

        assertEquals( 1, service.count() );

        final String deleteResp = mvc.perform( delete( "/api/v1/ingredients/" + i1.getId() ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        assertTrue( deleteResp.contains( "Ingredient with this id was deleted successfully" ) );
        // assertEquals( "Ingredient with id=" + i1.getId() + " was deleted
        // successfully", deleteResp );

        assertEquals( 0, service.count() );

    }

    /**
     * Test for the deleteIngredient DELETE api request that maps to the
     * APIIngredientController.deleteIngredient(). Ensures that a ingredient
     * that has not been added cannot be deleted, then verifies the error
     * response.
     */

    @Test
    @Transactional
    public void testDeleteIngredient2 () throws UnsupportedEncodingException, Exception {

        assertEquals( 0, service.count() );

        final String response = mvc.perform( delete( "/api/v1/ingredients/200" ) ).andExpect( status().isNotFound() )
                .andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "No ingredient found for id 200" ) );

        final Ingredient i1 = new Ingredient( "Coffee", 5 );

        service.save( i1 );

        mvc.perform( delete( "/api/v1/ingredients/" + i1.getId() ) ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();

        assertEquals( 0, service.count() );

        final String response2 = mvc.perform( delete( "/api/v1/ingredients/" + i1.getId() ) )
                .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();
        assertTrue( response2.contains( "No ingredient found for id " + i1.getId() ) );
    }

    /**
     * Test for the getIngredients GET api request that maps to the
     * APIIngredientController.getIngredients(). Adds three ingredients to the
     * database then retrieves them all through the api.
     */

    @Test
    @Transactional
    public void testGetAllIngredients () throws UnsupportedEncodingException, Exception {

        // Add ingredient to the database
        final Ingredient i1 = new Ingredient( "Coffee", 20 );
        service.save( i1 );

        final Ingredient i2 = new Ingredient( "Milk", 20 );
        service.save( i2 );

        final Ingredient i3 = new Ingredient( "Sugar", 20 );
        service.save( i3 );

        assertEquals( 3, service.count() );

        // Retrieve the ingredient in a GET request to the API.
        final String response = mvc.perform( get( "/api/v1/ingredients" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "Coffee" ) );
        assertTrue( response.contains( "Milk" ) );
        assertTrue( response.contains( "Sugar" ) );

    }

    /**
     * Test for the updateIngredient PUT api request that maps to the
     * APIIngredientController.updateIngredient(). Adds a ingredient to the
     * database, updates it, then retrieves it through the api.
     */

    @Test
    @Transactional
    public void testUpdateIngredient () throws UnsupportedEncodingException, Exception {

        // Add ingredient to the database
        final Ingredient i1 = new Ingredient( "Coffee", 20 );
        service.save( i1 );

        assertEquals( 1, service.count() );

        // Retrieve the ingredient in a GET request to the API.
        final String response = mvc.perform( get( "/api/v1/ingredients/" + i1.getId() ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "20" ) );

        final Ingredient i2 = new Ingredient( "Coffee", 10 );

        mvc.perform( put( "/api/v1/ingredients/" + i1.getId() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i2 ) ) ).andExpect( status().isOk() );

        final String response2 = mvc.perform( get( "/api/v1/ingredients/" + i1.getId() ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response2.contains( "10" ) );

    }

}
