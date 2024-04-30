package edu.ncsu.csc.CoffeeMaker.models;

/**
 * User Login model to be passed for login requests. Not made for persistence.
 *
 * @author rmmayo
 */

public class LoginObject {
    /**
     * User's username
     */
    private String username;
    /** User's password */
    private String password;

    /** Basic constructor */
    public LoginObject ( final String u, final String p ) {
        this.username = u;
        this.password = p;
    }

    public LoginObject () {

    }

    /**
     * @return the username
     */
    public String getUsername () {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword () {
        return password;
    }

}
