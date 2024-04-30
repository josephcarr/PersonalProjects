package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the Student class
 * @author cbdocke2
 * @author fttolliv
 * @author dccicin
 *
 */
class StudentTest {
	
	/** The first name for student*/
	private static final String  FIRST_NAME = "Caleb";
	
	/** The last name for student*/
	private static final String LAST_NAME = "Smith";
	
	/** The student ID */
	private static final String STUDENT_ID = "dccicin";
	
	/** The student email */
	private static final String EMAIL = "caleb_smith@ncsu.edu";
	
	/** The student password */
	private static final String PASSWORD = "password";
	
	/** The max credits for student*/
	private static final int MAX_CREDITS = 16;
	
	
	/**
	 * Tests that hashCode works correctly
	 */
	@Test
	void testHashCode() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD);
		User s2 = new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD);
		User s3 = new Student("Different", LAST_NAME, STUDENT_ID, EMAIL, PASSWORD);
		User s4 = new Student(FIRST_NAME, "Clark", STUDENT_ID, EMAIL, PASSWORD);
		User s5 = new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD, 10);
		User s6 = new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD, 10);

		assertEquals(s5.hashCode(), s6.hashCode());
		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s3.hashCode(), s4.hashCode());
	}

	/**
	 * Tests constructing a Student with custom max credits.
	 */
	@Test
	public void testStudentWithCustomMaxCredits() {
		// Test a valid construction
		Student s = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD, MAX_CREDITS),
				"Should not throw exception");

		assertAll("Student", 
				() -> assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"), 
				() -> assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"),
				() -> assertEquals(STUDENT_ID, s.getId(), "incorrect student id"), 
				() -> assertEquals(EMAIL, s.getEmail(), "incorrect email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "incorrect password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "incorrect max credits"));
	}
	/**
	 * Tests constructing a Student with default Max Credits
	 */
	@Test
	void testStudentWithDefaultMaxCredits() {
		// Test a valid construction
		User s = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD),
				"Should not throw exception");

		assertAll("Student", 
				() -> assertEquals(FIRST_NAME, s.getFirstName(), "incorrect first name"), 
				() -> assertEquals(LAST_NAME, s.getLastName(), "incorrect last name"),
				() -> assertEquals(STUDENT_ID, s.getId(), "incorrect student id"), 
				() -> assertEquals(EMAIL, s.getEmail(), "incorrect email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "incorrect password"));
	}

	/**
	 * Tests that the equals method works for all Student fields.
	 */
	@Test
	void testEqualsObject() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD);
		User s2 = new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD);
		User s3 = new Student("Different", LAST_NAME, STUDENT_ID, EMAIL, PASSWORD);
		User s4 = new Student(FIRST_NAME, "Clark", STUDENT_ID, EMAIL, PASSWORD);
		
		assertTrue(s1.equals(s2));
		assertFalse(s3.equals(s4));
	}
	/**
	 * Tests that toString returns the correct comma-separated value.
	 */
	@Test
	void testToString() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, STUDENT_ID, EMAIL, PASSWORD);
		assertEquals("Caleb,Smith,dccicin,caleb_smith@ncsu.edu," + PASSWORD + ",18", s1.toString());
	}
	
	/**
	 * Tests the setEmail method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test 
	void testSetEmail() {
		//Construct a valid Student
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Exception e1 = assertThrows(IllegalArgumentException.class,
								() -> s.setEmail(null));
		assertEquals("Invalid email", e1.getMessage()); //Check correct exception message
		assertEquals("email@ncsu.edu", s.getEmail()); //Check that email didn't change
				
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> s.setEmail(""));
		assertEquals("email@ncsu.edu", s.getEmail()); //Check that email didn't change
		assertEquals("Invalid email", e2.getMessage()); 
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
						() -> s.setEmail("emailncsu.edu")); //Check correct exception message
		assertEquals("email@ncsu.edu", s.getEmail()); //Check that email didn't change
		assertEquals("Invalid email", e3.getMessage()); 
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail("email@ncsuedu")); //Check correct exception message
		assertEquals("email@ncsu.edu", s.getEmail()); //Check that email didn't change
		assertEquals("Invalid email", e4.getMessage()); 
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> s.setEmail("my.email@ncsuedu")); //Check correct exception message
		assertEquals("email@ncsu.edu", s.getEmail()); //Check that email didn't change
		assertEquals("Invalid email", e5.getMessage()); 

	}
	
	/**
	 * Tests the setPassword method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test
	void testSetPassword() {
		//Construct a valid Student
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setPassword(null));
		assertEquals("Invalid password", e1.getMessage()); //Check correct exception message
		assertEquals("hashedpassword", s.getPassword()); //Check that password didn't change
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setPassword(""));
		assertEquals("hashedpassword", s.getPassword()); //Check that password didn't change
		assertEquals("Invalid password", e2.getMessage()); 
	}

	
	/**
	 * Tests the setMaxCredits method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test
	void testSetMaxCredits() {
		//Construct a valid Student
				Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
				Exception e1 = assertThrows(IllegalArgumentException.class,
								() -> s.setMaxCredits(2));
				assertEquals("Invalid max credits", e1.getMessage()); //Check correct exception message
				assertEquals(16, s.getMaxCredits()); //Check that max credits didn't change
				
				Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> s.setMaxCredits(19));
				assertEquals(16, s.getMaxCredits()); //Check that the max credits didn't change
				assertEquals("Invalid max credits", e2.getMessage()); 
	}

	/**
	 * Tests the setFirstName method with valid and invalid values, where the invalid
	 * values should throw IllegalArgumentExceptions
	 */
	@Test
	void testSetFirstName() {
		//Construct a valid Student
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
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
		User s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage()); //Check correct exception message
		assertEquals("last", s.getLastName()); //Check that last name didn't change
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setLastName(""));
		assertEquals("last", s.getLastName()); //Check that last name didn't change
		assertEquals("Invalid last name", e2.getMessage()); 
	}
	/**
	 * Tests the compareTo method in Student.
	 */
	@Test
	void testCompareTo() {
		//Constructs a valid Student
		Student s = new Student("Adam", "Barry", "abBarry", "email@ncsu.edu", "hashedpassword");
		//Constructs an equivalent Student, except for the id
		Student a = new Student("Adam", "Barry", "abBarry1", "email@ncsu.edu", "hashedpassword");
		//Constructs Student that is greater than the others, and would appear later in the list
		Student greaterThanLast = new Student("Adam", "Dawson", "id", "email@ncsu.edu", "hashedpassword");
		//Constructs Student that is less than the others, and would appear earlier on the list.
		Student lessThanLast = new Student("Adam", "Adler", "abBarry", "email@ncsu.edu", "hashedpassword");
		//Constructs a Student that is equivalent, but only difference is the first name, making it greater.
		Student greaterThanFirst = new Student("Damien", "Barry", "abBarry", "email@ncsu.edu", "hashedpassword");
		//Constructs a Student that is equivalent, but only difference is the first name, making it less.
		Student lessThanFirst = new Student("Aaron", "Barry", "abBarry", "email@ncsu.edu", "hashedpassword");
		//Constructs an equivalent Student
		Student equal = new Student("Adam", "Barry", "abBarry", "email@ncsu.edu", "hashedpassword");
		
		assertEquals(-1, s.compareTo(a)); //Checks s is less than a
		assertEquals(1, a.compareTo(s)); //Checks a is greater than s
		assertEquals(-1, s.compareTo(greaterThanLast)); //Checks s is less than greaterThanLast
		assertEquals(1, greaterThanLast.compareTo(s)); //Checks greaterThanLast is greater than s
		assertEquals(1, s.compareTo(lessThanLast)); //Checks that s is greater than lessThanLast
		assertEquals(-1, lessThanLast.compareTo(s)); //Checks that lessThanLast is less than s
		assertEquals(1, greaterThanFirst.compareTo(s)); //Checks that greaterThanFirst is greater than s
		assertEquals(-1, lessThanFirst.compareTo(s)); //Checks that lessThanFirst is less than s
		assertEquals(0, s.compareTo(equal)); //Checks that equals Students are equal.
	}
}