package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

class ScheduleTest {

	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	
	
	/**
	 * Tests Schedule constructor
	 */
	@Test
	void testSchedule() {
		Schedule s = new Schedule();
		
		assertEquals("My Schedule", s.getTitle());
		assertEquals(0, s.getScheduledCourses().length);
	}
	
	/**
	 * Tests addCourseToSchedule()
	 */
	@Test
	void testAddCourseToSchedule() {
		Schedule s = new Schedule();
		
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, 10, MEETING_DAYS, START_TIME, END_TIME);
		assertTrue(s.addCourseToSchedule(c));
		
		assertEquals(1, s.getScheduledCourses().length);
		
		try {
			s.addCourseToSchedule(c);
		} catch(IllegalArgumentException e){
			assertEquals("You are already enrolled in CSC216", e.getMessage());
		}
		
		Course c1 = new Course("CSC116", "Introduction to Programming - Java", SECTION, CREDITS, "jsetter", 10, MEETING_DAYS, START_TIME, END_TIME);
		try {
			s.addCourseToSchedule(c1);
		} catch(IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}
	}
	
	/**
	 * Tests removeCourseFromSchedule()
	 */
	@Test
	void testRemoveCourseFromSchedule() {
		Schedule s = new Schedule();
		
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, 10, MEETING_DAYS, START_TIME, END_TIME);
		Course c1 = new Course("CSC116", "Introduction to Programming - Java", SECTION, CREDITS, "jsetter", 10, "MW", START_TIME, END_TIME);
		Course c2 = new Course("CSC226", "Discrete Mathmatics for Computer Scientists", "001", CREDITS, "tmbarnes", 10, MEETING_DAYS, 1500, 1615);
		
		assertFalse(s.removeCourseFromSchedule(c));
		
		assertTrue(s.addCourseToSchedule(c));
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.addCourseToSchedule(c2));
		
		assertEquals(3, s.getScheduledCourses().length);
		
		assertTrue(s.removeCourseFromSchedule(c));
		
		assertEquals(2, s.getScheduledCourses().length);
	}

	/**
	 * Tests resetSchedule()
	 */
	@Test
	void testResetSchedule() {
		Schedule s = new Schedule();
		
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, 10, MEETING_DAYS, START_TIME, END_TIME);
		Course c1 = new Course("CSC116", "Introduction to Programming - Java", SECTION, CREDITS, "jsetter", 10, "MW", START_TIME, END_TIME);
		Course c2 = new Course("CSC226", "Discrete Mathmatics for Computer Scientists", "001", CREDITS, "tmbarnes", 10, MEETING_DAYS, 1500, 1615);
		
		assertTrue(s.addCourseToSchedule(c));
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.addCourseToSchedule(c2));
		
		assertEquals(3, s.getScheduledCourses().length);
		
		s.resetSchedule();
		
		assertEquals(0, s.getScheduledCourses().length);
	}
	
	/**
	 * Test getScheduleCourses()
	 */
	@Test
	void testGetScheduleCourses() {
		Schedule s = new Schedule();
		
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, 10, MEETING_DAYS, START_TIME, END_TIME);
		Course c1 = new Course("CSC116", "Introduction to Programming - Java", SECTION, CREDITS, "jsetter", 10, "MW", START_TIME, END_TIME);
		Course c2 = new Course("CSC226", "Discrete Mathmatics for Computer Scientists", "001", CREDITS, "tmbarnes", 10, MEETING_DAYS, 1500, 1615);
		
		assertTrue(s.addCourseToSchedule(c));
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.addCourseToSchedule(c2));
		
		assertEquals(3, s.getScheduledCourses().length);
		
		String [][] schedule = s.getScheduledCourses();
		//Row 1
		assertEquals("CSC216", schedule[0][0]);
		assertEquals("001", schedule[0][1]);
		assertEquals("Software Development Fundamentals", schedule[0][2]);
		assertEquals("TH 1:30PM-2:45PM", schedule[0][3]);
		assertEquals("10", schedule[0][4]);
		//Row 2
		assertEquals("CSC116", schedule[1][0]);
		assertEquals("001", schedule[1][1]);
		assertEquals("Introduction to Programming - Java", schedule[1][2]);
		assertEquals("MW 1:30PM-2:45PM", schedule[1][3]);
		assertEquals("10", schedule[1][4]);
		//Row 3
		assertEquals("CSC226", schedule[2][0]);
		assertEquals("001", schedule[2][1]);
		assertEquals("Discrete Mathmatics for Computer Scientists", schedule[2][2]);
		assertEquals("TH 3:00PM-4:15PM", schedule[2][3]);
		assertEquals("10", schedule[2][4]);
	}
	
	/**
	 * Tests getScheduleCredits
	 */
	@Test
	public void testGetScheduleCredits() {
		Schedule s = new Schedule();
		
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, 10, MEETING_DAYS, START_TIME, END_TIME);
		Course c1 = new Course("CSC116", "Introduction to Programming - Java", SECTION, CREDITS, "jsetter", 10, "MW", START_TIME, END_TIME);
		Course c2 = new Course("CSC226", "Discrete Mathmatics for Computer Scientists", "001", CREDITS, "tmbarnes", 10, MEETING_DAYS, 1500, 1615);
		
		assertTrue(s.addCourseToSchedule(c));
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.addCourseToSchedule(c2));
		
		assertEquals(3, s.getScheduledCourses().length);
		
		assertEquals(9, s.getScheduleCredits());
	}
	
	/**
	 * Tests canAdd
	 */
	@Test
	public void testCanAdd() {
		Schedule s = new Schedule();
		
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, 10, MEETING_DAYS, START_TIME, END_TIME);
		Course c1 = new Course("CSC116", "Introduction to Programming - Java", SECTION, CREDITS, "jsetter", 10, "MW", START_TIME, END_TIME);
		Course c2 = new Course("CSC226", "Discrete Mathmatics for Computer Scientists", "001", CREDITS, "tmbarnes", 10, MEETING_DAYS, 1500, 1615);
		Course c3 = new Course("CSC230", "C and Software Tools", "001", CREDITS, "twhite", 10, MEETING_DAYS, START_TIME, END_TIME);
		
		assertTrue(s.addCourseToSchedule(c));
		assertTrue(s.addCourseToSchedule(c1));
		assertTrue(s.canAdd(c2));
		
		assertTrue(s.addCourseToSchedule(c2));
		
		assertFalse(s.canAdd(c));
		assertFalse(s.canAdd(c3));
	}
}