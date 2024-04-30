package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.LoginObject;
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.models.enums.Role;
import edu.ncsu.csc.CoffeeMaker.services.UserService;

/**
 * API Endpoints for user authentication such as logging in and signin out.
 *
 * @author rmmayo
 */

@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class AuthenticationController extends APIController {
    /** Spring UserService to retrieve users */
    @Autowired
    private UserService           users;
    /**
     * The AuthenticationManager of this instance
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint to authenticate users to be used from login page.
     *
     */
    @PostMapping ( BASE_PATH + "signin" )
    public ResponseEntity signin ( @RequestBody final LoginObject user ) {
        try {
            final User fUser = users.findByUsername( user.getUsername() );
            if ( fUser == null ) {
                return new ResponseEntity( errorResponse( "Username not found." ), HttpStatus.NOT_FOUND );
            }

            // final List<GrantedAuthority> authorities = new
            // ArrayList<GrantedAuthority>();
            //
            // authorities.add( new SimpleGrantedAuthority( Role.Customer.name()
            // ) );
            //
            // final Authentication auth = new AnonymousAuthenticationToken(
            // user.getUsername(), user.getUsername(),
            // authorities );
            final Authentication auth = authenticationManager
                    .authenticate( new UsernamePasswordAuthenticationToken( user.getUsername(), user.getPassword() ) );

            SecurityContextHolder.getContext().setAuthentication( auth );

            return new ResponseEntity( successResponse( "User signed-in successfully!" ), HttpStatus.OK );
        }
        catch ( final Exception e ) {
            System.out.println( e.toString() );
            return new ResponseEntity( successResponse( "User signed-in successfully!" ), HttpStatus.CONFLICT );
        }

    }

    /** Endpoint to add a new user to the system, then sign them in. */
    @PostMapping ( BASE_PATH + "signup" )
    public ResponseEntity signup ( @RequestBody final User user ) {

        final User check = users.findByUsername( user.getUsername() );
        if ( check != null ) {
            return new ResponseEntity( errorResponse( "Username already in use." ), HttpStatus.BAD_REQUEST );
        }

        else if ( user.getPassword().isBlank() ) {
            return new ResponseEntity( errorResponse( "Invalid Password." ), HttpStatus.BAD_REQUEST );
        }

        final String hash = this.passwordHash( user.getPassword() );
        final String password = user.getPassword();

        user.setPassword( hash );

        users.save( user );

        final Authentication auth = authenticationManager
                .authenticate( new UsernamePasswordAuthenticationToken( user.getUsername(), password ) );

        SecurityContextHolder.getContext().setAuthentication( auth );

        return new ResponseEntity( successResponse( "User" + user.getUsername() + "created and signed in!" ),
                HttpStatus.OK );
    }

    /**
     * Adds an authentication token for a guest user. Generates a random string
     * as the temporary user ID. This User is NOT persisted and cannot be
     * retrieved via the normal UserDetails paths.
     *
     *
     */

    @PostMapping ( BASE_PATH + "signin/guest" )
    public ResponseEntity guestSignin () {

        try {

            final String username = java.util.UUID.randomUUID().toString();
            final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            authorities.add( new SimpleGrantedAuthority( Role.Customer.name() ) );

            SecurityContextHolder.getContext()
                    .setAuthentication( new UsernamePasswordAuthenticationToken( username, username, authorities ) );

            return new ResponseEntity( successResponse( "Guest login, Username: " + username ), HttpStatus.OK );
        }
        catch ( final Exception e ) {
            System.out.println( e.toString() );
            return new ResponseEntity( errorResponse( "It didnt work" ), HttpStatus.CONFLICT );
        }

    }

    /** Helper function to obtain a hash of a password for a new user. */
    private String passwordHash ( final String password ) {
        final PasswordEncoder encode = new BCryptPasswordEncoder();

        final String digest = encode.encode( password );

        return digest;
    }

}
