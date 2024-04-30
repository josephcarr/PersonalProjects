package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test method for CourseNameValidatorFSM
 * @author faithtolliver
 *
 */
class CourseNameValidatorFSMTest {
	
	/** Course name */
	private static final String COURSENAME = "CSCA116";

	/**
	 * Test the isValid() method in CourseNameValidatorFSM
	 */
	@Test
	void testIsValid() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		// test Course name can only contain letters and digits. invalid, exclamation
		try {
			assertFalse(c.isValid("CSC!!"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		try {
			assertFalse(c.isValid("CSC2276"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		// test Course name must start with a letter. valid, letter start
		// test Course name cannot start with more than 4 letters. valid, 4 digits
		try {
			assertTrue(c.isValid(COURSENAME));
		} catch (InvalidTransitionException e) {
			//skip
		}
		// test Course name must start with a letter. invalid, number start
		try {
			assertFalse(c.isValid("216CSC"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		// test Course name cannot start with more than 4 letters. invalid, +4 letters
		try {
			assertFalse(c.isValid("Computer"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		// test Course name must have 3 digits. invalid, 1 digits
		try {
			assertFalse(c.isValid("ml1"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		// test Course name must have 3 digits. invalid, 1 digits
		try {
			assertFalse(c.isValid("m1m"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		// test Course name must have 3 digits. invalid, 2 digits
		try {
			assertFalse(c.isValid("ml12k"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		// test Course name must have 3 digits. invalid, 2 digits
		try {
			assertFalse(c.isValid("mlk12"));
		} catch (InvalidTransitionException e) {
			fail();		
		}
		// test Course name can only have 3 digits. invalid, 4 digits
		try {
			assertFalse(c.isValid("CSC1234"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		// test Course name can only have 3 digits. invalid, 4 digits

		try {
			assertFalse(c.isValid("hsf6"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		// test Course name can only have a 1 letter suffix. valid, 1 letter
		try {
			assertTrue(c.isValid("CSC123b"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		// test Course name can only have a 1 letter suffix. invalid, 2 letters
		try {
			assertFalse(c.isValid("CSC123bb"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		// test Course name cannot contain digits after the suffix. invalid, digit after
		try {
			assertFalse(c.isValid("CSC123b445"));
			fail();
		} catch (InvalidTransitionException e) {
			//skip
		}
		
		try {
			assertTrue(c.isValid("E115"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		
		try {
			assertFalse(c.isValid("HSEF01"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}

}
