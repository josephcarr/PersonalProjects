/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;


/**
 * Creates the exception type ConflictException, which throws whenever two activities' 
 * times overlap and take place on the same day and the same time. 
 * @author cbdocke2
 *
 */
public interface Conflict {
	/**
	 * Checks if an activity takes place at the same time and day of another, previously added
	 * activity.
	 * @param possibleConflictingActivity the Activity that is being checked
	 * @throws ConflictException whenever an activity overlaps with a previously added activity.
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
