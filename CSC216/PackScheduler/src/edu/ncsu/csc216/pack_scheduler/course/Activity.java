package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

/**
 * Creates an activity with the title, the meeting days, the starting time,
 * and the ending time. Activity can then become either a Course object or 
 * and Event Object.
 *
 * @author cbdocke2
 *
 */
public abstract class Activity implements Conflict {

	/** Activity's title. */
	protected String title;
	/** maximum number of hours */
	private static final int UPPER_HOUR = 24;
	/** maximum number of minutes */
	private static final int UPPER_MINUTE = 60;
	/** latest possible military time */
	private static final int LATEST_TIME = 2359;
	/** time when AM switches to PM */
	private static final int NOON_TIME = 12;
	/** amount of minutes when a leading 0 is not needed */
	private static final int TEN_MINUTE_MARK = 10;
	/** Activity's meeting days */
	private String meetingDays;
	/** Returns an array that populates the rows of the course catalog and student schedule 
	 * for the activities.
	 * @return the shortDisplayArray
	 */
	public abstract String[] getShortDisplayArray();
	/** Displays the final schedule of Activities
	 * @return the longDisplayArray
	 */
	public abstract String[] getLongDisplayArray();
	/**
	 * Checks for duplicate activities
	 * @param activity the activity object
	 * @return true if activity is a duplicate
	 */
	public abstract boolean isDuplicate(Activity activity);
	/**
	 * Returns the Activity's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity's title. If title is null or contains no characters, 
	 * an IllegalArgumentException is thrown
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is invalid
	 */
	public void setTitle(String title) {
		//throw exception if title is null or contains no characters
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		
		this.title = title;
	}

