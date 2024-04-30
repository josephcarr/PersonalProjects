/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Creates the ConflictException, the exception that is thrown whenever an activity that is added
 * to the schedule overlaps with a previously added activity on the schedule.
 * @author cbdocke2
 *
 */
public class ConflictException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs the conflict exception with the default message "Schedule conflict."
	 * when thrown.
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

	
	/**
	 * Constructs the conflict exception to display the given message when thrown.
	 * @param message the message the exception displays when thrown
	 */
	public ConflictException(String message) {
		super(message);
	}

	


}

