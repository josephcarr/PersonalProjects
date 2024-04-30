package edu.ncsu.csc216.pack_scheduler.catalog;

	import static org.junit.Assert.assertEquals;
	import static org.junit.Assert.assertFalse;
	import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
	import static org.junit.Assert.fail;

	import java.io.FileInputStream;
	import java.io.IOException;
	import java.nio.file.FileSystems;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.util.Scanner;

	import org.junit.Before;
	import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;




	/**
	 * Tests the CourseCatalog class.
	 * 
	 * @author fttolliv
	 * @author Sarah Heckman
	 */
	public class CourseCatalogTest {
		
		/** Valid course records */
		private final String validTestFile = "test-files/course_records.txt";

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
		/** Course enrollment cap */
		private static final int ENROLLMENT_CAP = 10;
		/** Course meeting days */
		private static final String MEETING_DAYS = "TH";
		/** Course start time */
		private static final int START_TIME = 1330;
		/** Course end time */
		private static final int END_TIME = 1445;
		
		/**
		 * Resets course_records.txt for use in other tests.
		 * @throws Exception IOException if the files cannot reset
		 */
		@Before
		public void setUp() throws Exception {
			//Reset course_records.txt so that it's fine for other needed tests
			Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
			Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
			try {
				Files.deleteIfExists(destinationPath);
				Files.copy(sourcePath, destinationPath);
			} catch (IOException e) {
				fail("Unable to reset files");
			}
		}
		
		/**
		 * Tests CourseCatalog().
		 */
		@Test
		public void testCourseCatalog() {
			//Test that the StudentDirectory is initialized to an empty list
			CourseCatalog cc = new CourseCatalog();
			assertFalse(cc.removeCourseFromCatalog("CSC 116", "001"));
			assertEquals(0, cc.getCourseCatalog().length);
		}
		
		/**
		 * Tests CourseCatalog.testNewCourseCatalog().
		 */
		@Test
		public void testNewCourseCatalog() {
			//Test that if there are courses in the catalog, they 
			//are removed after calling newCourseCatalog().
			CourseCatalog cc = new CourseCatalog();
			
			cc.loadCoursesFromFile(validTestFile);
			assertEquals(13, cc.getCourseCatalog().length);
			
			cc.newCourseCatalog();
			assertEquals(0, cc.getCourseCatalog().length);
		}
		
		/**
		 * Tests CourseCatalog.loadCoursesFromFile().
		 */
		@Test
		public void testLoadCoursesFromFile() {
			CourseCatalog cc = new CourseCatalog();
					
			//Test valid file
			cc.loadCoursesFromFile(validTestFile);
			assertEquals(13, cc.getCourseCatalog().length);
		}
		
		/**
		 * Test WolfScheduler.addCourse().
		 */
		@Test
		public void testAddCourseToCatalog() {
			CourseCatalog cc = new CourseCatalog();
			
			assertEquals(0, cc.getCourseCatalog().length);

			//Attempt to add a course that does exist
			assertTrue(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
			assertEquals(1, cc.getCourseCatalog().length);
			String [] course = cc.getCourseCatalog()[0];
			assertEquals(NAME, course[0]);
			assertEquals(SECTION, course[1]);
			assertEquals(TITLE, course[2]);
			
			//Attempt to add a course that already exists, even if different section
			assertTrue(cc.addCourseToCatalog(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
			
		}
		
		/**
		 * Test CourseCatalog.removeCourse().
		 */
		@Test
		public void testRemoveCourseFromCatalog() {
			CourseCatalog cc = new CourseCatalog();
			//Attempt to remove from empty schedule
			assertFalse(cc.removeCourseFromCatalog(NAME, SECTION));
			
			//Add some courses and remove them
			assertTrue(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
			assertTrue(cc.addCourseToCatalog("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF", 935, 1025));
			assertTrue(cc.addCourseToCatalog("CSC116", "Intro to Programming - Java", "002", 3, "spbalik", 10, "MW", 1120, 1310));
			assertEquals(3, cc.getCourseCatalog().length);
			
			//Check that removing a course that doesn't exist when there are 
			//scheduled courses doesn't break anything
			assertFalse(cc.removeCourseFromCatalog("CSC492", "001"));
			assertEquals(3, cc.getCourseCatalog().length);
			
			//Remove CSC226
			assertTrue(cc.removeCourseFromCatalog("CSC226", "001"));
			assertEquals(2, cc.getCourseCatalog().length);
			
			//Remove CSC116
			assertTrue(cc.removeCourseFromCatalog("CSC116", "002"));
			assertEquals(1, cc.getCourseCatalog().length);
			
			//Remove CSC216
			assertTrue(cc.removeCourseFromCatalog(NAME, SECTION));
			assertEquals(0, cc.getCourseCatalog().length);
			
			//Check that removing all doesn't break future adds
			assertTrue(cc.addCourseToCatalog("CSC230", "C and Software Tools", "001", 3, "dbsturgi", 10, "MW", 1145, 1300));
			assertEquals(1, cc.getCourseCatalog().length);
		}
		
		/**
		 * Test CourseCatalog.getCourseFromCatalog().
		 */
		@Test
		public void testGetCourseFromCatalog() {
			CourseCatalog cc = new CourseCatalog();
			cc.loadCoursesFromFile(validTestFile);
			//Attempt to get a course that doesn't exist
			assertNull(cc.getCourseFromCatalog("CSC 492", "001"));
			
			//Attempt to get a course that does exist
			Course c = new Course(NAME, TITLE, SECTION, CREDITS, null, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			assertEquals(c, cc.getCourseFromCatalog("CSC216", "001"));
		}
		
		/**
		 * Test CourseCatalog.getCourseCatalog().
		 */
		@Test
		public void testGetCourseCatalog() {
			CourseCatalog cc = new CourseCatalog();
			cc.loadCoursesFromFile(validTestFile);
			//Get the catalog and make sure contents are correct
			//Name, section, title
			String [][] catalog = cc.getCourseCatalog();
			//Row 0
			assertEquals("CSC116", catalog[0][0]);
			assertEquals("001", catalog[0][1]);
			assertEquals("Intro to Programming - Java", catalog[0][2]);
			//Row 1
			assertEquals("CSC116", catalog[1][0]);
			assertEquals("002", catalog[1][1]);
			assertEquals("Intro to Programming - Java", catalog[1][2]);
			//Row 2
			assertEquals("CSC116", catalog[2][0]);
			assertEquals("003", catalog[2][1]);
			assertEquals("Intro to Programming - Java", catalog[2][2]);
			//Row 3
			assertEquals("CSC216", catalog[3][0]);
			assertEquals("001", catalog[3][1]);
			assertEquals("Software Development Fundamentals", catalog[3][2]);
			//Row 4
			assertEquals("CSC216", catalog[4][0]);
			assertEquals("002", catalog[4][1]);
			assertEquals("Software Development Fundamentals", catalog[4][2]);
			//Row 5
			assertEquals("CSC216", catalog[5][0]);
			assertEquals("601", catalog[5][1]);
			assertEquals("Software Development Fundamentals", catalog[5][2]);
			//Row 6
			assertEquals("CSC217", catalog[6][0]);
			assertEquals("202", catalog[6][1]);
			assertEquals("Software Development Fundamentals Lab", catalog[6][2]);
			//Row 7
			assertEquals("CSC217", catalog[7][0]);
			assertEquals("211", catalog[7][1]);
			assertEquals("Software Development Fundamentals Lab", catalog[7][2]);
			//Row 8
			assertEquals("CSC217", catalog[8][0]);
			assertEquals("223", catalog[8][1]);
			assertEquals("Software Development Fundamentals Lab", catalog[8][2]);
			//Row 9
			assertEquals("CSC217", catalog[9][0]);
			assertEquals("601", catalog[9][1]);
			assertEquals("Software Development Fundamentals Lab", catalog[9][2]);
			//Row 10
			assertEquals("CSC226", catalog[10][0]);
			assertEquals("001", catalog[10][1]);
			assertEquals("Discrete Mathematics for Computer Scientists", catalog[10][2]);
			//Row 11
			assertEquals("CSC230", catalog[11][0]);
			assertEquals("001", catalog[11][1]);
			assertEquals("C and Software Tools", catalog[11][2]);
			//Row 12
			assertEquals("CSC316", catalog[12][0]);
			assertEquals("001", catalog[12][1]);
			assertEquals("Data Structures and Algorithms", catalog[12][2]);
		}
		
		/**
		 * Tests CourseCatalog.saveCourseCatalog().
		 */
		@Test
		public void testSaveCourseCatalog() {
			CourseCatalog cc = new CourseCatalog();
			
			//Add a course
			cc.addCourseToCatalog("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
			assertEquals(1, cc.getCourseCatalog().length);
			cc.saveCourseCatalog("test-files/actual_course_records.txt");
			checkFiles("test-files/expected_course_records.txt", "test-files/actual_course_records.txt");
		}
		
		/**
		 * Helper method to compare two files for the same contents
		 * @param expFile expected output
		 * @param actFile actual output
		 */
		private void checkFiles(String expFile, String actFile) {
			try {
				Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));
				
				while (actScanner.hasNextLine()) {
					assertEquals(expScanner.nextLine(), actScanner.nextLine());
				}
				
				expScanner.close();
				actScanner.close();
			} catch (IOException e) {
				fail("Error reading files.");
			}
		}
		
	}

