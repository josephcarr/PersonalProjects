package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.validation.ConstraintViolationException;

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
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class IngredientTest {

    @Autowired
    private IngredientService service;

    @BeforeEach
    public void setup () {
        service.deleteAll();
    }

    @Test
    @Transactional
    public void testAddIngredient () {

        final Ingredient i1 = new Ingredient( "Coffee", 50 );
        service.save( i1 );

        final Ingredient i2 = new Ingredient( "Milk", 50 );
        service.save( i2 );

        final List<Ingredient> ingredient = service.findAll();
        Assertions.assertEquals( 2, ingredient.size(),
                "Creating two recipes should result in two recipes in the database" );

        Assertions.assertEquals( i1, ingredient.get( 0 ), "The retrieved ingredient should match the created one" );
    }

    @Test
    @Transactional
    public void testNoIngredients () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );

        final Ingredient i1 = new Ingredient( "Coffee", -50 );

        final Ingredient i2 = new Ingredient( "Milk", 50 );

        final List<Ingredient> ingredients = List.of( i1, i2 );

        try {
            service.saveAll( ingredients );
            Assertions.assertEquals( 0, service.count(),
                    "Trying to save a collection of elements where one is invalid should result in neither getting saved" );
        }
        catch ( final Exception e ) {
            Assertions.assertTrue( e instanceof ConstraintViolationException );
        }

    }

    @Test
    @Transactional
    public void testAddIngredient1 () {

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );
        final String name = "Coffee";

        final Ingredient i1 = new Ingredient( name, 50 );
        service.save( i1 );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one ingredient in the CoffeeMaker" );
        Assertions.assertNotNull( service.findById( i1.getId() ) );

    }

    /* Test2 is done via the API for different validation */

    @Test
    @Transactional
    public void testAddIngredient2 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );
        final String name = "Coffee";

        final Ingredient i1 = new Ingredient( name, -50 );

        try {
            service.save( i1 );

            Assertions.assertNull( service.findById( i1.getId() ),
                    "An ingredient was able to be created with a negative price" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testDeleteIngredient1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );

        final Ingredient i1 = new Ingredient( "Coffee", 50 );
        service.save( i1 );

        Assertions.assertEquals( 1, service.count(), "There should be one ingredient in the database" );

        service.delete( i1 );
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );
    }

    @Test
    @Transactional
    public void testDeleteIngredient2 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );

        final Ingredient i1 = new Ingredient( "Coffee", 50 );
        service.save( i1 );

        final Ingredient i2 = new Ingredient( "Milk", 50 );
        service.save( i2 );

        final Ingredient i3 = new Ingredient( "Sugar", 50 );
        service.save( i3 );

        Assertions.assertEquals( 3, service.count(), "There should be three ingredients in the database" );

        service.deleteAll();

        Assertions.assertEquals( 0, service.count(), "`service.deleteAll()` should remove everything" );

    }

    /**
     * Tests that Ingredients are kept after one has been deleted - jacarras
     */
    @Test
    @Transactional
    public void testDeleteIngredient3 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );

        final Ingredient i1 = new Ingredient( "Coffee", 50 );
        service.save( i1 );

        final Ingredient i2 = new Ingredient( "Milk", 50 );
        service.save( i2 );

        final Ingredient i3 = new Ingredient( "Sugar", 50 );
        service.save( i3 );

        Assertions.assertEquals( 3, service.count(), "There should be three ingredients in the database" );

        service.delete( i2 );

        List<Ingredient> dbIngredients = service.findAll();
        Assertions.assertEquals( 2, dbIngredients.size() );

        final Ingredient db1 = dbIngredients.get( 0 );
        Ingredient db2 = dbIngredients.get( 1 );

        // Checks that the Recipes remaining are the same
        assertEquals( i1.getName(), db1.getName() );
        assertEquals( i1.getAmount(), db1.getAmount() );

        assertEquals( i3.getName(), db2.getName() );
        assertEquals( i3.getAmount(), db2.getAmount() );

        service.delete( i1 );

        dbIngredients = service.findAll();
        Assertions.assertEquals( 1, dbIngredients.size() );

        db2 = dbIngredients.get( 0 );

        // Checks that the last Recipe is the same
        assertEquals( i3.getName(), db2.getName() );
        assertEquals( i3.getAmount(), db2.getAmount() );

        service.delete( i3 );
        dbIngredients = service.findAll();
        Assertions.assertEquals( 0, dbIngredients.size() );

        // Checks that Recipes can't be deleted again
        service.delete( i1 );
        service.delete( i2 );
        service.delete( i3 );
        dbIngredients = service.findAll();
        Assertions.assertEquals( 0, dbIngredients.size() );
    }

    /**
     * Tests that ingredients can be deleted after they are changed - jacarras
     */
    @Test
    @Transactional
    public void testDeleteIngredient4 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );

        final Ingredient i1 = new Ingredient( "Coffee", 50 );
        service.save( i1 );

        final Ingredient i2 = new Ingredient( "Milk", 50 );
        service.save( i2 );

        Assertions.assertEquals( 2, service.count(), "There should be one ingredient in the database" );

        List<Ingredient> dbIngredients = service.findAll();
        Assertions.assertEquals( 2, dbIngredients.size() );

        // Changes one of the recipes
        Ingredient db1 = dbIngredients.get( 0 );

        db1.setName( "New Coffee" );
        db1.setAmount( 15 );
        service.save( db1 );

        Assertions.assertEquals( 2, service.count(), "There should be two ingredients in the database" );

        // Deletes original recipe
        service.delete( i1 );
        Assertions.assertEquals( 1, service.count(), "There should be one ingredient in the database" );

        dbIngredients = service.findAll();
        Assertions.assertEquals( 1, dbIngredients.size() );

        // Checks that the right Recipe has been deleted
        db1 = dbIngredients.get( 0 );

        assertEquals( i2.getName(), db1.getName() );
        assertEquals( i2.getAmount(), db1.getAmount() );
    }

    @Test
    @Transactional
    public void testEditIngredient1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );

        final Ingredient i1 = new Ingredient( "Coffee", 50 );

        service.save( i1 );

        i1.setAmount( 15 );

        service.save( i1 );

        final Ingredient retrieved = service.findById( i1.getId() );

        Assertions.assertEquals( 15, (int) retrieved.getAmount() );

        Assertions.assertEquals( 1, service.count(), "Editing a ingredient shouldn't duplicate it" );

    }

    @Test
    @Transactional
    public void testEquals () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );

        final Ingredient i1 = new Ingredient( "Coffee", 50 );

        final Ingredient i2 = new Ingredient( "Coffee", 50 );

        final Ingredient i3 = new Ingredient( "Not Coffee", 50 );

        final Ingredient i4 = new Ingredient( null, 50 );

        Assertions.assertTrue( i1.equals( i2 ) );
        Assertions.assertFalse( i1.equals( i3 ) );
        Assertions.assertFalse( i1.equals( null ) );
        Assertions.assertFalse( i4.equals( i1 ) );

    }

    /**
     * Tests toString() of ingredient - jacarras
     */
    @Test
    @Transactional
    public void testToString () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Ingredients in the CoffeeMaker" );

        final Ingredient i1 = new Ingredient( "Coffee", 50 );
        service.save( i1 );

        final Ingredient i2 = new Ingredient( "Milk", 50 );
        service.save( i2 );

        final Ingredient i3 = new Ingredient( "Sugar", 50 );
        service.save( i3 );

        assertEquals( "Ingredient [name=Coffee, amount=50]", i1.toString() );
        assertEquals( "Ingredient [name=Milk, amount=50]", i2.toString() );
        assertEquals( "Ingredient [name=Sugar, amount=50]", i3.toString() );

    }

}
