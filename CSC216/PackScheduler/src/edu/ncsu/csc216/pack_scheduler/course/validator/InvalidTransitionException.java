/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Exception that is called when there is an invalid FSM transition.
 * 
 * @author Vineet Krishna
 *
 */
public class InvalidTransitionException extends Exception {

	/**
	 * ID for serialization.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Generates InvalidTranstionException with default message "Invalid FSM Transition".
	 * 
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}
	
	/**
	 * Generates InvalidTransitionException with the given message.
	 * 
	 * @param message the message to display
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

}
