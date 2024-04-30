package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the Student class
 * @author cbdocke2
 * @author fttolliv
 * @author dccicin
 * @author Shreya Holikatti
 *
 */
class FacultyTest {
	
	/** The first name for faculty*/
	private static final String  FIRST_NAME = "Jane";
	
	/** The last name for faculty*/
	private static final String LAST_NAME = "Doe";
	
	/** The faculty ID */
	private static final String FACULTY_ID = "jadoe";
	
	/** The faculty email */
	private static final String EMAIL = "jadoe@ncsu.edu";
	
	/** The faculty password */
	private static final String PASSWORD = "password";
	
	/** The max credits for faculty*/
	private static final int MAX_COURSES = 3;
	
	
	/**
	 * Tests that hashCode works correctly
	 */
	@Test
	void testHashCode() {
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, 2);
		User s2 = new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, 2);
		User s3 = new Faculty("Different", LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, 1);
		User s4 = new Faculty(FIRST_NAME, "Clark", FACULTY_ID, EMAIL, PASSWORD, 3);
		User s5 = new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, 3);
		User s6 = new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, 3);

		assertEquals(s5.hashCode(), s6.hashCode());
		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s3.hashCode(), s4.hashCode());
	}

	/**
	 * Tests constructing a Faculty member with custom max courses.
	 */
	@Test
	public void testFacultyWithCustomMaxCredits() {
		// Test a valid construction
		Faculty f = assertDoesNotThrow(
				() -> new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, MAX_COURSES),
				"Should not throw exception");

		assertAll("Faculty", 
				() -> assertEquals(FIRST_NAME, f.getFirstName(), "incorrect first name"), 
				() -> assertEquals(LAST_NAME, f.getLastName(), "incorrect last name"),
				() -> assertEquals(FACULTY_ID, f.getId(), "incorrect faculty id"), 
				() -> assertEquals(EMAIL, f.getEmail(), "incorrect email"),
				() -> assertEquals(PASSWORD, f.getPassword(), "incorrect password"),
				() -> assertEquals(MAX_COURSES, f.getMaxCourses(), "incorrect max credits"));
	}
	/**
	 * Tests constructing a Faculty with default Max Credits
	 */
	@Test
	void testFacultyWithDefaultMaxCourses() {
		// Test a valid construction
		User f = assertDoesNotThrow(
				() -> new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, MAX_COURSES),
				"Should not throw exception");

		assertAll("Faculty", 
				() -> assertEquals(FIRST_NAME, f.getFirstName(), "incorrect first name"), 
				() -> assertEquals(LAST_NAME, f.getLastName(), "incorrect last name"),
				() -> assertEquals(FACULTY_ID, f.getId(), "incorrect faculty id"), 
				() -> assertEquals(EMAIL, f.getEmail(), "incorrect email"),
				() -> assertEquals(PASSWORD, f.getPassword(), "incorrect password"));
	}

	/**
	 * Tests that the equals method works for all Faculty fields.
	 */
	@Test
	void testEqualsObject() {
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, MAX_COURSES);
		User s2 = new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, MAX_COURSES);
		User s3 = new Faculty("Different", LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, MAX_COURSES);
		User s4 = new Faculty(FIRST_NAME, "Clark", FACULTY_ID, EMAIL, PASSWORD, MAX_COURSES);
		
		assertTrue(s1.equals(s2));
		assertFalse(s3.equals(s4));
	}
	/**
	 * Tests that toString returns the correct comma-separated value.
	 */
	@Test
	void testToString() {
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, FACULTY_ID, EMAIL, PASSWORD, MAX_COURSES);
		assertEquals("Jane,Doe,jadoe,jadoe@ncsu.edu," + PASSWORD + ",3", s1.toString());
	}
	
	/**
	 * Tests the setEmail method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test 
	void testSetEmail() {
		//Construct a valid faculty member
		User f = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
		Exception e1 = assertThrows(IllegalArgumentException.class,
								() -> f.setEmail(null));
		assertEquals("Invalid email", e1.getMessage()); //Check correct exception message
		assertEquals("email@ncsu.edu", f.getEmail()); //Check that email didn't change
				
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> f.setEmail(""));
		assertEquals("email@ncsu.edu", f.getEmail()); //Check that email didn't change
		assertEquals("Invalid email", e2.getMessage()); 
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
						() -> f.setEmail("emailncsu.edu")); //Check correct exception message
		assertEquals("email@ncsu.edu", f.getEmail()); //Check that email didn't change
		assertEquals("Invalid email", e3.getMessage()); 
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> f.setEmail("email@ncsuedu")); //Check correct exception message
		assertEquals("email@ncsu.edu", f.getEmail()); //Check that email didn't change
		assertEquals("Invalid email", e4.getMessage()); 
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> f.setEmail("my.email@ncsuedu")); //Check correct exception message
		assertEquals("email@ncsu.edu", f.getEmail()); //Check that email didn't change
		assertEquals("Invalid email", e5.getMessage()); 

	}
	
	/**
	 * Tests the setPassword method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test
	void testSetPassword() {
		//Construct a valid Student
		User f = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 1);
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> f.setPassword(null));
		assertEquals("Invalid password", e1.getMessage()); //Check correct exception message
		assertEquals("hashedpassword", f.getPassword()); //Check that password didn't change
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> f.setPassword(""));
		assertEquals("hashedpassword", f.getPassword()); //Check that password didn't change
		assertEquals("Invalid password", e2.getMessage()); 
	}

	
	/**
	 * Tests the setMaxCredits method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test
	void testSetMaxCourses() {
		//Construct a valid Student
				Faculty s = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
				Exception e1 = assertThrows(IllegalArgumentException.class,
								() -> s.setMaxCourses(10));
				assertEquals("Invalid max courses", e1.getMessage()); //Check correct exception message
				assertEquals(2, s.getMaxCourses()); //Check that max credits didn't change
				
				Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> s.setMaxCourses(10));
				assertEquals(2, s.getMaxCourses()); //Check that the max credits didn't change
				assertEquals("Invalid max courses", e2.getMessage()); 
	}

	/**
	 * Tests the setFirstName method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test
	void testSetFirstName() {
		//Construct a valid Student
		User s = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setFirstName(null));
		assertEquals("Invalid first name", e1.getMessage()); //Check correct exception message
		assertEquals("first", s.getFirstName()); //Check that first name didn't change
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setFirstName(""));
		assertEquals("first", s.getFirstName()); //Check that first name didn't change
		assertEquals("Invalid first name", e2.getMessage()); 
	}
	
	/**
	 * Tests the setLastName method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test
	void testSetLastName() {
		//Construct a valid Student
		User s = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", 2);
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage()); //Check correct exception message
		assertEquals("last", s.getLastName()); //Check that last name didn't change
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setLastName(""));
		assertEquals("last", s.getLastName()); //Check that last name didn't change
		assertEquals("Invalid last name", e2.getMessage()); 
	}
	
}