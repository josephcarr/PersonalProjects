package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * This class constructs a Faculty object
 * @author shreyaholikatti
 *
 */
public class Faculty extends User {

	/** Maximum number of courses faculty can teach */
	private int maxCourses;
	/** Faculty schedule */
	FacultySchedule schedule;
	
	/** Constant for the minimum number of courses faculty can teach */
	public static final int MIN_COURSES = 1;
	/** Constant for the maximum number of courses faculty can teach */
	public static final int MAX_COURSES = 3;

	
	/**
	 * Creates a Faculty object with the parameterized fields
	 * 
	 * @param firstName		faculty first name
	 * @param lastName		faculty last name
	 * @param id			faculty unity id
	 * @param email			faculty email
	 * @param password		faculty password
	 * @param maxCourses	faculty maxCourses for max number of courses they can teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		//facultyDirectory = new FacultyDirectory();
		schedule = new FacultySchedule(id);
	}
	


	/**
	 * Sets the max number of courses a faculty member can
	 * teach in a given semester
	 * 
	 * @param maxCourses	maximum number of courses a faculty member can teach
	 */
	public void setMaxCourses(int maxCourses) {
		if(maxCourses < 1 || maxCourses > 3) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}
	
	
	/**
	 * Gets the maximum number of courses a faculty 
	 * member can teach in a given semester
	 * 
	 * @return maxCourses	maximum number of courses a faculty member can teach
	 */
	public int getMaxCourses() {
		return maxCourses;
	}
	
	/**
	 * Returns the schedule of the faculty
	 * @return schedule faculty schedule
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}
	
	/**
	 * Checks if the schedule is over the max amount of courses
	 * @return if schedule is over maximum courses
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > getMaxCourses();
	}
	
	/**
	 * Returns a comma separated string of all Student fields.
	 * Overrides the toString method to work specifically for Students
	 */
	@Override
	public String toString() {
		// Creates String of student using student fields
		String facultyString = getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCourses;
		return facultyString; 
	}



	/**
	 * Auto-generated hashCode code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}


	/**
	 * Auto-generated boolean code
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}
	
	
}
