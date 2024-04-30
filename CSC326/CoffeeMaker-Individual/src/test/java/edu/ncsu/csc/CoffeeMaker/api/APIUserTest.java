package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.CoffeeOrder;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.models.enums.Role;
import edu.ncsu.csc.CoffeeMaker.services.CoffeeOrderService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;
import edu.ncsu.csc.CoffeeMaker.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith ( SpringExtension.class )
public class APIUserTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService           service;

    @Autowired
    private RecipeService         rService;

    @Autowired
    private CoffeeOrderService    oService;

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
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void ensureUser () throws Exception {
        service.deleteAll();

        final User u1 = new User( "Ali", "Flores", "mflores3", "password", Role.Customer );

        mvc.perform( post( "/api/v1/users" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u1 ) ) ).andExpect( status().isOk() );

        final User u2 = new User( "Ramon", "Mayo", "rmmayo", "password", Role.Worker );

        mvc.perform( post( "/api/v1/users" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u2 ) ) ).andExpect( status().isOk() );

        final User u3 = new User( "Nathan", "Turpin", "naturpin", "password", Role.Manager );

        mvc.perform( post( "/api/v1/users" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u3 ) ) ).andExpect( status().isOk() );
    }

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testUserAPI () throws Exception {

        service.deleteAll();

        final User u1 = new User( "Ali", "Flores", "mflores3", "password", Role.Customer );

        mvc.perform( post( "/api/v1/users" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u1 ) ) );

        Assertions.assertEquals( 1, (int) service.count() );

    }

    /**
     * Test for the getUser GET api request that maps to the
     * APIUserController.getUser(). Adds a user to the database then retrieves
     * it through the api.
     */

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testGetUser () throws UnsupportedEncodingException, Exception {

        // Add user to the database
        final User u1 = new User( "Ali", "Flores", "mflores3", "password", Role.Customer );

        service.save( u1 );

        assertEquals( 1, service.count() );

        // Retrieve the user in a GET request to the API.
        final String response = mvc.perform( get( "/api/v1/users/" + u1.getId() ).with( csrf() ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "Ali" ) );

    }

    /**
     * Test for the getUser GET api request that maps to the
     * APIUserController.getUser(). Ensures that a user that has not been made
     * cannot be retrieved by the API, then verifies the error response.
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testGetUser2 () throws UnsupportedEncodingException, Exception {
        final String response = mvc.perform( get( "/api/v1/users/123" ).with( csrf() ) )
                .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();

        assertTrue( response.contains( "No user found with id 123" ) );
    }

    /**
     * Test for the deleteUser DELETE api request that maps to the
     * APIUserController.deleteUser(). Ensures that a user can be added and
     * deleted successfully.
     */

    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testDeleteUser () throws UnsupportedEncodingException, Exception {

        final User u1 = new User( "Ali", "Flores", "mflores3", "password", Role.Customer );

        assertEquals( 0, service.count() );

        service.save( u1 );

        final String response = mvc.perform( get( "/api/v1/users/" + u1.getId() ).with( csrf() ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "Ali" ) );

        assertEquals( 1, service.count() );

        final String deleteOrder = mvc.perform( delete( "/api/v1/users/" + u1.getId() ).with( csrf() ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        assertTrue( deleteOrder.contains( u1.getId() + " was deleted successfully" ) );

        assertEquals( 0, service.count() );

    }

    /**
     * Test for the deleteUser DELETE api request that maps to the
     * APIUserController.deleteUser(). Ensures that a user that has not been
     * made cannot be deleted, then verifies the error response.
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testDeleteUser2 () throws UnsupportedEncodingException, Exception {

        assertEquals( 0, service.count() );

        final String response = mvc.perform( delete( "/api/v1/users/123" ).with( csrf() ) )
                .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( "No user found for id 123" ) );

    }

    /**
     * tests that users can be deleted after being edited
     *
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testDeleteUser3 () throws UnsupportedEncodingException, Exception {

        assertEquals( 0, service.count() );

        final User u1 = new User( "Ali", "Flores", "mflores3", "password", Role.Customer );

        service.save( u1 );
        assertEquals( 1, service.count() );

        u1.setPassword( "password1" );

        service.save( u1 );
        assertEquals( 1, service.count() );

        mvc.perform( delete( "/api/v1/users/" + u1.getId() ).with( csrf() ) ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();

        assertEquals( 0, service.count() );
    }

    /**
     * Tests if each order that is created corresponds to the correct user.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testUserAddOrders () throws Exception {

        final User u1 = new User( "Ali", "Flores", "mflores3", "password", Role.Customer );
        service.save( u1 );

        final User u2 = new User( "A", "F", "mflores2", "password", Role.Customer );
        service.save( u2 );

        mvc.perform( post( "/api/v1/users" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u1 ) ) );

        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );
        oService.save( o1 );
        o1.setRecipe( r1 );

        final Recipe r2 = createRecipe( "Mocha", 5, 3, 1, 1, 0 );
        rService.save( r2 );
        final CoffeeOrder o2 = new CoffeeOrder( (long) 4321, "mflores2", r2, false );
        oService.save( o2 );
        o2.setRecipe( r2 );

        mvc.perform( post( "/api/v1/orders" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( o1 ) ) ).andExpect( status().is4xxClientError() );

        mvc.perform( post( "/api/v1/users/" + u1.getId() + "/order" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( o1 ) ) );

        mvc.perform( post( "/api/v1/users/" + u2.getId() + "/order" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( o2 ) ) );

        final User check = service.findById( u1.getId() );
        // assertEquals( 1234, check.getOrders().get( 0 ).getUserID() );
        assertEquals( "mflores3", u1.getOrders().get( 0 ).getUserName() );
        assertEquals( "Coffee", u1.getOrders().get( 0 ).getRecipe().getName() );
        assertFalse( u1.getOrders().get( 0 ).getFulfilled() );

        // assertEquals( 4321, u2.getOrders().get( 0 ).getUserID() );
        assertEquals( "mflores2", u2.getOrders().get( 0 ).getUserName() );
        assertEquals( "Mocha", u2.getOrders().get( 0 ).getRecipe().getName() );
        assertFalse( u2.getOrders().get( 0 ).getFulfilled() );

    }

    /**
     * Tests if an order associated with the correct user is successfully edited
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "admin", password = "password", roles = { "Manager", "Worker", "Customer" } )
    public void testUserEditOrders () throws Exception {

        final User u1 = new User( "Ali", "Flores", "mflores3", "password", Role.Customer );
        service.save( u1 );

        final User u2 = new User( "A", "F", "mflores2", "password", Role.Customer );
        service.save( u2 );

        mvc.perform( post( "/api/v1/users" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u1 ) ) );

        final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
        rService.save( r1 );
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1, false );
        oService.save( o1 );
        o1.setRecipe( r1 );

        final Recipe r2 = createRecipe( "Mocha", 5, 3, 1, 1, 0 );
        rService.save( r2 );
        final CoffeeOrder o2 = new CoffeeOrder( (long) 4321, "mflores2", r2, false );
        oService.save( o2 );
        o2.setRecipe( r2 );

        mvc.perform( post( "/api/v1/orders" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( o1 ) ) ).andExpect( status().is4xxClientError() );

        mvc.perform( post( "/api/v1/users/" + u1.getId() + "/order" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( o1 ) ) );

        mvc.perform( post( "/api/v1/users/" + u2.getId() + "/order" ).with( csrf() )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( o2 ) ) );

        final Recipe r3 = createRecipe( "Latte", 5, 3, 1, 1, 0 );
        rService.save( r3 );
        o2.setRecipe( r3 );

        final String response = mvc
                .perform( put( "/api/v1/orders" ).with( csrf() ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( o2 ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        assertTrue( response.contains( o2.getId() + " was updated successfully" ) );

        // Make sure mflores3's order hasn't changed
        // assertEquals( 1234, u1.getOrders().get( 0 ).getUserID() );
        assertEquals( "mflores3", u1.getOrders().get( 0 ).getUserName() );
        assertEquals( "Coffee", u1.getOrders().get( 0 ).getRecipe().getName() );
        assertFalse( u1.getOrders().get( 0 ).getFulfilled() );

        // Check if mflores2's order was successfully edited
        // assertEquals( 4321, u2.getOrders().get( 0 ).getUserID() );
        assertEquals( "mflores2", u2.getOrders().get( 0 ).getUserName() );
        assertEquals( "Latte", u2.getOrders().get( 0 ).getRecipe().getName() );
        assertFalse( u2.getOrders().get( 0 ).getFulfilled() );

    }

    // /**
    // * Tests if an order is successfully deleted from the correct user
    // *
    // * @throws Exception
    // */
    // @Test
    // @Transactional
    // @WithMockUser ( username = "admin", password = "password", roles = {
    // "Manager", "Worker", "Customer" } )
    // public void testUserDeleteOrders () throws Exception {
    //
    // final User u1 = new User( "Ali", "Flores", "mflores3", "password",
    // Role.Customer );
    // service.save( u1 );
    //
    // final User u2 = new User( "A", "F", "mflores2", "password", Role.Customer
    // );
    // service.save( u2 );
    //
    // mvc.perform( post( "/api/v1/users" ).with( csrf() ).contentType(
    // MediaType.APPLICATION_JSON )
    // .content( TestUtils.asJsonString( u1 ) ) );
    //
    // final Recipe r1 = createRecipe( "Coffee", 5, 3, 1, 1, 0 );
    // rService.save( r1 );
    // final CoffeeOrder o1 = new CoffeeOrder( (long) 1234, "mflores3", r1,
    // false );
    // oService.save( o1 );
    // o1.setRecipe( r1 );
    //
    // final Recipe r2 = createRecipe( "Mocha", 5, 3, 1, 1, 0 );
    // rService.save( r2 );
    // final CoffeeOrder o2 = new CoffeeOrder( (long) 4321, "mflores2", r2,
    // false );
    // oService.save( o2 );
    // o2.setRecipe( r2 );
    //
    // mvc.perform( post( "/api/v1/orders" ).with( csrf() ).contentType(
    // MediaType.APPLICATION_JSON )
    // .content( TestUtils.asJsonString( o1 ) ) ).andExpect(
    // status().is4xxClientError() );
    //
    // mvc.perform( post( "/api/v1/users/" + u1.getId() + "/order" ).with(
    // csrf() )
    // .contentType( MediaType.APPLICATION_JSON ).content(
    // TestUtils.asJsonString( o1 ) ) );
    //
    // mvc.perform( post( "/api/v1/users/" + u2.getId() + "/order" ).with(
    // csrf() )
    // .contentType( MediaType.APPLICATION_JSON ).content(
    // TestUtils.asJsonString( o2 ) ) );
    //
    // mvc.perform( delete( "/api/v1/orders/" + o2.getId() ).with( csrf() )
    // ).andExpect( status().isOk() ).andReturn()
    // .getResponse().getContentAsString();
    //
    // // Make sure mflores3's order still exists
    // // assertEquals( 1234, u1.getOrders().get( 0 ).getUserID() );
    // assertEquals( "mflores3", u1.getOrders().get( 0 ).getUserName() );
    // assertEquals( "Coffee", u1.getOrders().get( 0 ).getRecipe().getName() );
    // assertFalse( u1.getOrders().get( 0 ).getFulfilled() );
    //
    // // Check if mflores2's order was successfully deleted
    // assertEquals( 0, u2.getOrders().size() );
    //
    // }

    // /**
    // * Tests the proper flow of editing a user through the API.
    // *
    // */
    // @Test
    // @Transactional
    // @WithMockUser ( username = "admin", password = "password", roles = {
    // "Manager", "Worker", "Customer" } )
    // public void testEditUser1 () throws Exception {
    //
    // assertEquals( 0, service.count() );
    //
    // final User u1 = new User( "Ali", "Flores", "mflores3", "password",
    // Role.Customer );
    //
    // service.save( u1 );
    // assertEquals( 1, service.count() );
    //
    // final User newUser = new User( "Ali", "Flores", "mflores3", "password",
    // Role.Worker );
    //
    // final String response = mvc
    // .perform( put( "/api/v1/users" ).with( csrf() ).contentType(
    // MediaType.APPLICATION_JSON )
    // .content( TestUtils.asJsonString( newUser ) ) )
    // .andExpect( status().isOk()
    // ).andReturn().getResponse().getContentAsString();
    // assertTrue( response.contains( u1.getId() + " was updated successfully" )
    // );
    //
    // final User check = service.findById( newUser.getId() );
    // assertNotNull( check );
    // assertEquals( "Ali", check.getFirstName() );
    // assertEquals( "Flores", check.getLastName() );
    // assertEquals( "mflores3", check.getUserName() );
    // assertEquals( "password", check.getPassword() );
    // }

    // /**
    // * Tests the error flow of attempting to edit a user with a name not in
    // the
    // * system.
    // *
    // */
    // @Test
    // @Transactional
    // @WithMockUser ( username = "admin", password = "password", roles = {
    // "Manager" } )
    // public void testEditUser2 () throws Exception {
    // assertEquals( 0, service.count() );
    //
    // final User u1 = new User( "Ali", "Flores", "mflores3", "password",
    // Role.Customer );
    //
    // service.save( u1 );
    // assertEquals( 1, service.count() );
    //
    // // New user with different name
    // final User newUser = new User( "Ali2", "Flores", "mflores3", "password",
    // Role.Customer );
    //
    // final String response = mvc
    // .perform( put( "/api/v1/users" ).with( csrf() ).contentType(
    // MediaType.APPLICATION_JSON )
    // .content( TestUtils.asJsonString( newUser ) ) )
    // .andExpect( status().isNotFound()
    // ).andReturn().getResponse().getContentAsString();
    //
    // assertTrue( response.contains( "No user found for id " + newUser.getId()
    // ) );
    //
    // // assert no changes for orginal order
    // final User check = service.findById( u1.getId() );
    // assertNotNull( check );
    // assertEquals( "Ali", check.getFirstName() );
    // assertEquals( "Flores", check.getLastName() );
    // assertEquals( "mflores3", check.getUserName() );
    // assertEquals( "password", check.getPassword() );
    //
    // // assert that a new recipe was not added
    // assertTrue( null == service.findById( newUser.getId() ) );
    // assertEquals( 1, service.count() );
    // }

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
