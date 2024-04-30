/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests InvalidTransitionException.
 * 
 * @author Vineet Krishna
 *
 */
class InvalidTransitionExceptionTest {

	/**
	 * Test constructing InvalidTransitionException with default message.
	 */
	@Test
	public void testInvalidTransitionExceptionDefaultMessage() {
		InvalidTransitionException i = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", i.getMessage());
	}
	
	/**
	 * Test constructing InvalidTransitionException with custom message.
	 * 
	 */
	@Test
	public void testInvalidTransitionExceptionCustomMessage() {
		InvalidTransitionException i = new InvalidTransitionException("Hello world");
		assertEquals("Hello world", i.getMessage());
	}

}
