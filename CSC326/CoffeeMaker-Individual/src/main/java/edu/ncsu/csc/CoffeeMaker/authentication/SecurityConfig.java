package edu.ncsu.csc.CoffeeMaker.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import edu.ncsu.csc.CoffeeMaker.models.enums.Role;

/**
 * Spring configuration class to secure API endpoints.
 *
 * @author rmmayo
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /** Constant for api paths */
    final protected String           BASE_PATH = "/api/v1/";
    /** UserdetailsService required by the framework */
    @SuppressWarnings ( "unused" )
    private final UserDetailsService service;

    /** Basic constructor */
    public SecurityConfig ( final UserDetailsService userDetailsService ) {
        this.service = userDetailsService;

    }

    /**
     * Define a password encoder to be used with authentication. The default is
     * the BCrypt hashing technique.
     */
    @Bean
    public static PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    /** Gets the Spring AuthenticationManager instance */
    @Bean
    public AuthenticationManager authenticationManager ( final AuthenticationConfiguration config ) throws Exception {

        return config.getAuthenticationManager();

    }

    /**
     * Endpoint access configurations based on authenticated user roles. Access
     * to unauthorized pages rediects to the login page.
     */
    @Override
    protected void configure ( final HttpSecurity http ) throws Exception {
        http.authorizeRequests()
                /* GET REQUESTS */
                .antMatchers( HttpMethod.GET, BASE_PATH + "orders/", BASE_PATH + "ingredients/{id}/",
                        BASE_PATH + "inventory/", BASE_PATH + "/inventory/ingredients/", BASE_PATH + "/users/{id}/" )
                .hasAnyRole( Role.Manager.name(), Role.Worker.name() )

                .antMatchers( HttpMethod.GET, BASE_PATH + "/orders/{id}/", BASE_PATH + "/ingredients/",
                        BASE_PATH + "/recipes/{name}/", BASE_PATH + "/ingredients/identifier/", BASE_PATH + "/recipes/",
                        BASE_PATH + "orders/user/" )
                .authenticated()

                /* POST REQUESTS */
                .antMatchers( HttpMethod.POST, BASE_PATH + "/ingredients/", BASE_PATH + "/inventory/ingredients/",
                        BASE_PATH + "/recipes/" )
                .hasAnyRole( Role.Manager.name(), Role.Worker.name() )
                .antMatchers( HttpMethod.POST, BASE_PATH + "/users/" ).hasAnyRole( Role.Manager.name() )
                .antMatchers( HttpMethod.POST, BASE_PATH + "/orders/" ).authenticated()

                /* DELETE REQUESTS */
                .antMatchers( HttpMethod.DELETE, BASE_PATH + "/orders/", BASE_PATH + "/ingredients/{id}/",
                        BASE_PATH + "/recipes/{name}/", BASE_PATH + "/users/{id}/" )
                .hasAnyRole( Role.Manager.name(), Role.Worker.name() )

                /* PUT REQUESTS */
                .antMatchers( HttpMethod.PUT, BASE_PATH + "/orders/", BASE_PATH + "/ingredients/{id}/",
                        BASE_PATH + "/inventory/", BASE_PATH + "/recipes/" )
                .hasAnyRole( Role.Manager.name(), Role.Worker.name() )

                /* WebPage Requests */
                .antMatchers( HttpMethod.GET, "/recipe/", "/recipe.html/", "/addrecipe/", "/addrecipe.html/",
                        "/deleterecipe/", "/deleterecipe.html/", "/editrecipe/", "/editrecipe.html/", "/inventory/",
                        "/inventory.html/", "/addingredient/", "/addingredient.html/", "/worker/", "/worker.html/" )
                .hasAnyRole( Role.Manager.name(), Role.Worker.name() )

                .antMatchers( HttpMethod.GET, "/makecoffee/", "/makecoffee.html/" ).authenticated()

                .antMatchers( HttpMethod.GET, "/customer/", "/customer.html/" )
                .hasAnyRole( Role.Manager.name(), Role.Customer.name() )

                .antMatchers( HttpMethod.GET, "/manager/", "/manger.html/", "/adduser/", "/adduser.html/" )
                .hasRole( Role.Manager.name() )

                .antMatchers( HttpMethod.GET, "/signup" ).anonymous()
                .antMatchers( HttpMethod.GET, "/icons/coffee-background.jpg" ).anonymous()

                /*
                 * Authentication configuration for login and logout, and
                 * default request rules
                 */

                .antMatchers( HttpMethod.POST, BASE_PATH + "signin/" ).anonymous().antMatchers( BASE_PATH + "signup/" )
                .anonymous().antMatchers( HttpMethod.POST, BASE_PATH + "/signin/guest" ).anonymous()
                .antMatchers( "/login" ).anonymous().antMatchers( "/css/**" ).permitAll().anyRequest().authenticated()
                .and().formLogin().loginPage( "/login" ).defaultSuccessUrl( "/" ).and().csrf()
                .csrfTokenRepository( CookieCsrfTokenRepository.withHttpOnlyFalse() ).and().logout()
                .logoutUrl( BASE_PATH + "logout" ).invalidateHttpSession( true ).logoutSuccessUrl( "/login" );

    }
    //
    // @Override
    // public void configure ( final AuthenticationManagerBuilder auth ) {
    // auth.authenticationProvider( AnonAuthProvider );
    // ;
    //
    // }
}
