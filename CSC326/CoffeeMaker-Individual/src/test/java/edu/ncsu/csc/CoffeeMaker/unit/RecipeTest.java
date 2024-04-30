package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class RecipeTest {

    @Autowired
    private RecipeService service;

    @BeforeEach
    public void setup () {
        service.deleteAll();
    }

    @Test
    @Transactional
    public void testAddRecipe () {

        final Recipe r1 = new Recipe();
        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );
        r1.addIngredient( new Ingredient( "Coffee", 1 ) );
        r1.addIngredient( new Ingredient( "Milk", 0 ) );
        r1.addIngredient( new Ingredient( "Sugar", 0 ) );
        r1.addIngredient( new Ingredient( "Chocolate", 0 ) );
        service.save( r1 );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( 1 );
        r2.addIngredient( new Ingredient( "Coffee", 1 ) );
        r2.addIngredient( new Ingredient( "Milk", 1 ) );
        r2.addIngredient( new Ingredient( "Sugar", 1 ) );
        r2.addIngredient( new Ingredient( "Chocolate", 1 ) );
        service.save( r2 );

        final List<Recipe> recipes = service.findAll();
        Assertions.assertEquals( 2, recipes.size(),
                "Creating two recipes should result in two recipes in the database" );

        Assertions.assertEquals( r1, recipes.get( 0 ), "The retrieved recipe should match the created one" );
    }

    @Test
    @Transactional
    public void testNoRecipes () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = new Recipe();
        r1.setName( "Tasty Drink" );
        r1.setPrice( 12 );
        r1.addIngredient( new Ingredient( "Coffee", -12 ) );
        r1.addIngredient( new Ingredient( "Milk", 0 ) );
        r1.addIngredient( new Ingredient( "Sugar", 0 ) );
        r1.addIngredient( new Ingredient( "Chocolate", 0 ) );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( 1 );
        r2.addIngredient( new Ingredient( "Coffee", 1 ) );
        r2.addIngredient( new Ingredient( "Milk", 1 ) );
        r2.addIngredient( new Ingredient( "Sugar", 1 ) );
        r2.addIngredient( new Ingredient( "Chocolate", 1 ) );

        final List<Recipe> recipes = List.of( r1, r2 );

        try {
            service.saveAll( recipes );
            Assertions.assertEquals( 0, service.count(),
                    "Trying to save a collection of elements where one is invalid should result in neither getting saved" );
        }
        catch ( final Exception e ) {
            Assertions.assertTrue( e instanceof ConstraintViolationException );
        }

    }

    @Test
    @Transactional
    public void testAddRecipe1 () {

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", 1 ) );
        ingredients.add( new Ingredient( "Sugar", 1 ) );
        ingredients.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( name, 50, ingredients );

        service.save( r1 );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one recipe in the CoffeeMaker" );
        Assertions.assertNotNull( service.findByName( name ) );

    }

    /* Test2 is done via the API for different validation */

    @Test
    @Transactional
    public void testAddRecipe3 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", 1 ) );
        ingredients.add( new Ingredient( "Sugar", 1 ) );
        ingredients.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( name, -50, ingredients );

        try {
            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative price" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe4 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", -3 ) );
        ingredients.add( new Ingredient( "Milk", 1 ) );
        ingredients.add( new Ingredient( "Sugar", 1 ) );
        ingredients.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( name, 50, ingredients );

        try {
            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative amount of coffee" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe5 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", -1 ) );
        ingredients.add( new Ingredient( "Sugar", 1 ) );
        ingredients.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( name, 50, ingredients );

        try {
            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative amount of milk" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe6 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", 1 ) );
        ingredients.add( new Ingredient( "Sugar", -1 ) );
        ingredients.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( name, 50, ingredients );

        try {
            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative amount of sugar" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe7 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", 1 ) );
        ingredients.add( new Ingredient( "Sugar", 1 ) );
        ingredients.add( new Ingredient( "Chocolate", -2 ) );

        final Recipe r1 = createRecipe( name, 50, ingredients );

        try {
            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative amount of chocolate" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe13 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add( new Ingredient( "Coffee", 3 ) );
        ingredients1.add( new Ingredient( "Milk", 1 ) );
        ingredients1.add( new Ingredient( "Sugar", 1 ) );
        ingredients1.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients1 );
        service.save( r1 );

        final List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add( new Ingredient( "Coffee", 3 ) );
        ingredients2.add( new Ingredient( "Milk", 1 ) );
        ingredients2.add( new Ingredient( "Sugar", 1 ) );
        ingredients2.add( new Ingredient( "Chocolate", 2 ) );

        final Recipe r2 = createRecipe( "Mocha", 50, ingredients2 );
        service.save( r2 );

        Assertions.assertEquals( 2, service.count(),
                "Creating two recipes should result in two recipes in the database" );

    }

    @Test
    @Transactional
    public void testAddRecipe14 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add( new Ingredient( "Coffee", 3 ) );
        ingredients1.add( new Ingredient( "Milk", 1 ) );
        ingredients1.add( new Ingredient( "Sugar", 1 ) );
        ingredients1.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients1 );
        service.save( r1 );

        final List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add( new Ingredient( "Coffee", 3 ) );
        ingredients2.add( new Ingredient( "Milk", 1 ) );
        ingredients2.add( new Ingredient( "Sugar", 1 ) );
        ingredients2.add( new Ingredient( "Chocolate", 2 ) );

        final Recipe r2 = createRecipe( "Mocha", 50, ingredients2 );
        service.save( r2 );

        final List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
        ingredients3.add( new Ingredient( "Coffee", 3 ) );
        ingredients3.add( new Ingredient( "Milk", 2 ) );
        ingredients3.add( new Ingredient( "Sugar", 2 ) );
        ingredients3.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r3 = createRecipe( "Latte", 60, ingredients3 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipes in the database" );

    }

    @Test
    @Transactional
    public void testDeleteRecipe1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", 1 ) );
        ingredients.add( new Ingredient( "Sugar", 1 ) );
        ingredients.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients );
        service.save( r1 );

        Assertions.assertEquals( 1, service.count(), "There should be one recipe in the database" );

        service.delete( r1 );
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
    }

    @Test
    @Transactional
    public void testDeleteRecipe2 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add( new Ingredient( "Coffee", 3 ) );
        ingredients1.add( new Ingredient( "Milk", 1 ) );
        ingredients1.add( new Ingredient( "Sugar", 1 ) );
        ingredients1.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients1 );
        service.save( r1 );

        final List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add( new Ingredient( "Coffee", 3 ) );
        ingredients2.add( new Ingredient( "Milk", 1 ) );
        ingredients2.add( new Ingredient( "Sugar", 1 ) );
        ingredients2.add( new Ingredient( "Chocolate", 2 ) );

        final Recipe r2 = createRecipe( "Mocha", 50, ingredients2 );
        service.save( r2 );

        final List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
        ingredients3.add( new Ingredient( "Coffee", 3 ) );
        ingredients3.add( new Ingredient( "Milk", 2 ) );
        ingredients3.add( new Ingredient( "Sugar", 2 ) );
        ingredients3.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r3 = createRecipe( "Latte", 60, ingredients3 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(), "There should be three recipes in the database" );

        service.deleteAll();

        Assertions.assertEquals( 0, service.count(), "`service.deleteAll()` should remove everything" );

    }

    /**
     * Tests that Recipes are kept after one has been deleted - jacarras
     */
    @Test
    @Transactional
    public void testDeleteRecipe3 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add( new Ingredient( "Coffee", 3 ) );
        ingredients1.add( new Ingredient( "Milk", 1 ) );
        ingredients1.add( new Ingredient( "Sugar", 1 ) );
        ingredients1.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients1 );
        service.save( r1 );

        final List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add( new Ingredient( "Coffee", 3 ) );
        ingredients2.add( new Ingredient( "Milk", 1 ) );
        ingredients2.add( new Ingredient( "Sugar", 1 ) );
        ingredients2.add( new Ingredient( "Chocolate", 2 ) );

        final Recipe r2 = createRecipe( "Mocha", 50, ingredients2 );
        service.save( r2 );

        final List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
        ingredients3.add( new Ingredient( "Coffee", 3 ) );
        ingredients3.add( new Ingredient( "Milk", 2 ) );
        ingredients3.add( new Ingredient( "Sugar", 2 ) );
        ingredients3.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r3 = createRecipe( "Latte", 60, ingredients3 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(), "There should be three recipes in the database" );

        service.delete( r2 );

        List<Recipe> dbRecipes = service.findAll();
        Assertions.assertEquals( 2, dbRecipes.size() );

        final Recipe db1 = dbRecipes.get( 0 );
        Recipe db2 = dbRecipes.get( 1 );

        // Checks that the Recipes remaining are the same
        assertEquals( r1.getName(), db1.getName() );
        assertEquals( r1.getPrice(), db1.getPrice() );
        assertEquals( r1.getIngredientByName( "Coffee" ).getAmount(), db1.getIngredientByName( "Coffee" ).getAmount() );
        assertEquals( r1.getIngredientByName( "Sugar" ).getAmount(), db1.getIngredientByName( "Sugar" ).getAmount() );
        assertEquals( r1.getIngredientByName( "Milk" ).getAmount(), db1.getIngredientByName( "Milk" ).getAmount() );
        assertEquals( r1.getIngredientByName( "Chocolate" ).getAmount(),
                db1.getIngredientByName( "Chocolate" ).getAmount() );

        assertEquals( r3.getName(), db2.getName() );
        assertEquals( r3.getPrice(), db2.getPrice() );
        assertEquals( r3.getIngredientByName( "Coffee" ).getAmount(), db2.getIngredientByName( "Coffee" ).getAmount() );
        assertEquals( r3.getIngredientByName( "Sugar" ).getAmount(), db2.getIngredientByName( "Sugar" ).getAmount() );
        assertEquals( r3.getIngredientByName( "Milk" ).getAmount(), db2.getIngredientByName( "Milk" ).getAmount() );
        assertEquals( r3.getIngredientByName( "Chocolate" ).getAmount(),
                db2.getIngredientByName( "Chocolate" ).getAmount() );

        service.delete( r1 );

        dbRecipes = service.findAll();
        Assertions.assertEquals( 1, dbRecipes.size() );

        db2 = dbRecipes.get( 0 );

        // Checks that the last Recipe is the same
        assertEquals( r3.getName(), db2.getName() );
        assertEquals( r3.getPrice(), db2.getPrice() );
        assertEquals( r3.getIngredientByName( "Coffee" ).getAmount(), db2.getIngredientByName( "Coffee" ).getAmount() );
        assertEquals( r3.getIngredientByName( "Sugar" ).getAmount(), db2.getIngredientByName( "Sugar" ).getAmount() );
        assertEquals( r3.getIngredientByName( "Milk" ).getAmount(), db2.getIngredientByName( "Milk" ).getAmount() );
        assertEquals( r3.getIngredientByName( "Chocolate" ).getAmount(),
                db2.getIngredientByName( "Chocolate" ).getAmount() );

        service.delete( r3 );
        dbRecipes = service.findAll();
        Assertions.assertEquals( 0, dbRecipes.size() );

        // Checks that Recipes can't be deleted again
        service.delete( r1 );
        service.delete( r2 );
        service.delete( r3 );
        dbRecipes = service.findAll();
        Assertions.assertEquals( 0, dbRecipes.size() );
    }

    /**
     * Tests that recipes can be deleted after they are changed - jacarras
     */
    @Test
    @Transactional
    public void testDeleteRecipe4 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add( new Ingredient( "Coffee", 3 ) );
        ingredients1.add( new Ingredient( "Milk", 1 ) );
        ingredients1.add( new Ingredient( "Sugar", 1 ) );
        ingredients1.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients1 );
        service.save( r1 );

        final List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add( new Ingredient( "Coffee", 3 ) );
        ingredients2.add( new Ingredient( "Milk", 1 ) );
        ingredients2.add( new Ingredient( "Sugar", 1 ) );
        ingredients2.add( new Ingredient( "Chocolate", 2 ) );

        final Recipe r2 = createRecipe( "Mocha", 50, ingredients2 );
        service.save( r2 );

        Assertions.assertEquals( 2, service.count(), "There should be one recipe in the database" );

        List<Recipe> dbRecipes = service.findAll();
        Assertions.assertEquals( 2, dbRecipes.size() );

        // Changes one of the recipes
        Recipe db1 = dbRecipes.get( 0 );

        db1.setName( "New Coffee" );
        db1.setPrice( 6 );
        db1.setIngredientByName( "Sugar", 10 );
        service.save( db1 );

        Assertions.assertEquals( 2, service.count(), "There should be two recipes in the database" );

        // Deletes original recipe
        service.delete( r1 );
        Assertions.assertEquals( 1, service.count(), "There should be one recipe in the database" );

        dbRecipes = service.findAll();
        Assertions.assertEquals( 1, dbRecipes.size() );

        // Checks that the right Recipe has been deleted
        db1 = dbRecipes.get( 0 );

        assertEquals( r2.getName(), db1.getName() );
        assertEquals( r2.getPrice(), db1.getPrice() );
        assertEquals( r2.getIngredientByName( "Coffee" ).getAmount(), db1.getIngredientByName( "Coffee" ).getAmount() );
        assertEquals( r2.getIngredientByName( "Sugar" ).getAmount(), db1.getIngredientByName( "Sugar" ).getAmount() );
        assertEquals( r2.getIngredientByName( "Milk" ).getAmount(), db1.getIngredientByName( "Milk" ).getAmount() );
        assertEquals( r2.getIngredientByName( "Chocolate" ).getAmount(),
                db1.getIngredientByName( "Chocolate" ).getAmount() );
    }

    /**
     * Tests that deleteAll() deletes edited recipes - jacarras
     */
    @Test
    @Transactional
    public void testDeleteRecipe5 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add( new Ingredient( "Coffee", 3 ) );
        ingredients1.add( new Ingredient( "Milk", 1 ) );
        ingredients1.add( new Ingredient( "Sugar", 1 ) );
        ingredients1.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients1 );
        service.save( r1 );

        final List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add( new Ingredient( "Coffee", 3 ) );
        ingredients2.add( new Ingredient( "Milk", 1 ) );
        ingredients2.add( new Ingredient( "Sugar", 1 ) );
        ingredients2.add( new Ingredient( "Chocolate", 2 ) );

        final Recipe r2 = createRecipe( "Mocha", 50, ingredients2 );
        service.save( r2 );

        final List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
        ingredients3.add( new Ingredient( "Coffee", 3 ) );
        ingredients3.add( new Ingredient( "Milk", 2 ) );
        ingredients3.add( new Ingredient( "Sugar", 2 ) );
        ingredients3.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r3 = createRecipe( "Latte", 60, ingredients3 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(), "There should be three recipes in the database" );

        final List<Recipe> dbRecipes = service.findAll();
        Assertions.assertEquals( 3, dbRecipes.size() );

        final Recipe db1 = dbRecipes.get( 0 );
        final Recipe db2 = dbRecipes.get( 1 );
        final Recipe db3 = dbRecipes.get( 2 );

        db1.setName( "New Coffee" );
        db1.setIngredientByName( "Sugar", 10 );
        service.save( db1 );

        db2.setName( "New Mocha" );
        db1.setIngredientByName( "Chocolate", 30 );
        service.save( db2 );

        db3.setName( "New Latte" );
        db1.setIngredientByName( "Milk", 30 );
        service.save( db3 );

        Assertions.assertEquals( 3, service.count(), "There should be three recipes in the database" );

        service.delete( db1 );
        Assertions.assertEquals( 2, service.count(), "There should be two recipes in the database" );

        service.deleteAll();

        Assertions.assertEquals( 0, service.count(), "`service.deleteAll()` should remove everything" );
    }

    @Test
    @Transactional
    public void testEditRecipe1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", 1 ) );
        ingredients.add( new Ingredient( "Sugar", 1 ) );
        ingredients.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients );
        service.save( r1 );

        r1.setPrice( 70 );

        service.save( r1 );

        final Recipe retrieved = service.findByName( "Coffee" );

        Assertions.assertEquals( 70, (int) retrieved.getPrice() );
        Assertions.assertEquals( 3, (int) retrieved.getIngredientByName( "Coffee" ).getAmount() );
        Assertions.assertEquals( 1, (int) retrieved.getIngredientByName( "Milk" ).getAmount() );
        Assertions.assertEquals( 1, (int) retrieved.getIngredientByName( "Sugar" ).getAmount() );
        Assertions.assertEquals( 0, (int) retrieved.getIngredientByName( "Chocolate" ).getAmount() );

        Assertions.assertEquals( 1, service.count(), "Editing a recipe shouldn't duplicate it" );

    }

    @Test
    @Transactional
    public void testUpdateRecipe () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add( new Ingredient( "Coffee", 3 ) );
        ingredients1.add( new Ingredient( "Milk", 1 ) );
        ingredients1.add( new Ingredient( "Sugar", 1 ) );
        ingredients1.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients1 );
        service.save( r1 );

        final List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add( new Ingredient( "Coffee", 100 ) );
        ingredients2.add( new Ingredient( "Milk", 100 ) );
        ingredients2.add( new Ingredient( "Sugar", 100 ) );
        ingredients2.add( new Ingredient( "Chocolate", 100 ) );

        final Recipe r2 = createRecipe( "New Coffee", 100, ingredients2 );
        r1.updateRecipe( r2 );
        service.save( r1 );

        Assertions.assertEquals( 100, (int) r1.getPrice() );
        Assertions.assertEquals( 100, (int) r1.getIngredientByName( "Coffee" ).getAmount() );
        Assertions.assertEquals( 100, (int) r1.getIngredientByName( "Milk" ).getAmount() );
        Assertions.assertEquals( 100, (int) r1.getIngredientByName( "Sugar" ).getAmount() );
        Assertions.assertEquals( 100, (int) r1.getIngredientByName( "Chocolate" ).getAmount() );

        Assertions.assertEquals( 1, service.count(), "Updating a recipe shouldn't duplicate it" );

    }

    @Test
    @Transactional
    public void testEquals () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", 1 ) );
        ingredients.add( new Ingredient( "Sugar", 1 ) );
        ingredients.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients );

        final Recipe r2 = createRecipe( "Coffee", 50, ingredients );

        final Recipe r3 = createRecipe( "Not Same Coffee", 50, ingredients );

        final Recipe r4 = createRecipe( null, 50, ingredients );

        Assertions.assertTrue( r1.equals( r2 ) );
        Assertions.assertFalse( r1.equals( r3 ) );
        Assertions.assertFalse( r1.equals( null ) );
        Assertions.assertFalse( r4.equals( r1 ) );

    }

    /**
     * Tests toString() of recipe - jacarras
     */
    @Test
    @Transactional
    public void testToString () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final List<Ingredient> ingredients1 = new ArrayList<Ingredient>();
        ingredients1.add( new Ingredient( "Coffee", 3 ) );
        ingredients1.add( new Ingredient( "Milk", 1 ) );
        ingredients1.add( new Ingredient( "Sugar", 1 ) );
        ingredients1.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r1 = createRecipe( "Coffee", 50, ingredients1 );
        service.save( r1 );

        final List<Ingredient> ingredients2 = new ArrayList<Ingredient>();
        ingredients2.add( new Ingredient( "Coffee", 3 ) );
        ingredients2.add( new Ingredient( "Milk", 1 ) );
        ingredients2.add( new Ingredient( "Sugar", 1 ) );
        ingredients2.add( new Ingredient( "Chocolate", 2 ) );

        final Recipe r2 = createRecipe( "Mocha", 50, ingredients2 );
        service.save( r2 );

        final List<Ingredient> ingredients3 = new ArrayList<Ingredient>();
        ingredients3.add( new Ingredient( "Coffee", 3 ) );
        ingredients3.add( new Ingredient( "Milk", 2 ) );
        ingredients3.add( new Ingredient( "Sugar", 2 ) );
        ingredients3.add( new Ingredient( "Chocolate", 0 ) );

        final Recipe r3 = createRecipe( "Latte", 60, ingredients3 );
        service.save( r3 );

        assertEquals( "Coffee", r1.toString() );
        assertEquals( "Mocha", r2.toString() );
        assertEquals( "Latte", r3.toString() );

    }

    /**
     * Tests checkRecipe() of Recipe - jacarras
     */
    @Test
    @Transactional
    public void testCheckRecipe () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = new Recipe();
        r1.addIngredient( new Ingredient( "Coffee", 1 ) );
        service.save( r1 );
        Assertions.assertFalse( r1.checkRecipe() );

        r1.setIngredientByName( "Coffee", 0 );
        r1.addIngredient( new Ingredient( "Milk", 1 ) );
        service.save( r1 );
        Assertions.assertFalse( r1.checkRecipe() );

        r1.setIngredientByName( "Milk", 0 );
        r1.addIngredient( new Ingredient( "Sugar", 1 ) );
        service.save( r1 );
        Assertions.assertFalse( r1.checkRecipe() );

        r1.setIngredientByName( "Sugar", 0 );
        r1.addIngredient( new Ingredient( "Chocolate", 1 ) );
        service.save( r1 );
        Assertions.assertFalse( r1.checkRecipe() );

        r1.setIngredientByName( "Chocolate", 0 );
        service.save( r1 );
        Assertions.assertTrue( r1.checkRecipe() );

    }

    private Recipe createRecipe ( final String name, final Integer price, final List<Ingredient> ingredients ) {
        final Recipe recipe = new Recipe();
        recipe.setName( name );
        recipe.setPrice( price );

        for ( final Ingredient ingredient : ingredients ) {
            recipe.addIngredient( ingredient );
        }

        return recipe;
    }

}
