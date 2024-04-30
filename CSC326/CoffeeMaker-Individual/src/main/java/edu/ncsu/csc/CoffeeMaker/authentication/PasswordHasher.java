package edu.ncsu.csc.CoffeeMaker.authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Helper class for getting password hashes easily, delete this as it will not
 * be part of final product.
 *
 * @author rmmayo
 *
 */
public class PasswordHasher {
    public static void main ( final String[] args ) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println( passwordEncoder.encode( "password" ) );
    }
}
