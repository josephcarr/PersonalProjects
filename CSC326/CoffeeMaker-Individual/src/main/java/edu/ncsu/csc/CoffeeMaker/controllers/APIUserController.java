package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.CoffeeOrder;
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.models.UserDetailsObject;
import edu.ncsu.csc.CoffeeMaker.services.UserService;

/**
 * This is the controller that holds the REST endpoints that handle CRUD
 * operations for Users.
 *
 * Spring will automatically convert all of the ResponseEntity and List results
 * to JSON
 *
 * @author Nathan Turpin (naturpin)
 *
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APIUserController extends APIController {

    /**
     * UserService object, to be autowired in by Spring to allow for
     * manipulating the User model
     */
    @Autowired
    private UserService service;

    /**
     * REST API method to provide GET access to all users in the system
     *
     * @return JSON representation of all users
     */
    @GetMapping ( BASE_PATH + "/users" )
    public List<User> getUsers () {
        return service.findAll();
    }

    /**
     * REST API method to provide GET access to a specific user, as indicated by
     * the path variable provided (the name of the user desired)
     *
     * @param id
     *            user id
     * @return response to the request
     */
    @GetMapping ( BASE_PATH + "/users/{id}" )
    public ResponseEntity getUser ( @PathVariable ( "id" ) final long id ) {
        final User user = service.findById( id );
        return null == user ? new ResponseEntity( errorResponse( "No user found with id " + id ), HttpStatus.NOT_FOUND )
                : new ResponseEntity( user, HttpStatus.OK );
    }

    @GetMapping ( BASE_PATH + "/users/current" )
    public ResponseEntity getAuthUser ( final Authentication user ) {
        long id = 0;
        String first = null;
        String last = null;
        final User u = service.findByUsername( user.getName() );

        if ( u != null ) {
            first = u.getFirstName();
            last = u.getLastName();
            id = u.getId();
        }

        return new ResponseEntity( new UserDetailsObject( user.getName(), first, last, id ), HttpStatus.OK );

    }

    /**
     * REST API method to provide POST access to the User model. This is used to
     * create a new User by automatically converting the JSON RequestBody
     * provided to a User object. Invalid JSON will fail.
     *
     * @param user
     *            The valid User to be saved.
     * @return ResponseEntity indicating success if the User could be saved, or
     *         an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/users" )
    public ResponseEntity createUser ( @RequestBody final User user ) {
        if ( null != service.findByUsername( user.getUsername() ) ) {
            return new ResponseEntity(
                    errorResponse( "User with the username " + user.getUsername() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        else {
            user.setPassword( this.passwordHash( user.getPassword() ) );

            service.save( user );
            return new ResponseEntity( successResponse( user.getUsername() + " successfully created" ), HttpStatus.OK );
        }

    }

    /**
     * REST API method to allow deleting a User,, by making a DELETE request to
     * the API endpoint and indicating the user to delete (as a path variable)
     *
     * @param id
     *            The id of the User to delete
     * @return Success if the user could be deleted; an error if the user does
     *         not exist
     */
    @DeleteMapping ( BASE_PATH + "/users/{id}" )
    public ResponseEntity deleteUser ( @PathVariable final long id ) {
        final User user = service.findById( id );
        if ( null == user ) {
            return new ResponseEntity( errorResponse( "No user found for id " + id ), HttpStatus.NOT_FOUND );
        }
        service.delete( user );

        return new ResponseEntity( successResponse( id + " was deleted successfully" ), HttpStatus.OK );
    }

    @PostMapping ( BASE_PATH + "/users/{id}/order" )
    public ResponseEntity addOrder ( @PathVariable final long id, @RequestBody final CoffeeOrder o ) {
        final User user = service.findById( id );
        user.addOrder( o );
        service.save( user );
        return new ResponseEntity( successResponse( o.getId() + " successfully created" ), HttpStatus.OK );
    }

    @GetMapping ( BASE_PATH + "/users/{id}/orders" )
    public List<CoffeeOrder> getOrders ( @PathVariable final long id ) {
        final User user = service.findById( id );

        return user.getOrders();
    }

    // /**
    // * REST API method to allow editing a User.
    // * Converts the JSON RequestBody into a new User and searches for an
    // * existing User with the same name, then updates it if found.
    // *
    // * If a matching User by name is not found, a 404 response is sent.
    // *
    // */
    // @PutMapping ( BASE_PATH + "/users" )
    // public ResponseEntity editUser ( @RequestBody final User r ) {
    // final User user = service.findById( r.getId()) );
    // if ( null == user ) {
    // return new ResponseEntity( errorResponse( "No user found for id " +
    // r.getId() ), HttpStatus.NOT_FOUND );
    // }
    //
    // user.updateUser( r );
    //
    // service.save( user );
    //
    // return new ResponseEntity( successResponse( r.getId() + " was updated
    // successfully" ), HttpStatus.OK );
    // }

    /** Helper function to obtain a hash of a password for a new user. */
    private String passwordHash ( final String password ) {
        final PasswordEncoder encode = new BCryptPasswordEncoder();

        final String digest = encode.encode( password );

        return digest;
    }
}
