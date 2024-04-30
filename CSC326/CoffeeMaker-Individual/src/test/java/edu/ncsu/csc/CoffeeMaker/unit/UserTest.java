/**
 *
 */
package edu.ncsu.csc.CoffeeMaker.unit;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.models.enums.Role;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;
import edu.ncsu.csc.CoffeeMaker.services.UserService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
/**
 * Persistence tests for the User class
 *
 * @author Madeleine Jenks (mrjenks)
 *
 */
public class UserTest {

    @Autowired
    private UserService      service;

    @Autowired
    private RecipeService    rService;

    @Enumerated ( EnumType.STRING )
    private static Role      role;

    /** Basic recipe used for testing */
    private final Recipe     r = new Recipe();
    /** Basic ingredient used for testing */
    private final Ingredient i = new Ingredient( "Coffee", 50 );

    @BeforeEach
    public void setup () {
        service.deleteAll();
        rService.deleteAll();
        r.addIngredient( i );
        r.setName( "Black Coffee" );
        r.setPrice( 1 );
        rService.save( r );
    }

    /**
     * Makes sure that a User is being created correctly with all of the correct
     * fields, specifically differentiating between a Manager and Customer
     */
    @Test
    @Transactional
    public void testCreateUser () {
        // Testing a user with Manager role
        final User u = new User( "Frederick", "Fazbear", "ffaz87", "password", Role.Manager );

        Assertions.assertEquals( "Frederick", u.getFirstName() );
        Assertions.assertEquals( "Fazbear", u.getLastName() );
        Assertions.assertEquals( "ffaz87", u.getUsername() );
        Assertions.assertEquals( "password", u.getPassword() );
        Assertions.assertEquals( Role.Manager, u.getRole() );

        // Testing a user with Customer role
        final User u2 = new User( "Michael", "Afton", "prplguy", "passwd", Role.Customer );
        Assertions.assertEquals( "Michael", u2.getFirstName() );
        Assertions.assertEquals( "Afton", u2.getLastName() );
        Assertions.assertEquals( "prplguy", u2.getUsername() );
        Assertions.assertEquals( "passwd", u2.getPassword() );
        Assertions.assertEquals( Role.Customer, u2.getRole() );
    }

    // @Test
    // @Transactional
    // public void testUserOrders () {
    // final User u = new User( "Frederick", "Fazbear", "ffaz87", "password",
    // Role.Manager );
    //
    // Assertions.assertEquals( 0, u.getOrders().size() );
    //
    // u.addOrder( r );
    //
    // Assertions.assertEquals( 1, u.getOrders().size() );
    // Assertions.assertEquals( u.getId(), u.getOrders().get( 0 ).getUserID() );
    // }

}
