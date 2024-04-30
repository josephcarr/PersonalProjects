/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Creates a course with the name of the course, the title,
 * section number, number of credit hours, the instructor unity id, 
 * the meeting days, the starting time, and the ending time
 *
 * @author cbdocke2
 *
 */
public class Course extends Activity implements Comparable<Course> {
		
		/** Course's name. */
		private String name;
		/** minimum course name length */
		private static final int MIN_NAME_LENGTH = 4;
		/** maximum course name length */
		private static final int MAX_NAME_LENGTH = 7;
//		/** minimum course prefix character count */
//		private static final int MIN_LETTER_COUNT = 1;
//		/** maximum course prefix character count */
//		private static final int MAX_LETTER_COUNT = 4;
//		/** number of digits allowed in course name */
//		private static final int DIGIT_COUNT = 3;
		/** number of digits in section number */
		private static final int SECTION_LENGTH = 3;
		/** minimum number of credit hours */
		private static final int MIN_CREDITS = 1;
		/** maximum number of credit hours */
		private static final int MAX_CREDITS = 5;
		
		/** Course name validator */
		private CourseNameValidator validator;
		/** Course roll */
		private CourseRoll roll;
				
		/**
		 * Returns the Course's name
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * Sets the Course's name.  If the name is null, has a length less than 5 or more than 8,
		 * does not contain a space between letter characters and number characters, has less than 1
		 * or more than 4 letter characters, and not exactly three trailing digit characters, an
		 * IllegalArgumentException is thrown.
		 * @param name the name to set
		 * @throws IllegalArgumentException if the name parameter is invalid
		 */
		private void setName(String name) {
			//Throw exception if the name is null
			if (name == null) {
				throw new IllegalArgumentException("Invalid course name.");
			}
			
			//Throw exception if the name is an empty string
			//Throw exception if the name contains less than 5 characters or greater than 8 characters
			if ("".equals(name)) {
				throw new IllegalArgumentException("Invalid course name.");
			}
			if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
				throw new IllegalArgumentException("Invalid course name.");
			}
			
			//Check for pattern of L[LLL] NNN
//			int letterCounter = 0;
//			int digitCounter = 0;
//			boolean foundASpace = false;
//				for (int i = 0; i < name.length(); ++i) {
//					if (!foundASpace) {
//						if (Character.isLetter(name.charAt(i))) {
//							++letterCounter;
//						}
//						else if (name.charAt(i) == ' ') {
//							foundASpace = true;
//						}
//						else {
//							throw new IllegalArgumentException("Invalid course name.");
//						}
//					}
//					else if (foundASpace) {
//						if (Character.isDigit(name.charAt(i))) {
//							++digitCounter;
//						}
//						else {
//							throw new IllegalArgumentException("Invalid course name.");
//						}
//					}
//				}
//			//Check that the number of letters is correct
//				if (letterCounter < MIN_LETTER_COUNT || letterCounter > MAX_LETTER_COUNT) {
//					throw new IllegalArgumentException("Invalid course name.");
//				}
//			
//			//Check that the number of digits is correct
//				if (digitCounter != DIGIT_COUNT) {
//					throw new IllegalArgumentException("Invalid course name.");
//				}
			
