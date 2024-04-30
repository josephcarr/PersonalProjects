package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;
/**
 * Creates a Student with the the student's first name, last name, student ID,
 * email, password, and the max credits the student can get. Implements Comparable to compare
 * Students and put them in order.
 * @author cbdocke2
 * @author dccicin
 * @author fttolliv
 */
public class Student extends User implements Comparable<Student> {
	
	/** The max credits a student can have */
	public static final int MAX_CREDITS = 18;
	/** The max credits a student can have */
	private int maxCredits;
	/** The student's schedule */
	private Schedule mySchedule;

	/**
	 * Constructs a Student object with all the fields
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param id the student's id
	 * @param email the student's email
	 * @param password the student's password
	 * @param maxCredits the student's max credits
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
		mySchedule = new Schedule();
	}
	
	
	/**
	 * Constructs a Student object with the student's first name, last name,
	 * studentId, email, and password.
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param id the student's id
	 * @param email the student's email
	 * @param password the student's password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}
	
	/**
	 * Returns a comma separated string of all Student fields.
	 * Overrides the toString method to work specifically for Students
	 */
	@Override
	public String toString() {
		// Creates String of student using student fields
		String studentString = getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCredits;
		return studentString; 
	}

	/**
	 * Returns the max credits
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the Student's maxCredits. If maxCredits is less than 3 or greater than 18,
	 * an IllegalArgumentException is thrown.
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if maxCredits are less than or greater than their boundaries
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}
	
	/**
	 * Gets the schedule
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return mySchedule;
	}

	/**
	 * Compares the last name, first name, and student ID of two student's in alphabetical order,
	 * first checking the last name, then the first name, then the studentId. Overrides the compareTo method
	 * to work specifically with Students.
	 * @param s the Student to compare
	 * @return compareValue, which is a 1 if the Student is greater than the given Student, and a -1
	 *  if the Student is less than the given Student, and a 0 if they are equal.
	 */
	@Override
	public int compareTo(Student s) {
		//Creates the compareValue, which is 1 when compareTo is positive, -1 when it is
		//negative, and 0 when they are equal.
		int compareValue = 0;
		//Compares the last names
		if (this.getLastName().compareTo(s.getLastName()) > 0) {
			compareValue = 1;
		}
		else if (this.getLastName().compareTo(s.getLastName()) < 0) {
			compareValue = -1;
		}
		else if (this.getLastName().compareTo(s.getLastName()) == 0) {
			//Compares the first names
			if (this.getFirstName().compareTo(s.getFirstName()) > 0) {
				compareValue = 1;
			}
			else if (this.getFirstName().compareTo(s.getFirstName()) < 0) {
				compareValue = -1;
			}
			else if (this.getFirstName().compareTo(s.getFirstName()) == 0) {
				//Compares the student IDs
				if (this.getId().compareTo(s.getId()) > 0) {
					compareValue = 1;
				}
				else if (this.getId().compareTo(s.getId()) < 0) {
					compareValue = -1;
				}
				else if(this.getId().compareTo(s.getId()) == 0) {
					compareValue = 0;
				}

			}

		}
		
		return compareValue;
		
	}
	
	/**
	 * Auto-generated hashCode method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCredits);
		return result;
	}
	
	/**
	 * Auto-generated equals method that checks if
	 * two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}
	
	/**
	 * true if the student can add the class, otherwise false
	 * @param c the class that will be looked into, whether they can add it
	 * @return true if can add the class, false otherwise
	 */
	public boolean canAdd(Course c) {
		int credits = c.getCredits();
		int totalCredits = credits + mySchedule.getScheduleCredits();
		
		return totalCredits <= maxCredits;

	}

}