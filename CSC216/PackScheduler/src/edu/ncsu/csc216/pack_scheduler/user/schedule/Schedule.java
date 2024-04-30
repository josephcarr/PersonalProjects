package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Creates an arraylist of courses and holds a title. When constructed, the title is defaulted
 * to My Schedule and the arraylist is casted to an empty arraylist of courses. It can add a 
 * course to the schedule. It can remove a given course from the schedule if the course is 
 * in the schedule. It can reset the schedule to an empty arraylist. It can return the courses
 * in the schedule with there given name, section, title and meeting string. It can change 
 * the schedule title and return the title.
 * 
 * @author Thomas Elpers
 *
 */
public class Schedule {
	
	/** Schedule's title */
	private String title;
	/** ArrayList with schedule contents */
	private ArrayList<Course> schedule;
	
	/**
	 * Constructs a Schedule with title "My Schedule" and an empty ArrayList
	 */
	public Schedule() {
		setTitle("My Schedule");
		schedule = new ArrayList<Course>();
	}
	
	/**
	 * Adds course to the schedule. If the given course is already in the schedule or the 
	 * course meeting time causes a conflict an exception is thrown
	 * @param c Given course to add
	 * @return a boolean whether the course is added or not
	 * @throws IllegalArgumentException if already enrolled in class
	 */
	public boolean addCourseToSchedule(Course c) {
		for(int i = 0; i < schedule.size(); i++) {
			if(c.isDuplicate(schedule.get(i))) {
				throw new IllegalArgumentException("You are already enrolled in " + schedule.get(i).getName());
			}
			try {
				c.checkConflict(schedule.get(i));
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		schedule.add(schedule.size(), c);
		return true;
	}
	
	/**
	 * Removes given course from schedule if the course is in the schedule
	 * @param c the course to be removed
	 * @return a boolean whether the course is removed or not 
	 */
	public boolean removeCourseFromSchedule(Course c) {
		if (c == null) {
			return false;
		}
		
		for(int i = 0; i < schedule.size(); i++) {
			if(c.isDuplicate(schedule.get(i))) { //changed equals to isDuplicate
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Resets the schedule by emptying the arraylist and changing the title to My Schedule
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
		setTitle("My Schedule");
	}
	
	/**
	 * Returns an array with each row being a course with the name, section, title, and 
	 * meeting string
	 * @return array of courses with name, section, title, and meeting string
	 */
	public String[][] getScheduledCourses(){
		String[][] courses = new String[schedule.size()][4];
		for(int i = 0; i < schedule.size(); i++) {
			Course course = schedule.get(i);
			courses[i] = course.getShortDisplayArray();
		}
		return courses;
	}
	
	/**
	 * Sets the Schedule's title. If the title is null an exception is thrown
	 * @param title Schedule title
	 * @throws IllegalArgumentException if title parameter is invalid
	 */
	public void setTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		else {
			this.title = title;
		}
	}
	
	/**
	 * Returns title
	 * @return title Schedule's title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the total credits in the schedule
	 * @return total credits in the schedule
	 */
	public int getScheduleCredits() {
		int total = 0;
		for(int i = 0; i < schedule.size(); i++) {
			total += schedule.get(i).getCredits();
		}
		return total;
	}
	
	/**
	 * Checks to see a course can be added to the schedule
	 * @param c course to add to the schedule
	 * @return if course can be added to schedule
	 */
	public boolean canAdd(Course c) {
		if(c == null) {
			return false;
		}
		for(int i = 0; i < schedule.size(); i++) {
			if(c.isDuplicate(schedule.get(i))) {
				return false;
			}
			try {
				c.checkConflict(schedule.get(i));
			} catch (ConflictException e) {
				return false;
			}
		}
		return true;
	}
}