			try {
				if (validator.isValid(name)) {
					this.name = name;
				}
				else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
			catch (InvalidTransitionException e) {
				throw new IllegalArgumentException("Invalid course name.");
			}
		}
		/**
		 * Returns the Course's section
		 * @return the section
		 */
		public String getSection() {
			return section;
		}
		/**
		 * Sets the Course's section. If section is not exactly 3 digits
		 * an IllegalArgumentException is thrown.
		 * @param section the section to set
		 * @throws IllegalArgumentException if section is invalid
		 */
		public void setSection(String section) {
			//throw exception if section number is not exactly 3 digits
			if (section == null || section.length() != SECTION_LENGTH) {
				throw new IllegalArgumentException("Invalid section.");
			}
			for (int i = 0; i < section.length(); ++i) {
				if(!Character.isDigit(section.charAt(i))) {
					throw new IllegalArgumentException("Invalid section.");
				}
			}
			this.section = section;
		}
		/**
		 * Returns the Course's credit hours
		 * @return the credits
		 */
		public int getCredits() {
			return credits;
		}
		/**
		 * Sets the Course's credit hours. If credit hours are less than
		 * 1, or greater than 5, an IllegalArgumentException is thrown
		 * @param credits the credits to set
		 * @throws IllegalArgumentException if credits is invalid
		 */
		public void setCredits(int credits) {
			//throws IllegalArgumentException if credits is less than 1 or greater than 5
			if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
				throw new IllegalArgumentException("Invalid credits.");
			}
			
			
			this.credits = credits;
		}
		/**
		 * Returns the Course's instructor unity id
		 * @return the instructorId
		 */
		public String getInstructorId() {
			return instructorId;
		}
		/**
		 * Sets the Course's instructor unity id. If instructor id is null
		 * or an empty string, IllegalArgumentException is thrown.
		 * @param instructorId the instructorId to set
		 * @throws IllegalArgumentException if instructorId is invalid
		 */
		public void setInstructorId(String instructorId) {
			//throws IllegalArgumentException if instructorId is null or empty
			if("".equals(instructorId)) {
				throw new IllegalArgumentException("Invalid instructor id.");
			}
			
			this.instructorId = instructorId;
		}
		
		/**
		 * Generates a hashCode for Course using the credits, instructorId,
		 * name, and section fields.
		 * Overrides to work specifically for Course objects
		 * @return hashCode for Activity
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + credits;
			result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((section == null) ? 0 : section.hashCode());
			return result;
		}
		
		/**
		 * Compares a given object to this object for equality in all fields.
		 * Overrides the equals method to work specifically for Course objects
		 * @param obj the Object to compare
		 * @return true if the objects are the same on all fields 
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			Course other = (Course) obj;
			if (credits != other.credits)
				return false;
			if (instructorId == null) {
				if (other.instructorId != null)
					return false;
			} else if (!instructorId.equals(other.instructorId))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (section == null) {
				if (other.section != null)
					return false;
			} else if (!section.equals(other.section))
				return false;
			return true;
		}

		/** Course's section. */
		private String section;
		/** Course's credit hours */
		private int credits;
		/** Course's instructor */
		private String instructorId;
		/**
		 * Constructs a Course object with values for all fields.
		 * @param name name of Course
		 * @param title title of Course
		 * @param section section of Course
		 * @param credits credit hours of Course
		 * @param instructorId instructor's unity id
		 * @param enrollmentCap the enrollment cap
		 * @param meetingDays meeting days for Course as series of chars
		 * @param startTime start time for Course
		 * @param endTime end time for Course
		 */
		public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
			super(title, meetingDays, startTime, endTime);
			validator = new CourseNameValidator();
			setName(name);
			setSection(section);
			setCredits(credits);
			setInstructorId(instructorId);
			roll = new CourseRoll(this, enrollmentCap);

		}
		
		/**
		 * Creates a Course with the given name, title, section, credits, instructId, and meetingDays for
		 * courses that are arranged.
		 * @param name name of Course
		 * @param title title of Course
		 * @param section section of Course
		 * @param credits credit hours of Course
		 * @param instructorId instructor's unity id
		 * @param enrollmentCap the enrollment cap
		 * @param meetingDays meeting days for Course as series of chars
		 */
		public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
			//this(name, title, section, credits, instructorId, meetingDays, 0, 0);
			this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
		}
		
		/**
		 * Sets the meetingDays, startTime, and endTime for the Course using the
		 * method from the Activity class. Allows for meetingDays to be arranged.
		 * Overrides the setMeetingDaysAndTime method from Activity to work specifically for
		 * Course objects.
		 * @param meetingDays the meetingDays
		 * @param startTime the startTime
		 * @param endTime the endTime
		 * @throws IllegalArgumentException if meetingDays is invalid
		 */
		@Override
		public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
			if("A".equals(meetingDays)) { //Arranged
				if(startTime == 0 && endTime == 0) {
					super.setMeetingDaysAndTime(meetingDays, 0, 0);
				}
				else {
					//throws IllegalArgumentException if starting times or ending times
					//are more than 0 if meetingDays are arranged
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			else { //not arranged
				int mondayCount = 0;
				int tuesdayCount = 0;
				int wednesdayCount = 0;
				int thursdayCount = 0;
				int fridayCount = 0;
				
				for(int i = 0; i < meetingDays.length(); i++) {
					if (meetingDays.charAt(i) == 'M') {
						++mondayCount;
					}
					else if (meetingDays.charAt(i) == 'T') {
						++tuesdayCount;
					}
					else if (meetingDays.charAt(i) == 'W') {
						++wednesdayCount;
					}
					else if (meetingDays.charAt(i) == 'H') {
						++thursdayCount;
					}
					else if (meetingDays.charAt(i) == 'F') {
						++fridayCount;
					}
				
					else {
						//throws IllegalArgumentException if meetingDays is not M,T,W,H, or F
						throw new IllegalArgumentException("Invalid meeting days and times.");
					}
				}
				
				if(mondayCount > 1 || tuesdayCount > 1 || wednesdayCount > 1) {
					//throws IllegalArgumentException if there is a repeat M,T, or W
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				if(thursdayCount > 1 || fridayCount > 1) {
					//throws IllegalArgumentException if there is a repeat H or F
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
			}
		}	
				
		/**
		 * Returns a comma separated value String of all Course fields.
		 * Overrides the toString method to work specifically for Course objects.
		 * @return String representation of Course
		 */
		@Override
		public String toString() {
		    if ("A".equals(getMeetingDays())) {
		        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays();
		    }
		    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
		}
		
		/** Returns an array that populates the rows of the course catalog and student schedule 
		 * for a Course.
		 * Overrides the getShortDisplayArray method in Activity to work for Courses 
		 * @return the shortDisplayArray
		 */
		@Override
		public String[] getShortDisplayArray() {
			String[] shortDisplayArray = new String[5];
			shortDisplayArray[0] = name;
			shortDisplayArray[1] = section;
			shortDisplayArray[2] = getTitle();
			shortDisplayArray[3] = getMeetingString();
			shortDisplayArray[4] = Integer.toString(roll.getOpenSeats());
			return shortDisplayArray;
		}
		
		
		/** Displays the final schedule for a Course
		 * Overrides the getLongDisplayArray method in Activity to work for Courses
	     * @return the longDisplayArray
		 */
		@Override
		public String[] getLongDisplayArray() {
			String[] longDisplayArray = new String[7];
			longDisplayArray[0] = name;
			longDisplayArray[1] = section;
			longDisplayArray[2] = getTitle();
			longDisplayArray[3] = Integer.toString(credits);
			longDisplayArray[4] = instructorId;
			longDisplayArray[5] = getMeetingString();
			longDisplayArray[6] = "";
			return longDisplayArray;
		}
		
		
		/**
		 * Checks if given Activity is a Course, and if so, if it is a duplicate
		 * of another Course by checking their names
		 * Overrides the isDuplicate method in the Activity method to work specifically
		 * for Courses.
		 * @return true if Course is a duplicate
		 */
		@Override
		public boolean isDuplicate(Activity activity) {
			return activity instanceof Course && ((Course) activity).getName().equals(name);
		}
		
		/**
		 * Compares 2 courses to see if they are duplicates of one another
		 * 
		 * @param c for the course that is being checked
		 */
		@Override
		public int compareTo(Course c) {
			int compareValue = 0;
			if (this.getName().compareTo(c.getName()) > 0) {
				compareValue = 1;
			}
			else if (this.getName().compareTo(c.getName()) < 0) {
				compareValue = -1;
			}
			else if (this.getName().compareTo(c.getName()) == 0) {
				if (this.getSection().compareTo(c.getSection()) > 0) {
					compareValue = 1;
				}
				else if (this.getSection().compareTo(c.getSection()) < 0) {
					compareValue = -1;
				}
				else if (this.getSection().compareTo(c.getSection()) == 0) {
					compareValue = 0;
				}
			}	
			return compareValue;
		}
		
		/**
		 * Gets the course roll
		 * @return roll, the roll for the course
		 */
		public CourseRoll getCourseRoll() {
			return roll;
		}
		
}

