package edu.ncsu.csc.CoffeeMaker.unit;

import java.util.HashMap;
import java.util.Map;

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
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class InventoryTest {

    @Autowired
    private InventoryService inventoryService;

    private final Ingredient chocolate = new Ingredient( "chocolate", 10 );
    private final Ingredient coffee    = new Ingredient( "coffee", 1 );
    private final Ingredient milk      = new Ingredient( "milk", 20 );
    private final Ingredient sugar     = new Ingredient( "sugar", 5 );

    @BeforeEach
    public void setup () {
        final Inventory ivt = inventoryService.getInventory();
        ivt.addIngredient( milk, 200 );
        ivt.addIngredient( chocolate, 200 );
        ivt.addIngredient( coffee, 200 );
        ivt.addIngredient( sugar, 200 );

        inventoryService.save( ivt );
    }

    @Test
    @Transactional
    public void testConsumeInventory () {
        final Inventory i = inventoryService.getInventory();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        recipe.addIngredient( chocolate );
        recipe.addIngredient( coffee );
        recipe.addIngredient( milk );
        recipe.addIngredient( sugar );

        recipe.setPrice( 5 );

        i.useIngredients( recipe );

        /*
         * Make sure that all of the inventory fields are now properly updated
         */

        Assertions.assertEquals( 190, (int) i.getIngredientAmount( chocolate ) );
        Assertions.assertEquals( 180, (int) i.getIngredientAmount( milk ) );
        Assertions.assertEquals( 195, (int) i.getIngredientAmount( sugar ) );
        Assertions.assertEquals( 199, (int) i.getIngredientAmount( coffee ) );
    }

    @Test
    @Transactional
    public void testAddInventory1 () {
        Inventory ivt = inventoryService.getInventory();

        ivt.addIngredient( coffee, 5 );
        ivt.addIngredient( milk, 3 );
        ivt.addIngredient( sugar, 7 );
        ivt.addIngredient( chocolate, 2 );

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        Assertions.assertEquals( 205, (int) ivt.getIngredientAmount( coffee ),
                "Adding to the inventory should result in correctly-updated values for coffee" );
        Assertions.assertEquals( 203, (int) ivt.getIngredientAmount( milk ),
                "Adding to the inventory should result in correctly-updated values for milk" );
        Assertions.assertEquals( 207, (int) ivt.getIngredientAmount( sugar ),
                "Adding to the inventory should result in correctly-updated values sugar" );
        Assertions.assertEquals( 202, (int) ivt.getIngredientAmount( chocolate ),
                "Adding to the inventory should result in correctly-updated values chocolate" );

    }

    @Test
    @Transactional
    public void testAddInventory2 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            // ivt.addIngredients( -5, 3, 7, 2 );
            ivt.addIngredient( coffee, -5 );
            ivt.addIngredient( milk, 3 );
            ivt.addIngredient( sugar, 7 );
            ivt.addIngredient( chocolate, 2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( coffee ),
                    "Adding to the inventory should result in correctly-updated values for coffee" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( milk ),
                    "Adding to the inventory should result in correctly-updated values for milk" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( sugar ),
                    "Adding to the inventory should result in correctly-updated values sugar" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( chocolate ),
                    "Adding to the inventory should result in correctly-updated values chocolate" );
        }
    }

    @Test
    @Transactional
    public void testAddInventory3 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            // ivt.addIngredients( 5, -3, 7, 2 );
            ivt.addIngredient( coffee, 5 );
            ivt.addIngredient( milk, -3 );
            ivt.addIngredient( sugar, 7 );
            ivt.addIngredient( chocolate, 2 );

        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( coffee ),
                    "Adding to the inventory should result in correctly-updated values for coffee" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( milk ),
                    "Adding to the inventory should result in correctly-updated values for milk" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( sugar ),
                    "Adding to the inventory should result in correctly-updated values sugar" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( chocolate ),
                    "Adding to the inventory should result in correctly-updated values chocolate" );

        }

    }

    @Test
    @Transactional
    public void testAddInventory4 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            // ivt.addIngredients( 5, 3, -7, 2 );
            ivt.addIngredient( coffee, 5 );
            ivt.addIngredient( milk, 3 );
            ivt.addIngredient( sugar, -7 );
            ivt.addIngredient( chocolate, 2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( coffee ),
                    "Adding to the inventory should result in correctly-updated values for coffee" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( milk ),
                    "Adding to the inventory should result in correctly-updated values for milk" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( sugar ),
                    "Adding to the inventory should result in correctly-updated values sugar" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( chocolate ),
                    "Adding to the inventory should result in correctly-updated values chocolate" );

        }

    }

    @Test
    @Transactional
    public void testAddInventory5 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            // ivt.addIngredients( 5, 3, 7, -2 );
            ivt.addIngredient( coffee, 5 );
            ivt.addIngredient( milk, 3 );
            ivt.addIngredient( sugar, 7 );
            ivt.addIngredient( chocolate, -2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( coffee ),
                    "Adding to the inventory should result in correctly-updated values for coffee" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( milk ),
                    "Adding to the inventory should result in correctly-updated values for milk" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( sugar ),
                    "Adding to the inventory should result in correctly-updated values sugar" );
            Assertions.assertEquals( 500, (int) ivt.getIngredientAmount( chocolate ),
                    "Adding to the inventory should result in correctly-updated values chocolate" );
        }

    }

    @Test
    @Transactional
    public void testParseAmounts () throws Exception {
        final Inventory ivt = inventoryService.getInventory();

        // Testing checkChocolate()
        Assertions.assertEquals( 10, ivt.checkIngredient( chocolate, "10" ), "This should parse as a valid input" );

        final IllegalArgumentException e1 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredient( chocolate, "abc" );
        }, "This should parse as an invalid input (not a number)" );
        Assertions.assertEquals( "Units of chocolate must be a positive integer", e1.getMessage() );

        // final IllegalArgumentException e2 = Assertions.assertThrows(
        // IllegalArgumentException.class, () -> {
        // ivt.checkChocolate( "0" );
        // }, "This should parse as an invalid input (not a number)" );
        // Assertions.assertEquals( "Units of chocolate must be a positive
        // integer", e2.getMessage() );

        final IllegalArgumentException e3 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredient( chocolate, "-10" );
        }, "This should parse as an invalid input (not a number)" );
        Assertions.assertEquals( "Units of chocolate must be a positive integer", e3.getMessage() );

        // Testing checkCoffee()
        Assertions.assertEquals( 10, ivt.checkIngredient( coffee, "10" ), "This should parse as a valid input" );

        final IllegalArgumentException e4 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredient( coffee, "abc" );
        }, "This should parse as an invalid input (not a number)" );
        Assertions.assertEquals( "Units of coffee must be a positive integer", e4.getMessage() );

        // final IllegalArgumentException e5 = Assertions.assertThrows(
        // IllegalArgumentException.class, () -> {
        // ivt.checkCoffee( "0" );
        // }, "This should parse as an invalid input (not a number)" );
        // Assertions.assertEquals( "Units of coffee must be a positive
        // integer", e5.getMessage() );

        final IllegalArgumentException e6 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredient( coffee, "-10" );
        }, "This should parse as an invalid input (not a number)" );
        Assertions.assertEquals( "Units of coffee must be a positive integer", e6.getMessage() );

        // Testing checkMilk()
        Assertions.assertEquals( 10, ivt.checkIngredient( milk, "10" ), "This should parse as a valid input" );

        final IllegalArgumentException e7 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredient( milk, "abc" );
        }, "This should parse as an invalid input (not a number)" );
        Assertions.assertEquals( "Units of milk must be a positive integer", e7.getMessage() );

        // final IllegalArgumentException e8 = Assertions.assertThrows(
        // IllegalArgumentException.class, () -> {
        // ivt.checkMilk( "0" );
        // }, "This should parse as an invalid input (not a number)" );
        // Assertions.assertEquals( "Units of milk must be a positive integer",
        // e8.getMessage() );

        final IllegalArgumentException e9 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredient( milk, "-10" );
        }, "This should parse as an invalid input (not a number)" );
        Assertions.assertEquals( "Units of milk must be a positive integer", e9.getMessage() );

        // Testing checkSugar()
        Assertions.assertEquals( 10, ivt.checkIngredient( sugar, "10" ), "This should parse as a valid input" );

        final IllegalArgumentException e10 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredient( sugar, "abc" );
        }, "This should parse as an invalid input (not a number)" );
        Assertions.assertEquals( "Units of sugar must be a positive integer", e10.getMessage() );

        // final IllegalArgumentException e11 = Assertions.assertThrows(
        // IllegalArgumentException.class, () -> {
        // ivt.checkSugar( "0" );
        // }, "This should parse as an invalid input (not a number)" );
        // Assertions.assertEquals( "Units of sugar must be a positive integer",
        // e11.getMessage() );

        final IllegalArgumentException e12 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredient( sugar, "-10" );
        }, "This should parse as an invalid input (not a number)" );
        Assertions.assertEquals( "Units of sugar must be a positive integer", e12.getMessage() );

    }

    @Test
    @Transactional
    public void testToString () {
        final Inventory ivt = inventoryService.getInventory();

        ivt.addIngredient( coffee, 5 );
        ivt.addIngredient( milk, 3 );
        ivt.addIngredient( sugar, 7 );
        ivt.addIngredient( chocolate, 2 );

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        Assertions.assertEquals( "coffee: 205\nmilk: 203\nsugar: 207\nchocolate: 202\n", ivt.toString() );

    }

    @Test
    @Transactional
    public void testEnoughIngredients () {

        /* Create Recipe */
        final Recipe recipe = new Recipe();
        recipe.setName( "Coffee" );

        recipe.addIngredient( chocolate );
        recipe.addIngredient( coffee );
        recipe.addIngredient( milk );
        recipe.addIngredient( sugar );

        recipe.setIngredientByName( "chocolate", 1 );
        recipe.setIngredientByName( "milk", 1 );
        recipe.setIngredientByName( "sugar", 1 );
        recipe.setIngredientByName( "coffee", 1 );
        recipe.setPrice( 1 );

        /* Try an inventory with enough ingredients */
        final Inventory ivt = inventoryService.getInventory();
        ivt.setIngredient( chocolate, 500 );
        ivt.setIngredient( coffee, 500 );
        ivt.setIngredient( milk, 500 );
        ivt.setIngredient( sugar, 500 );

        inventoryService.save( ivt );
        Assertions.assertTrue( ivt.enoughIngredients( recipe ) );

        /* Try an inventory with no coffee */
        ivt.setIngredient( coffee, 0 );
        inventoryService.save( ivt );
        Assertions.assertFalse( ivt.enoughIngredients( recipe ) );

        /* Try an inventory with no milk */
        ivt.setIngredient( coffee, 500 );
        ivt.setIngredient( milk, 0 );
        inventoryService.save( ivt );
        Assertions.assertFalse( ivt.enoughIngredients( recipe ) );

        /* Try an inventory with no sugar */
        ivt.setIngredient( milk, 500 );
        ivt.setIngredient( sugar, 0 );
        inventoryService.save( ivt );
        Assertions.assertFalse( ivt.enoughIngredients( recipe ) );

        /* Try an inventory with no chocolate */
        ivt.setIngredient( sugar, 500 );
        ivt.setIngredient( chocolate, 0 );
        inventoryService.save( ivt );
        Assertions.assertFalse( ivt.enoughIngredients( recipe ) );

    }

    @Test
    @Transactional
    public void testRemoveIngredient () {
        final Inventory ivt = inventoryService.getInventory();

        Assertions.assertNotNull( ivt.getIngredientAmount( chocolate ) );
        Assertions.assertTrue( ivt.removeIngredient( chocolate ) );
        Assertions.assertNull( ivt.getIngredientAmount( chocolate ) );
        Assertions.assertFalse( ivt.removeIngredient( chocolate ) );

    }

    @Test
    @Transactional
    public void testAddIngredients () {
        final Inventory ivt = inventoryService.getInventory();

        final Map<String, Integer> map = new HashMap<>();

        map.put( chocolate.getName(), 10 );
        map.put( milk.getName(), 10 );
        map.put( sugar.getName(), 10 );
        map.put( coffee.getName(), 10 );

        ivt.removeIngredient( milk );
        ivt.removeIngredient( chocolate );
        ivt.removeIngredient( coffee );
        ivt.removeIngredient( sugar );

        Assertions.assertTrue( ivt.getInventory().isEmpty() );

        Assertions.assertTrue( ivt.addIngredients( map ) );

        Assertions.assertFalse( ivt.getInventory().isEmpty() );

        Assertions.assertEquals( 10, ivt.getIngredientAmount( chocolate ) );
        Assertions.assertEquals( 10, ivt.getIngredientAmount( milk ) );
        Assertions.assertEquals( 10, ivt.getIngredientAmount( sugar ) );
        Assertions.assertEquals( 10, ivt.getIngredientAmount( coffee ) );

        ivt.addIngredients( map );

        Assertions.assertEquals( 20, ivt.getIngredientAmount( chocolate ) );
        Assertions.assertEquals( 20, ivt.getIngredientAmount( milk ) );
        Assertions.assertEquals( 20, ivt.getIngredientAmount( sugar ) );
        Assertions.assertEquals( 20, ivt.getIngredientAmount( coffee ) );
    }
}