	/**
	 * Returns the Activity's meeting days
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Activity's starting time
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Activity's ending time
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Checks if this instance of Activity overlaps with the given Activity by comparing 
	 * the meetingDays and the startTime and endTime.
	 * Overrides the checkConflict exception to work for activities
	 * @param possibleConflictingActivity the activity that is checked to conflict with this activity.
	 * @throws ConflictException if the added Activity overlaps with an Activity in schedule.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		Activity newActivity = this; // new activity
		boolean conflict = false; // boolean looking for conflict
		
		String daysPCA = possibleConflictingActivity.getMeetingDays(); //sets the days for possibleConflictingActivity
		String daysNA = newActivity.getMeetingDays(); // sets the days for newActivity
		String checkPCA = ""; // string that will be used to check characters of the meeting days string for possibleConflictingActivity
		String checkNA = ""; // string that will be used to check characters of the meeting days string for newActivity
		for(int i = 0; i < daysPCA.length(); i++) { // for loop that will check if the days are the same
			if(i < daysPCA.length()) { 
				checkPCA = "" + daysPCA.charAt(i); // takes a character from the sting if it is able(if statement)
			}
			if(i < daysNA.length()) {
				checkNA = "" + daysNA.charAt(i); // takes a character from the sting if it is able(if statement)
			}
			if((!("A".equals(daysPCA)) || !("A".equals(daysNA))) && (daysPCA.contains(checkNA) || daysNA.contains(checkPCA))){
				conflict = true; //makes conflict true if the days aren't arranged and if they have the same days
			} 
		}
		int startPCA = possibleConflictingActivity.getStartTime(); //sets the start time for possibleConflictingActivity
		int endPCA = possibleConflictingActivity.getEndTime(); //sets the end time for possibleConflictingActivity
		int startNA = newActivity.getStartTime(); //sets the start time for newActivity
		int endNA = newActivity.getEndTime(); //sets the end time for newActivity
		if(conflict) { //only runs if there is a conflict
			if (startPCA <= endNA && startPCA >= startNA) { // checks if an activity starts and ends in the middle of another
				throw new ConflictException("Schedule conflict.");
			}
			if (startNA <= endPCA && startNA >= startPCA) { // checks if an activity starts and ends in the middle of another
				throw new ConflictException("Schedule conflict.");
			}
		}
	}
	/**
	 * Sets the Activity's meeting days, start time,
	 * and ending time if applicable.
	 * @param meetingDays the meetingDays to set
	 * @param startTime the startTime to set
	 * @param endTime the endTime to set
	 * @throws IllegalArgumentException if meetingDays is invalid
	 * @throws IllegalArgumentException if startTime or endTime are invalid
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		//throws IllegalArgumentException if meetingDays is null or an empty string
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}		
			if (startTime > endTime) {
				//throws IllegalArgumentException if startTime is greater than endTime
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (startTime < 0 || startTime > LATEST_TIME) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endTime < 0 || endTime > LATEST_TIME) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			//breaks startTime and endTime into hours and minutes
			int startHour = startTime / 100;
			int startMin = startTime % 100;
			int endHour = endTime / 100;
			int endMin = endTime % 100;
			
			if (startHour < 0 || startHour > UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (startMin < 0 || startMin >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endHour < 0 || endHour > UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endMin < 0 || endMin >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			this.meetingDays = meetingDays;
			this.startTime = startTime;
			this.endTime = endTime;
		}

	/**
	 * Creates a String of the meeting days, starting time
	 * and ending time in standard time 
	 * @return meetingString String that represents days and 
	 * times of meeting if applicable, otherwise Arranged.
	 */
	public String getMeetingString() {
		int startHour = startTime / 100;
		int startMin = startTime % 100;
		int endHour = endTime / 100;
		int endMin = endTime % 100;
		boolean startPostMeridiem = false;
		boolean endPostMeridiem = false;
		boolean startOverNineMinutes = false;
		boolean endOverNineMinutes = false;
		String meetingString = "";
		
		if (startHour >= NOON_TIME) {
			startHour = startHour - NOON_TIME;
			startPostMeridiem = true;
		}
		if (endHour >= NOON_TIME) {
			endHour = endHour - NOON_TIME;
			endPostMeridiem = true;
		}
		if (startMin >= TEN_MINUTE_MARK) {
			startOverNineMinutes = true;
		}
		if (endMin >= TEN_MINUTE_MARK) {
			endOverNineMinutes = true;
		}
		if (startHour == 0) {
			startHour = NOON_TIME;
		}
		if (endHour == 0) {
			endHour = NOON_TIME;
		}
		if ("A".equals(meetingDays)) {
			meetingString = "Arranged";
		}
		else {
			meetingString = meetingDays + " " + startHour + ":";
			if (startOverNineMinutes) {
				meetingString += startMin;
				if (startPostMeridiem) {
					meetingString += "PM-";
				}
				if (!startPostMeridiem) {
					meetingString += "AM-";
				}
			}
			if (!startOverNineMinutes) {
				meetingString += "0" + startMin;
				if (startPostMeridiem) {
					meetingString += "PM-";
				}
				if (!startPostMeridiem) {
					meetingString += "AM-";
				}
			}
			meetingString += endHour + ":";
			if (endOverNineMinutes) {
				meetingString += endMin;
				if (endPostMeridiem) {
					meetingString += "PM";
				}
				if (!endPostMeridiem) {
					meetingString += "AM";
				}
			}
			if (!endOverNineMinutes) {
				meetingString += "0" + endMin;
				if (endPostMeridiem) {
					meetingString += "PM";
				}
				if (!endPostMeridiem) {
					meetingString += "AM";
				}
			}
		}
		return meetingString;
	}
	
	
	/**
	 * Generates a hashCode for Activity using endTime, startTime, meetingDays, and title.
	 * @return hashCoade for Activity
	 */
	@Override
	public int hashCode() {
		return Objects.hash(endTime, meetingDays, startTime, title);
	}

	/**
	 * Compares given object to this object
	 * @param obj the Object to compare
	 * @return true if objects are equal, false if not, and credits if other equals object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return endTime == other.endTime && Objects.equals(meetingDays, other.meetingDays)
				&& startTime == other.startTime && Objects.equals(title, other.title);
	}



	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;

	/**
	 * Constructs an Activity with the given title, meetingDays, startTime and endTime.
	 * @param title the title of the Course
	 * @param meetingDays the days the Course meets
     * @param startTime the Course's starting time
	 * @param endTime the Course's ending time
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	

}