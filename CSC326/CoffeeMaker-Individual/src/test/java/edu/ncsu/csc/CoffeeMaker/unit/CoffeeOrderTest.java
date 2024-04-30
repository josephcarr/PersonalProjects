/**
 *
 */
package edu.ncsu.csc.CoffeeMaker.unit;

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
import edu.ncsu.csc.CoffeeMaker.models.CoffeeOrder;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.CoffeeOrderService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
/**
 * Persistence tests for the Order class
 *
 * @author Madeleine Jenks (mrjenks)
 *
 */
public class CoffeeOrderTest {

    @Autowired
    private CoffeeOrderService service;

    @Autowired
    private RecipeService      rService;

    /** Basic ingredient used for testing */
    private final Ingredient   i = new Ingredient( "Coffee", 50 );

    /** Basic recipe used for testing */
    private final Recipe       r = new Recipe();

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
     * Tests to make sure a CoffeeOrder gets created correctly with the right
     * fields
     */
    @Test
    @Transactional
    public void testCreateNewOrder () {
        // Valid Order
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1983, "Frederick F.", r, false );
        service.save( o1 );
        Assertions.assertEquals( "Frederick F.", o1.getUserName() );
        Assertions.assertEquals( "Black Coffee", o1.getRecipe().getName() );
        Assertions.assertFalse( o1.getFulfilled() );

        // // Cannot have a blank username
        // try {
        // o1 = new CoffeeOrder( (long) 1987, "", r, false );
        // }
        // catch ( final Exception e ) {
        // Assertions.assertTrue( e instanceof IllegalArgumentException );
        // }
        //
        // // Cannot have null Recipe
        // try {
        // o1 = new CoffeeOrder( (long) 1983, "Frederick F.", null, false );
        // }
        // catch ( final Exception e ) {
        // Assertions.assertTrue( e instanceof IllegalArgumentException );
        // }

    }

    /**
     * Tests that an order is correctly marked as fulfilled when called
     */
    @Test
    @Transactional
    public void testOrderFulfilled () {
        final CoffeeOrder o1 = new CoffeeOrder( (long) 1983, "", r, false );
        service.save( o1 );
        Assertions.assertFalse( o1.getFulfilled() );
        o1.setFulfilled( true );
        Assertions.assertTrue( o1.getFulfilled() );
        service.save( o1 );
    }

}
