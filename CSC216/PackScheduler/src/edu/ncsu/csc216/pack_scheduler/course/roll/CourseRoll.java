/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * CourseRoll class for PackScheduler.
 * @author faithtolliver
 * @author Shreya Holikatti
 *
 */
public class CourseRoll {
	
	/** Minimum number of students for class */
	private static final int MIN_ENROLLMENT = 10;
	/** Maximum number of students for class */
	private static final int MAX_ENROLLMENT = 250;
	/** enrollment cap */
	private int enrollmentCap;
	/** list for roll */
	private LinkedAbstractList<Student> roll;
	/** Max waitlist capacity */
	public static final int WAITLIST_SIZE = 10;
	/** Waitlist field for when students are added */
	private ArrayQueue<Student> waitlist;
	/** Course for roll */
	private Course c;
	
	/**
	 * Constructs the CourseRoll by setting the enrollment capacity
	 * and waitlist, and created a new LinkedList for the course
	 * roll.
	 * 
	 * @param c							course associated with the course roll
	 * @param enrollmentCap 			the enrollment cap
	 * @throws IllegalArgumentException	if the course is null
	 */
	public CourseRoll(Course c, int enrollmentCap) {
		if (c == null) {
			throw new IllegalArgumentException("Invalid course");
		}
		this.c = c;
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		setEnrollmentCap(enrollmentCap);
		waitlist = new ArrayQueue<Student>(10);
		waitlist.setCapacity(10);		
	}
	

	/**
	 * gets the Enrollment cap
	 * @return enrollmentCap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}
	
	/**
	 * sets enrollment cap
	 * @param enrollmentCap the enrollment cap
	 * @throws IllegalArgumentException	if the enrollment cap is below the minimum or above the maximum
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if(enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Invalid enrollment cap.");
		}
		
		if (enrollmentCap < roll.size()) {
			throw new IllegalArgumentException("Invalid enrollment cap.");
		}
		
		this.enrollmentCap = enrollmentCap;
		roll.setCapacity(enrollmentCap);
//		if(roll != null && enrollmentCap >= roll.size()) {
//			this.enrollmentCap = enrollmentCap;
//			roll.setCapacity(enrollmentCap);
//
//		}
	}
	
	/**
	 * enrolls a student into a course
	 * @param s the student that will be added
	 * @throws IllegalArgumentException	if the student is null
	 */
	public void enroll(Student s) {
		if(s == null) {
			throw new IllegalArgumentException();
		}
		
		else if(roll.contains(s)) {
			throw new IllegalArgumentException();
		}
		
		else if(roll.size() == enrollmentCap) {
			if(waitlist.size() < WAITLIST_SIZE) {
				waitlist.enqueue(s);
			}
			
			else {
				throw new IllegalArgumentException("Cannot add student to waitlist");
			}
		}
		// if it generates an exception to add the student throw an exception
		else {
			roll.add(s);
		}
	}
	
	/**
	 * removes a student from a class
	 * @param s 						the student that will be removed
	 * @throws IllegalArgumentException if the student is null
	 */
	public void drop(Student s) {
		if(s == null) {
			throw new IllegalArgumentException();
		}
//		// if it generates an exception to add the student throw an exception
//		else {
			
			//if student is in main roll, remove the student and add the first student in the waitlist
			for(int i = 0; i < roll.size(); i++) {
				if(roll.get(i) == s) {
					roll.remove(i);
					
					if (waitlist.size() > 0) {
						Student student = waitlist.dequeue();
						Schedule schedule = student.getSchedule();
						roll.add(student);
						schedule.addCourseToSchedule(c);
					}
				}
			}
			
			//if student is on the waitlist, remove them from it
			for(int i = 0; i < waitlist.size(); i++) {
					Student student = waitlist.dequeue();
					if(!(student == s)) {
						waitlist.enqueue(student);
					}
				}
			}
	
		
	
	
	/**
	 * gets the number of open seats
	 * @return the number of open seats
	 */
	public int getOpenSeats() {
		int open = enrollmentCap - roll.size();
		return open;
	}
	
	
	/**
	 * returns a boolean for whether a student can be added to a class
	 * @param s the student
	 * @return false if student cannot be added, otherwise true.
	 */
	public boolean canEnroll(Student s) {
		if(roll.size() >= enrollmentCap) {
			return false;
		}
		else if(roll.contains(s)) {
			return false;
		}
		else if(waitlist.contains(s)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets the number of students on the waitlist
	 * @return number of students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

}
