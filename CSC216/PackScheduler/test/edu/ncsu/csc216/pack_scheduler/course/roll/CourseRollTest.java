/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Test class for CourseRoll.
 * @author faithtolliver
 * @author Vineet Krishna
 * @author Joseph Carrasco
 *
 */
public class CourseRollTest {
	
	/** valid enrollment cap */
	private final static int VALID_CAP = 100;
	/** above maximum enrollment cap */
	private final static int OVER_CAP = 251;
	/** below minimum enrollment cap */
	private final static int UNDER_CAP = 9;
	/** the maximum cap */
	private final static int MAX_CAP = 250;
	/** the minimum cap */
	private final static  int MIN_CAP = 10;
		
	/** 
	 * Test CourseRoll constructor
	 */
	@Test
	public void courseRollConstructorTest() {
		// test valid constructions of CourseRoll
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();

		//CourseRoll roll = null;
		try {
			roll = new CourseRoll(c, VALID_CAP);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(VALID_CAP, roll.getOpenSeats());
		assertEquals(VALID_CAP, roll.getEnrollmentCap());
		
		try {
			roll = new CourseRoll(c, MIN_CAP);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(MIN_CAP, roll.getOpenSeats());
		assertEquals(MIN_CAP, roll.getEnrollmentCap());
		
		try {
			roll = new CourseRoll(c, MAX_CAP);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(MAX_CAP, roll.getOpenSeats());
		assertEquals(MAX_CAP, roll.getEnrollmentCap());
		
		// test invalid constructions of CourseRoll
		assertThrows(IllegalArgumentException.class, 
			() -> {
				new CourseRoll(c, OVER_CAP);
			});
		
		assertThrows(IllegalArgumentException.class, 
			() -> {
				new CourseRoll(c, UNDER_CAP);
			});
	}
	
	/**
	 * Test setEnrollmentCap()
	 */
	@Test
	public void setEnrollmentCapTest() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = new CourseRoll(c, VALID_CAP);
		StudentDirectory s = new StudentDirectory();
		s.loadStudentsFromFile("test-files/student_records.txt");
		roll.enroll(s.getStudentById("zking"));
		roll.enroll(s.getStudentById("shansen"));
		assertEquals(VALID_CAP - 2, roll.getOpenSeats());
		assertEquals(VALID_CAP, roll.getEnrollmentCap());
		// valid new cap
		assertDoesNotThrow(() -> roll.setEnrollmentCap(10));
		
		// invalid cap 
		assertThrows(IllegalArgumentException.class, 
			() -> {
				roll.setEnrollmentCap(1);
			});
	}
	
	/**
	 * test enroll();
	 */
	@Test
	public void enrollTest() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = new CourseRoll(c, 10);
		StudentDirectory s1 = new StudentDirectory();
		s1.loadStudentsFromFile("test-files/student_records.txt");
		roll.enroll(s1.getStudentById("zking"));
		roll.enroll(s1.getStudentById("cschwartz"));
		assertEquals(8, roll.getOpenSeats());
		assertEquals(10, roll.getEnrollmentCap());
		
		assertThrows(IllegalArgumentException.class, 
			() -> {
				roll.enroll(s1.getStudentById("zking"));
			});
		
		roll.enroll(s1.getStudentById("shansen"));
		roll.enroll(s1.getStudentById("daustin"));
		roll.enroll(s1.getStudentById("rbrennan"));
		roll.enroll(s1.getStudentById("efrost"));
		roll.enroll(s1.getStudentById("lberg"));
		roll.enroll(s1.getStudentById("gstone"));
		roll.enroll(s1.getStudentById("ahicks"));
		roll.enroll(s1.getStudentById("dnolan"));
		
		Student student = new Student("Test", "Waitlist", "testdwaitlist", "test@ncsu.edu", "abc123", 14);
		roll.enroll(student);
		
		assertEquals(0, roll.getOpenSeats());
		
		roll.drop(s1.getStudentById("dnolan"));
		
		assertEquals(0, roll.getOpenSeats());
		
		// set larger cap
		roll.setEnrollmentCap(VALID_CAP);
		
		assertThrows(IllegalArgumentException.class, 
			() -> {
				roll.enroll(s1.getStudentById("zking"));
			});
		assertThrows(IllegalArgumentException.class, 
			() -> {
				roll.enroll(s1.getStudentById(null));
			});
		

	}
	
	/**
	 * Test drop();
	 */
	@Test
	public void dropTest() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = new CourseRoll(c, VALID_CAP);
		StudentDirectory s = new StudentDirectory();
		s.loadStudentsFromFile("test-files/student_records.txt");
		roll.enroll(s.getStudentById("zking"));
		roll.enroll(s.getStudentById("shansen"));
		assertEquals(VALID_CAP - 2, roll.getOpenSeats());
		assertEquals(VALID_CAP, roll.getEnrollmentCap());
		
		// valid drop
		try {
			roll.drop(s.getStudentById("zking")); 
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(VALID_CAP - 1, roll.getOpenSeats());
		assertEquals(VALID_CAP, roll.getEnrollmentCap());
		
		// invalid drop: null student
		assertThrows(IllegalArgumentException.class, () -> {
			roll.drop(null);
		});
		
		
	}
	
	/**
	 * Test canEnroll()
	 */
	@Test
	public void canEnrollTest() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = new CourseRoll(c, VALID_CAP);
		StudentDirectory s = new StudentDirectory();
		s.loadStudentsFromFile("test-files/student_records.txt");
		roll.enroll(s.getStudentById("zking"));
		roll.enroll(s.getStudentById("shansen"));
		assertEquals(VALID_CAP - 2, roll.getOpenSeats());
		assertEquals(VALID_CAP, roll.getEnrollmentCap());
		
		// valid student to enroll
		assertTrue(roll.canEnroll(s.getStudentById("daustin")));
		assertEquals(VALID_CAP - 2, roll.getOpenSeats());
		assertEquals(VALID_CAP, roll.getEnrollmentCap());
		
		// invalid student to enroll: already enrolled student
		assertFalse(roll.canEnroll(s.getStudentById("zking")));
		assertEquals(VALID_CAP - 2, roll.getOpenSeats());
		assertEquals(VALID_CAP, roll.getEnrollmentCap());
		
		// set cap to current size (full roll)
		roll.setEnrollmentCap(10);
		assertEquals(8, roll.getOpenSeats());
		assertEquals(10, roll.getEnrollmentCap());
		roll.enroll(s.getStudentById("cschwartz"));
		roll.enroll(s.getStudentById("rbrennan"));
		roll.enroll(s.getStudentById("efrost"));
		roll.enroll(s.getStudentById("lberg"));
		roll.enroll(s.getStudentById("daustin"));
		roll.enroll(s.getStudentById("gstone"));
		roll.enroll(s.getStudentById("ahicks"));
		roll.enroll(s.getStudentById("dnolan"));
		// invalid student to enroll: no more room
		assertFalse(roll.canEnroll(s.getStudentById("daustin")));
		assertEquals(0, roll.getOpenSeats());
		assertEquals(10, roll.getEnrollmentCap());
	}
}
