/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the ConflictException class.
 * 
 * @author cbdocke2
 *
 */
class ConflictExceptionTest {

	/**
	 * Test construction of a conflictException with the default message.
	 */
	@Test
	void testConflictException() {
		//Creates a conflictException to test if the message is equal to the default.
		ConflictException ce = new ConflictException();
		assertEquals("Schedule conflict.", ce.getMessage());
	}

	/**
	 * Tests construction of a conflictException with a custom message
	 */
	@Test
	public void testConflictExceptionString() {
	    //Creates a conflictException to test if the message is equal to the custom message.
		ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

}
