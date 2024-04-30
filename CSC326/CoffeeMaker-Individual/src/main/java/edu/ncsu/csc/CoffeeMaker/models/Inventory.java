package edu.ncsu.csc.CoffeeMaker.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Inventory for the coffee maker. Inventory is tied to the database using
 * Hibernate libraries. See InventoryRepository and InventoryService for the
 * other two pieces used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Inventory extends DomainObject {

    /** id for inventory entry */
    @Id
    @GeneratedValue
    private Long                       id;
    @ElementCollection
    private final Map<String, Integer> inventory;

    /**
     * Empty constructor for Hibernate
     */
    public Inventory () {
        // Intentionally empty so that Hibernate can instantiate
        // Inventory object.

        this.inventory = new HashMap<String, Integer>();

    }

    /**
     * Returns the ID of the entry in the DB
     *
     * @return long
     */
    @Override
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Inventory (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    public Map<String, Integer> getInventory () {
        return this.inventory;
    }

    /**
     * Returns true if there are enough ingredients to make the beverage.
     *
     * @param r
     *            recipe to check if there are enough ingredients
     * @return true if enough ingredients to make the beverage
     */
    public boolean enoughIngredients ( final Recipe r ) {
        boolean isEnough = true;

        for ( final Ingredient i : r.getIngredients() ) {
            if ( !this.inventory.containsKey( i.getName() ) ) {
                return false;
            }
            if ( this.inventory.get( i.getName() ) - i.getAmount() < 0 ) {

                isEnough = false;
            }
        }

        return isEnough;
    }

    /**
     * Removes the ingredients used to make the specified recipe. Assumes that
     * the user has checked that there are enough ingredients to make
     *
     * @param r
     *            recipe to make
     * @return true if recipe is made.
     */
    public boolean useIngredients ( final Recipe r ) {
        if ( enoughIngredients( r ) ) {

            for ( final Ingredient i : r.getIngredients() ) {
                this.inventory.put( i.getName(), this.inventory.get( i.getName() ) - i.getAmount() );
            }

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds an ingredient to the inventory by its name and its amount only if
     * the ingredient does not already exist in its inventory. This method is
     * only intended to add new ingredients, not update existing ones.
     *
     * @param i
     *            an Ingredient object to add to the inventory
     * @return
     *
     */
    public boolean addIngredient ( final Ingredient i, final int amount ) {

        if ( this.inventory.containsKey( i.getName() ) ) {
            this.inventory.put( i.getName(), this.inventory.get( i.getName() ) + amount );
            return true;
        }
        else {
            this.inventory.put( i.getName(), amount );
        }

        return true;
    }

    /**
     * Removes an ingredient object from the inventory.
     *
     * @param i
     *            the Ingredient object to be removed.
     * @return true if the Ingredient is removed, false if there is no matching
     *         ingredient currently in the Inventory.
     */
    public boolean removeIngredient ( final Ingredient i ) {
        if ( !this.inventory.containsKey( i.getName() ) ) {
            return false;
        }
        else {
            this.inventory.remove( i.getName() );
            return true;
        }
    }

    /**
     * Update existing ingredients in this Inventory.
     *
     * @param ingredients
     *            a Map of Ingredients and their Integer amounts.
     * @return true
     */
    public boolean addIngredients ( final Map<String, Integer> ingredients ) {
        for ( final Integer i : ingredients.values() ) {
            if ( i < 0 ) {
                return false;
            }
        }

        for ( final Entry<String, Integer> en : ingredients.entrySet() ) {

            if ( this.inventory.containsKey( en.getKey() ) ) {
                this.inventory.put( en.getKey(), this.inventory.get( en.getKey() ) + en.getValue() );
            }
            else {
                this.inventory.put( en.getKey(), en.getValue() );
            }
        }

        return true;
    }

    /**
     * Gets the amount of an Ingredient stored in the inventory
     *
     * @param in
     *            the Ingredient object to search for
     * @return the integer amount if the ingredient in the inventory
     */
    public Integer getIngredientAmount ( final Ingredient in ) {
        return this.inventory.get( in.getName() );
    }

    /**
     * Currently only returns the parsed integer from the amount parameter
     *
     * @param i
     *            the Ingredient in the inventory
     * @param amount
     *            the positive integer amount as a string
     * @throws IllegalArgumentException
     *             if the amount param is not able to be parsed into a positive
     *             integer
     * @return the parsed postive integer
     */
    public int checkIngredient ( final Ingredient i, final String amount ) throws Exception {
        final Exception err = new IllegalArgumentException( "Units of " + i.getName() + " must be a positive integer" );
        int prs = 0;
        try {
            prs = Integer.parseInt( amount );
        }
        catch ( final Exception e ) {
            throw err;
        }

        if ( prs < 0 ) {
            throw err;
        }

        return prs;

    }

    /**
     * Sets the amount of an Ingredient in the Inventory
     *
     * @param i
     *            the Ingredient object in the Inventory
     * @param amount
     *            the integer to set to the inventory
     */
    public void setIngredient ( final Ingredient i, final int amount ) {
        if ( amount < 0 ) {
            throw new IllegalArgumentException( "Amount must be a positive integer" );
        }

        this.inventory.put( i.getName(), amount );

    }

    /**
     * Gets list of ingredients from map
     *
     * @return list of ingredients
     */
    public List<Ingredient> getIngredients () {
        final List<Ingredient> ingredients = new ArrayList<Ingredient>();

        for ( final Map.Entry<String, Integer> entry : inventory.entrySet() ) {
            final String key = entry.getKey();
            final Integer val = entry.getValue();

            ingredients.add( new Ingredient( key, val ) );
        }

        return ingredients;
    }

    /**
     * Returns a string describing the current contents of the inventory.
     *
     * @return String
     */
    @Override
    public String toString () {
        final StringBuffer buf = new StringBuffer();

        for ( final Entry<String, Integer> i : this.inventory.entrySet() ) {
            buf.append( i.getKey() );
            buf.append( ": " );
            buf.append( i.getValue() );
            buf.append( "\n" );
        }

        return buf.toString();
    }

}
