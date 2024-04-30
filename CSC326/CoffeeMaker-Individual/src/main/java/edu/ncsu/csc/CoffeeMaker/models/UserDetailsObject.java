package edu.ncsu.csc.CoffeeMaker.models;

/**
 * Non-persisted model class for sending the details of a user to the frontend.
 *
 */
public class UserDetailsObject {
    /** Username */
    private String username;
    /** First Name */
    private String firstname;
    /** Lastname */
    private String lastname;
    /** ID */
    private Long   id;

    /** Default constructor */
    public UserDetailsObject () {

    }

    /** Constructor to initialize all fields */
    public UserDetailsObject ( final String u, final String f, final String l, final Long id ) {
        this.setFirstname( f );
        this.setUsername( u );
        this.setId( id );
        this.setLastname( l );
    }

    /**
     * @return the username
     */
    public String getUsername () {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername ( final String username ) {
        this.username = username;
    }

    /**
     * @return the firstname
     */
    public String getFirstname () {
        return firstname;
    }

    /**
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname ( final String firstname ) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname () {
        return lastname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public void setLastname ( final String lastname ) {
        this.lastname = lastname;
    }

    /**
     * @return the id
     */
    public Long getId () {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

}
