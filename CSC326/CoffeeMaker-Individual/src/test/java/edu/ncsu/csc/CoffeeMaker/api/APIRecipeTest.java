package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith ( SpringExtension.class )
public class APIRecipeTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RecipeService         service;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        service.deleteAll();
    }

    @Test
    @Transactional
    public void ensureRecipe () throws Exception {
        service.deleteAll();

        final Recipe r = new Recipe();
        r.addIngredient( new Ingredient( "Chocolate", 5 ) );
        r.addIngredient( new Ingredient( "Coffee", 3 ) );
        r.addIngredient( new Ingredient( "Milk", 4 ) );
        r.addIngredient( new Ingredient( "Sugar", 8 ) );
        r.setPrice( 10 );
        r.setName( "Mocha" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    public void testRecipeAPI () throws Exception {

        service.deleteAll();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        recipe.addIngredient( new Ingredient( "Chocolate", 5 ) );
        recipe.addIngredient( new Ingredient( "Coffee", 3 ) );
        recipe.addIngredient( new Ingredient( "Milk", 4 ) );
        recipe.addIngredient( new Ingredient( "Sugar", 8 ) );

        recipe.setPrice( 5 );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipe ) ) );

        Assertions.assertEquals( 1, (int) service.count() );

    }

    @Test
    @Transactional
    public void testAddRecipe2 () throws Exception {

        /* Tests a recipe with a duplicate name to make sure it's rejected */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50, 3, 1, 1, 0 );

        service.save( r1 );

        final Recipe r2 = createRecipe( name, 50, 3, 1, 1, 0 );
        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r2 ) ) ).andExpect( status().is4xxClientError() );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one recipe in the CoffeeMaker" );
    }

    @Test
    @Transactional
    public void testAddRecipe15 () throws Exception {

        /* Tests to make sure that our cap of 3 recipes is enforced */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, 3, 1, 1, 2 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, 3, 2, 2, 0 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipes in the database" );

        final Recipe r4 = createRecipe( "Hot Chocolate", 75, 0, 2, 1, 2 );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r4 ) ) ).andExpect( status().isInsufficientStorage() );

        Assertions.assertEquals( 3, service.count(), "Creating a fourth recipe should not get saved" );
    }

    /**
     * Test for the getRecipe GET api request that maps to the
     * APIRecipeController.getRecipe(). Adds a recipe to the database then
     * retrieves it through the api. Written by rmmayo
     */

    @Test
    @Transactional
    public void testGetRecipe () throws UnsupportedEncodingException, Exception {

        // Add recipe to the database
        final Recipe r1 = createRecipe( "Frapacino", 50, 3, 1, 1, 0 );
        service.save( r1 );

        assertEquals( 1, service.count() );

        // Retrieve the recipe in a GET request to the API.
        final String response = mvc.perform( get( "/api/v1/recipes/Frapacino" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "Frapacino" ) );

    }

    /**
     * Test for the getRecipe GET api request that maps to the
     * APIRecipeController.getRecipe(). Ensures that a recipe that has not been
     * added cannot be retrieved by the API, then verifies the error response.
     * Written by rmmayo
     */

    @Test
    @Transactional
    public void testGetRecipe2 () throws UnsupportedEncodingException, Exception {
        final String response = mvc.perform( get( "/api/v1/recipes/Frapacino" ) ).andExpect( status().isNotFound() )
                .andReturn().getResponse().getContentAsString();

        assertTrue( response.contains( "No recipe found with name Frapacino" ) );
    }

    /**
     * Test for the deleteRecipe DELETE api request that maps to the
     * APIRecipeController.deleteRecipe(). Ensures that a recipe can be added
     * and deleted successfully. Written by rmmayo
     */

    @Test
    @Transactional
    public void testDeleteRecipe () throws UnsupportedEncodingException, Exception {
        final Recipe r1 = createRecipe( "Frapacino", 50, 3, 1, 1, 0 );
        assertEquals( 0, service.count() );
        service.save( r1 );

        final String response = mvc.perform( get( "/api/v1/recipes/Frapacino" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "Frapacino" ) );

        assertEquals( 1, service.count() );

        final String deleteResp = mvc.perform( delete( "/api/v1/recipes/Frapacino" ) ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        assertTrue( deleteResp.contains( "Frapacino was deleted successfully" ) );

        assertEquals( 0, service.count() );

    }

    /**
     * Test for the deleteRecipe DELETE api request that maps to the
     * APIRecipeController.deleteRecipe(). Ensures that a recipe that has not
     * been added cannot be deleted, then verifies the error response. Written
     * by rmmayo
     */

    @Test
    @Transactional
    public void testDeleteRecipe2 () throws UnsupportedEncodingException, Exception {

        assertEquals( 0, service.count() );

        final String response = mvc.perform( delete( "/api/v1/recipes/Frapacino" ) ).andExpect( status().isNotFound() )
                .andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "No recipe found for name Frapacino" ) );

        final Recipe r1 = createRecipe( "Frapacino", 50, 3, 1, 1, 0 );

        service.save( r1 );

        mvc.perform( delete( "/api/v1/recipes/Frapacino" ) ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();

        assertEquals( 0, service.count() );

        final String response2 = mvc.perform( delete( "/api/v1/recipes/Frapacino" ) ).andExpect( status().isNotFound() )
                .andReturn().getResponse().getContentAsString();
        assertTrue( response2.contains( "No recipe found for name Frapacino" ) );
    }

    /**
     * tests that recipes can be deleted after being edited - jacarras
     *
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    @Test
    @Transactional
    public void testDeleteRecipe3 () throws UnsupportedEncodingException, Exception {

        assertEquals( 0, service.count() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );

        service.save( r1 );
        assertEquals( 1, service.count() );

        r1.setName( "New Coffee" );

        service.save( r1 );
        assertEquals( 1, service.count() );

        mvc.perform( delete( "/api/v1/recipes/New Coffee" ) ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();

        assertEquals( 0, service.count() );
    }

    /**
     * Tests the proper flow of editing a recipe through the API. - rmmayo
     *
     */
    @Test
    @Transactional
    public void testEditRecipe1 () throws Exception {

        assertEquals( 0, service.count() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );

        service.save( r1 );
        assertEquals( 1, service.count() );

        final Recipe newRec = this.createRecipe( "Coffee", 32, 8, 3, 3, 1 );

        final String response = mvc
                .perform( put( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( newRec ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "Coffee was updated successfully" ) );

        final Recipe check = service.findByName( "Coffee" );
        assertNotNull( check );
        assertEquals( 32, check.getPrice() );
        assertEquals( 1, check.getIngredientByName( "Chocolate" ).getAmount() );
        assertEquals( 8, check.getIngredientByName( "Coffee" ).getAmount() );
        assertEquals( 3, check.getIngredientByName( "Milk" ).getAmount() );
        assertEquals( 3, check.getIngredientByName( "Sugar" ).getAmount() );

    }

    /**
     * Tests the error flow of attempting to edit a recipe with a name not in
     * the system. - rmmayo
     *
     */
    @Test
    @Transactional
    public void testEditRecipe2 () throws Exception {
        assertEquals( 0, service.count() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );

        service.save( r1 );
        assertEquals( 1, service.count() );

        // New Recipe with different name
        final Recipe newRec = this.createRecipe( "Coffee2", 32, 8, 3, 3, 1 );

        final String response = mvc
                .perform( put( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( newRec ) ) )
                .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();

        assertTrue( response.contains( "No recipe found for name Coffee2" ) );

        // assert no changes for orginal recipe
        final Recipe check = service.findByName( "Coffee" );
        assertNotNull( check );
        assertEquals( 50, check.getPrice() );
        assertEquals( 0, check.getIngredientByName( "Chocolate" ).getAmount() );
        assertEquals( 3, check.getIngredientByName( "Coffee" ).getAmount() );
        assertEquals( 1, check.getIngredientByName( "Milk" ).getAmount() );
        assertEquals( 1, check.getIngredientByName( "Sugar" ).getAmount() );

        // assert that a new recipe was not added
        assertTrue( null == service.findByName( "Coffee2" ) );
        assertEquals( 1, service.count() );
    }

    /**
     * Test for incorrectly editing a recipe through the API. User sends a
     * Recipe with 0s for all ingredient units. - rmmayo
     */
    @Test
    @Transactional
    public void testEditRecipe3 () throws UnsupportedEncodingException, Exception {
        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );

        final Recipe r2 = createRecipe( "Coffee", 0, 0, 0, 0, 0 );
        final String response = mvc
                .perform( put( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( r2 ) ) )
                .andExpect( status().isBadRequest() ).andReturn().getResponse().getContentAsString();

        assertTrue( response.contains(
                "Recipe edited with no ingredients. Recipe must have at least one non-zero value for an ingredient." ) );

        final Recipe check = service.findByName( "Coffee" );
        assertNotNull( check );
        assertEquals( 50, check.getPrice() );
        assertEquals( 0, check.getIngredientByName( "Chocolate" ).getAmount() );
        assertEquals( 3, check.getIngredientByName( "Coffee" ).getAmount() );
        assertEquals( 1, check.getIngredientByName( "Milk" ).getAmount() );
        assertEquals( 1, check.getIngredientByName( "Sugar" ).getAmount() );
    }

    /**
     * Test for incorrectly editing a recipe through the API. User sends a
     * Recipe with no ingredients in its list. - rmmayo
     */
    @Test
    @Transactional
    public void testEditRecipe4 () throws Exception {
        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );

        final Recipe r2 = new Recipe();
        r2.setName( "Coffee" );
        assertTrue( r2.getIngredients().size() == 0 );

        final String response = mvc
                .perform( put( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( r2 ) ) )
                .andExpect( status().isBadRequest() ).andReturn().getResponse().getContentAsString();

        assertTrue( response.contains(
                "Recipe edited with no ingredients. Recipe must have at least one non-zero value for an ingredient." ) );

        final Recipe check = service.findByName( "Coffee" );
        assertNotNull( check );
        assertEquals( 50, check.getPrice() );
        assertEquals( 0, check.getIngredientByName( "Chocolate" ).getAmount() );
        assertEquals( 3, check.getIngredientByName( "Coffee" ).getAmount() );
        assertEquals( 1, check.getIngredientByName( "Milk" ).getAmount() );
        assertEquals( 1, check.getIngredientByName( "Sugar" ).getAmount() );
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
