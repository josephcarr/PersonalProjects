package edu.ncsu.csc.CoffeeMaker.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * CoffeeOrder for the coffee maker. CoffeeOrder is tied to the database using
 * Hibernate libraries. See CoffeeOrderRepository and CoffeeOrderService for the
 * other two pieces used for database support.
 *
 * @author Joseph Carrasco
 */
@Entity
public class CoffeeOrder extends DomainObject {

    /** CoffeeOrder id */
    @Id
    @GeneratedValue
    private Long          id;

    /** CoffeeOrder User's user name */
    private Long          user_id;

    /** CoffeeOrder User's user name */
    private String        userName;

    /** CoffeeOrder recipe */
    @ManyToOne
    private Recipe        recipe;

    /** CoffeeOrder Fulfill Status */
    private boolean       fulfilled;

    /** CoffeeOrder Submit Time */
    private LocalDateTime placement_time;

    /**
     * Creates a default recipe for the coffee maker.
     */
    public CoffeeOrder ( final Long uId, final String uName, final Recipe r, final boolean f ) {
        setUserID( uId );
        setUserName( uName );
        setRecipe( r );
        setDate( LocalDateTime.now() );
        setFulfilled( f );
    }

    public CoffeeOrder () {

    }

    /**
     * Get the ID of the User
     *
     * @return the ID
     */
    @Override
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Recipe (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    @SuppressWarnings ( "unused" )
    private void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * @return the user ID
     */
    public Long getUserID () {
        return user_id;
    }

    /**
     * @param user_id
     *            the user ID to set
     */
    public void setUserID ( final Long user_id ) {
        this.user_id = user_id;
    }

    /**
     * @return the userName
     */
    public String getUserName () {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName ( final String userName ) {
        this.userName = userName;
    }

    /**
     * @return the recipe
     */
    public Recipe getRecipe () {
        return recipe;
    }

    /**
     * @param recipe
     *            the recipe to set
     */
    public void setRecipe ( final Recipe recipe ) {
        this.recipe = recipe;
    }

    /**
     * @return if CoffeeOrder is fulfilled
     */
    public boolean getFulfilled () {
        return fulfilled;
    }

    /**
     * @param fulfilled
     *            set if CoffeeOrder is fulfilled
     */
    public void setFulfilled ( final boolean fulfilled ) {
        this.fulfilled = fulfilled;
    }

    /**
     * @return the time submitted as string
     */
    public String getTimeSubmitted () {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern( "MM-dd-yyyy HH:mm:ss" );
        final String dateString = placement_time.format( format );

        return dateString;
    }

    /**
     * @param currentTime
     *            the date to set
     */
    public void setDate ( final LocalDateTime currentTime ) {
        this.placement_time = currentTime;
    }

}
