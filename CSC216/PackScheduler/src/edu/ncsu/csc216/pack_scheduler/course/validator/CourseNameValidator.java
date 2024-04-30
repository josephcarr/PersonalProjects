/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Validates a given string as a proper course name using a FSM. A valid course
 * name is given by (1-4 letters)(3 digits)(optionally, a 1 letter suffix)
 * 
 * @author Vineet Krishna
 * @author Shreya Holikatti
 */
public class CourseNameValidator {
	/** Constant string for initial state */
	public static final String INITIAL = "Initial";
	/** Constant string for letter state */
	public static final String LETTER = "Letter";
	/** Constant string for Suffix state */
	public static final String SUFFIX = "Suffix";
	/** Constant string for initial state */
	public static final String NUMBER = "Number";


	/** if at valid end state */
	private boolean validEndState;
	/** the letter count */
	private int letterCount;
	/** the digit count */
	private int digitCount;
	
	/** the current state */
	private State state;
	///** final instance of InitialState */
	//private final InitialState initial = new InitialState();
	/** final instance of LetterState */
	private final LetterState letter = new LetterState();
	/** final instance of NumberState */
	private final NumberState number = new NumberState();
	/** final instance of SuffixState */
	private final SuffixState suffix = new SuffixState();
	

	/**
	 * The method checks if the name of the course is valid. No spaces or dashes in between the letters
	 * and digits. Makes sure there is no less than 1 letter and no more than four letters followed by 
	 * exactly 3 digits.
	 * @param name the course's name
	 * @return true if course name is valid, false if not
	 * @throws InvalidTransitionException if course is an invalid parameter
	 */ 
	public boolean isValid(String name) throws InvalidTransitionException {
		state = new InitialState();
		for (int i = 0; i < name.length(); ++i) {
			Character c = (Character) name.charAt(i);
			if (Character.isLetter(c)) {
				state.onLetter();
			} else if (Character.isDigit(c)) {
				state.onDigit();
			} else {
				state.onOther();
			}
		}
		
		//state = initial;
		return validEndState;
	}
	
	
	/**
	 * Determines the current state of the CourseNameValidator by checking if the course name
	 * is currently on a letter, digit, or on other.
	 */ 
	public abstract class State {
		
		/**
		 * Constructor for the state class
		 */
		public State() {
//			letterCount = 0;
//			digitCount = 0;
//			validEndState = false;
		}
		
		/** Updates the state for being on letter 
		 * @throws InvalidTransitionException if being on a letter is invalid in the current state */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/** Updates the state for being on digit 
		 * @throws InvalidTransitionException if being on a digit is invalid in the current state */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/** 
		 * Updates the state for being on other.
		 * @throws InvalidTransitionException always
		 */
		public void onOther() throws InvalidTransitionException {
			state = new InitialState();
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	/**
	 * Represents the letter state in the FSM.
	 */
	public class LetterState extends State {
		/** maximum number of prefix letters */
		private static final int MAX_PREFIX_LETTERS = 4;
		
		/**
		 * Creates a LetterState.
		 */
		private LetterState() {
			super();
			//letterCount = 0;
		}
		
		/**
		 * The method checks to make sure there isn't more than 4 letters at the beginning of the name.
		 * @throws InvalidTransitionException if there are too many letters
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			letterCount++;
			if (letterCount > MAX_PREFIX_LETTERS) {
				state = new InitialState();
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}

		/**
		 * Updates the state for being on digit.
		 */
		@Override
		public void onDigit() {
			digitCount++;
			state = number;
		}
	}
	
	/**
	 * Represents the suffix state in the FSM 
	 */
	public class SuffixState extends State {
		
		/**
		 * Creates a SuffixState.
		 */
		private SuffixState() {
			super();
		}

		/**
		 * Updates the state for being on letter.
		 * @throws InvalidTransitionException if it is a letter in the suffix state
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			state = new InitialState();
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * Updates the state for being on digit.
		 * @throws InvalidTransitionException if it is a digit in the suffix state
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			state = new InitialState();
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
	
	/**
	 * Represents the initial state of the FSM.
	 */
	public class InitialState extends State {

		/**
	     * The State that represents the first character of the Course Name, which can only be a letter.
		 */
		private InitialState() {
			super();
			letterCount = 0;
			digitCount = 0;
			validEndState = false;
		}
		
		/**
		 * Updates the state for being on letter.
		 */
		@Override
		public void onLetter() {
			letterCount++;
			state = letter;
		}

		/**
		 * Throws exception since name cannot start with a digit
		 * @throws InvalidTransitionException if method is ran
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}
	
	/**
	 * Represents the number state in the FSM.
	 */
	public class NumberState extends State {
		/** the course number length */
		private static final int COURSE_NUMBER_LENGTH = 3;
		
		/**
		 * Creates a NumberState.
		 */
		private NumberState() {
			super();
			//digitCount = 0;
		}

		/**
		 * Updates the state for being on letter.
		 * @throws InvalidTransitionException when digit count is not the correct value
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				validEndState = true;
				state = suffix;
			} else {
				state = new InitialState();
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}

		/**
		 * Updates the state for being on digit.
		 * @throws InvalidTransitionException when digit count is greater than the correct value
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			digitCount++;
			if (digitCount == COURSE_NUMBER_LENGTH) {
				validEndState = true;
			} else if (digitCount > COURSE_NUMBER_LENGTH) {
				state = new InitialState();
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
	}
}
