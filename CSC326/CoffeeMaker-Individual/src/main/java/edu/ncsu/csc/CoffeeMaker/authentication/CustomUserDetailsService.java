package edu.ncsu.csc.CoffeeMaker.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.repositories.UserRepository;

/**
 * Implementation of a core interface to load user-specific data
 *
 * @author rmmayo
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    /** Repository of users */
    private final UserRepository users;

    /** Constructor */
    public CustomUserDetailsService ( final UserRepository users ) {
        this.users = users;
    }

    /**
     * Retrieves a user from the system by their username and creates a
     * Spring-Security-User object with associated authorities based on
     * User.Role
     */
    @Override
    public UserDetails loadUserByUsername ( final String username ) throws UsernameNotFoundException {

        final User user = users.findByUsername( username );

        if ( user == null ) {
            throw new UsernameNotFoundException( "User not found with username:" + username );
        }

        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add( new SimpleGrantedAuthority( user.getRole().name() ) );

        return new org.springframework.security.core.userdetails.User( user.getUsername(), user.getPassword(),
                authorities );

    }

}
