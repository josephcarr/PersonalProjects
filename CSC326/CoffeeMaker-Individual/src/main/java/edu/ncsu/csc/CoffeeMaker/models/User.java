package edu.ncsu.csc.CoffeeMaker.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import edu.ncsu.csc.CoffeeMaker.models.enums.Role;

/**
 * User for the coffee maker. User is tied to the database using Hibernate
 * libraries. See UserRepository and UserService for the other two pieces used
 * for database support.
 *
 * @author Joseph Carrasco
 */
@Entity
public class User extends DomainObject {

    /** User id */
    @Id
    @GeneratedValue
    private Long                    id;

    /** User First Name */
    private String                  firstName;

    /** User Last Name */
    private String                  lastName;

    /** Username */
    private String                  username;

    /** User Password */
    private String                  password;

    /** User Password */
    @Enumerated ( EnumType.STRING )
    private Role                    role;

    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private final List<CoffeeOrder> orders;

    public User () {
        orders = new ArrayList<CoffeeOrder>();
    }

    /**
     * Creates a default recipe for the coffee maker.
     */
    public User ( final String fName, final String lName, final String uName, final String pwd, final Role r ) {
        setFirstName( fName );
        setLastName( lName );
        setUserName( uName );
        setPassword( pwd );
        setRole( r );
        orders = new ArrayList<CoffeeOrder>();
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
     * @return the firstName
     */
    public String getFirstName () {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName ( final String firstName ) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName () {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName ( final String lastName ) {
        this.lastName = lastName;
    }

    /**
     * @return the userName
     */
    public String getUsername () {
        return username;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName ( final String userName ) {
        this.username = userName;
    }

    /**
     * @return the password
     */
    public String getPassword () {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword ( final String password ) {
        this.password = password;
    }

    /**
     * @return the role
     */
    public Role getRole () {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole ( final Role r ) {
        role = r;
    }

    /**
     * @return the orders
     */
    public List<CoffeeOrder> getOrders () {
        return orders;
    }

    // /**
    // * @param role
    // * the role to set
    // */
    // public void setOrders ( final List<CoffeeOrder> o ) {
    // this.orders = o;
    // }

    public boolean addOrder ( final CoffeeOrder order ) {
        if ( order.getRecipe() == null ) {
            return false;
        }

        orders.add( order );
        return true;
    }

}
