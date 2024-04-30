package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

/**
 * Creates User.
 * 
 * @author Vineet Krishna
 * @author Faith Tolliver
 * @author Joseph Carrasco
 *
 */
public abstract class User {

	/** The student's first name */
	private String firstName;
	/** The student's last name */
	private String lastName;
	/** The student's ID */
	private String id;
	/** The student's email */
	private String email;
	/** The student's password */
	private String password;

	/**
	 * Constructs user.
	 * 
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param id the id
	 * @param email the email
	 * @param password the password
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}
	
	/**
	 * Creates a hashcode for the current user using all its fields.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password, id);
	}

	/** 
	 * Checks if given user is equal to current user.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(id, other.id);
	}


	/**
	 * Returns the Student's first name
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the Student's Last Name
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the Student's ID
	 * @return the studentId
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the student's ID. If studentId is null or an empty string, 
	 * then an IllegalArgumentException is thrown.
	 * @param id the studentId to set
	 * @throws IllegalArgumentException if studentId is invalid
	 */
	private void setId(String id) {
		if (id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Returns the Student's email
	 * @return the email
	 */
	public String getEmail() {
		
		return email;
	}

	/**
	 * Sets the student's email. If email is null or an empty string, or
	 * if it does not contain '@' or a '.', or if the index of the last ‘.’ character 
	 * in the email string is earlier than the index of the first ‘@’ character, then an
	 * IllegalArgumentException is thrown.
	 * @param email the email to set
	 * @throws IllegalArgumentException if email is invalid
	 */
	public void setEmail(String email) {
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
		
		boolean foundAt = false;
		boolean foundPeriod = false;
		for(int i = 0; i < email.length();  i++) {
			if (email.charAt(i) == '@') {
				foundAt = true;
			}
			if (email.charAt(i) == '.') {
				foundPeriod = true;
			}
			if (email.lastIndexOf('.') < email.indexOf('@')) {
				throw new IllegalArgumentException("Invalid email");
			}
			
		}
		
		if (!foundAt || !foundPeriod) {
			throw new IllegalArgumentException("Invalid email");
			
		}
		this.email = email;
	}

	/**
	 * Returns the Student's password.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the Student's password. If password is null or
	 * if is an empty string, an IllegalArgumentException is thrown.
	 * @param password the password to set
	 * @throws IllegalArgumentException if password is invalid.
	 */
	public void setPassword(String password) {
		if (password == null || "".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Sets the Student's firstName. If firstName is null or
	 * is an empty string, then an IllegalArgumentException is thrown.
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if first name is null or empty.
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the Student's lastName. If lastName is null or 
	 * is and empty string, then an IllegalArgumentException is thrown.
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if last name is invalid or empty.
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

}