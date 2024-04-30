package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Allows the Registrar to maintain a Course Catalog that they created or loaded from a file, by allowing them to add and remove courses,
 * as well as save the course catalog to a file. The Registrar can also maintain a Student Directory by allowing them to create a
 * student directory, or load a student directory from a file, add and remove students from it, as well as saving it to a file.
 * @author cbdocke2
 * @author Thomas Elpers
 * @author Shreya Holikatti
 * @author Sarah Heckman
 *
 */
public class RegistrationManager {
	
	/** Instance of the RegistrationManager */
	private static RegistrationManager instance;
	/** Registrar Course Catalog */
	private CourseCatalog courseCatalog;
	/** Registrar Student Directory */
	private StudentDirectory studentDirectory;
	/** Registrar Faculty Directory */
	private FacultyDirectory facultyDirectory;
	/** The Registrar */
	private User registrar;
	/** The Current User */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** The properties file */
	private static final String PROP_FILE = "registrar.properties";

	/** RegistrationManager Constructor
	 * Creates a new Registrar
	 */
	private RegistrationManager() {
		createRegistrar();
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
		facultyDirectory = new FacultyDirectory();
	}
	
	/**
	 * Creates a Registrar from the Properties File
	 * @throws IllegalArgumentException if Registrar cannot be created (no properties file)
	 */
	private void createRegistrar() {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			
			String hashPW = hashPW(prop.getProperty("pw"));
			
			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}
	
	/**
	 * Creates a hash password
	 * @param pw the password used
	 * @return the hash password
	 * @throws IllegalArgumentException if hash password cannot be created
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Creates an instance of the RegitrationManger if null and returns it
	 * @return instance The instance of the RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	
	/**
	 * Returns the Course Catalog
	 * @return courseCatalog The Course Catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	
	/**
	 * Returns the Student Directory
	 * @return studentDirectory The Student Directory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	/**
	 * Returns the Faculty Directory
	 * @return studentDirectory The Student Directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Checks if the User has the right login information
	 * @param id the ID of the User
	 * @param password the Password of the User
	 * @return true if password and id are the same as the Registrar's
	 * @throws IllegalArgumentException if the user does not exist
	 */
	public boolean login(String id, String password) {
		
		Student s = studentDirectory.getStudentById(id);
		Faculty faculty = facultyDirectory.getFacultyById(id);

		
		if (currentUser != null) {
			return false;
		}
		
		String localHashPW = hashPW(password);
		
		if (s != null) {
			if (s.getPassword().equals(localHashPW)) {
				currentUser = s;
				return true;
			}
			else {
				return false;
			}
		}

		
		if (faculty != null) {
			if (faculty.getPassword().equals(localHashPW)) {
				currentUser = faculty;
				return true;
			}
			else {
				return false;
			}
			

		}

		
		if (registrar.getId().equals(id)) {
			if(!registrar.getPassword().equals(localHashPW)) {
				return false;
			}
			else {
				currentUser = registrar; 
				return true;
			}
			
		}
		
		if (faculty != null || s != null || registrar.getId().equals(id)) {
			return false;
		}
		
		else {
			throw new IllegalArgumentException("User doesn't exist.");
		}
		

	
	}

	/**
	 * Logs the Current user out and sets it to the registrar
	 */
	public void logout() {
		currentUser = null; 
	}
	
	/**
	 * Returns the Current User
	 * @return currentUser the Current User
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Resets the Course Catalog and Student Directory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
		logout();
	}
	
	/**
	 * Creates a Registrar.
	 * The class for the Registrar type of User, who is a University official who maintains the directory
	 * of Students. They can also create a Course catalog, in which they can add and remove courses, and also 
	 * save the Course catalog to a file.
	 * @author cbdocke2
	 * @author Sarah Heckman
	 *
	 */
	private static class Registrar extends User {
		/**
		 * Creates a Registrar User
		 * @param firstName First name of User
		 * @param lastName Last nane of User
		 * @param id ID of User
		 * @param email Email of User
		 * @param hashPW Password of User
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 * @throws IllegalArgumentException if the current user is an instance of a student
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 * @throws IllegalArgumentException if the current user is an instance of a student
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * Adds course to faculty schedule if user is registrar
	 * @param course given course
	 * @param faculty given faculty
	 * @return if course is added
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser == registrar) {
			faculty.getSchedule().addCourseToSchedule(course);
			return true;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Removes course from faculty schedule if user is registrar
	 * @param course given course
	 * @param faculty given faculty
	 * @return if course is removed
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser == registrar) {
			faculty.getSchedule().removeCourseFromSchedule(course);
			return true;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Resets faculty schedule if user is registrar
	 * @param faculty given faculty
	 * @return if faculty schedule is reset
	 */
	public boolean resetFacultySchedule(Faculty faculty) {
		if (currentUser != null && currentUser == registrar) {
			faculty.getSchedule().resetSchedule();
			return true;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
}