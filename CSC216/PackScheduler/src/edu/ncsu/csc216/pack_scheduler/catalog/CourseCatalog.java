package edu.ncsu.csc216.pack_scheduler.catalog;


import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * 
 * Class for PackScheduler. Creates a course catalog. Loads the courses from a file.
 * Adds, removes, and get courses from the course catalog. Also, saves the catalog.
 * @author faithtolliver
 *
 */
public class CourseCatalog {

	/** Catalog for Course SortedList. */
	private SortedList<Course> catalog;

/**
	 * Creates an empty course catalog.
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

/**
	 * Creates an empty course catalog. 
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

/**
	 * Constructs the course catalog by reading in course information
	 * from the given file.  Throws an IllegalArgumentException if the 
	 * file cannot be found.
	 * @param fileName file containing catalog of course
	 * @throws IllegalArgumentException if file cannot be read
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
/**
	 * Adds a Course to the catalog.  Returns true if the course is added and false if
	 * the course is unable to be added.
	 * 
	 * 
	 * @param name courses name
	 * @param title the title of the course
	 * @param section the section of the course
	 * @param credits the number of credits for the course
	 * @param instructorId the instructors ID
	 * @param enrollmentCap the enrollment cap
	 * @param meetingDays are the meetingDays of the course
	 * @param startTime is the start time of the course
	* @param endTime is the end time of the course
	 * @throws IllegalArgumentException if the course cannot be made
	 * @return true if added
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		// Find course in catalog
		Course findCourse = getCourseFromCatalog(name, section);
		// if course is already in catalog then return false
		if (findCourse != null) {
			return false;
		}
		
		//If an IllegalArgumentException is thrown, it's passed up from Course
		//to the GUI
		Course addCourse = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		catalog.add(addCourse);
		
		return true;
	}
	/**
	 * Removes the course with the given name and section from the catalog..
	 * Returns true if the course is removed and false if the course is not in the catalog.
	 * @param name the course name
	 * @param section the section of the course
	 * @return true if removed
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		boolean inCatalog = false;
	for (int i = 0; i < catalog.size(); i++) {
		Course c = catalog.get(i);
		if (c.getName().equals(name) && c.getSection().equals(section) && catalog.contains(c)) {
				catalog.remove(i);
				inCatalog = true;
		}
	}
	return inCatalog;
	}
	
/**
	 * Runs through catalog to see if a certain name and section are present
	 * @param name name of the course
	 * @param section section of the course
	 * @return course, object in catalog or null if not found.
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
					return course;
			}
		}
		return null;
	}

/**
	 * Creates a 2D array based on the catalog size, and gets the
	 * name, section, title, and information.
	 * @return catalogArray returns a 2D array of courses
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][5];
		for(int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			catalogArray[i] = c.getShortDisplayArray();
			
		}
		
		return catalogArray;
	}

	/**
	 * Saves all courses in the catalog to a file.
	 * @param fileName name of file to save courses to the catalog.
	 * @throws IllegalArgumentException if file cannot be written.
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

}

