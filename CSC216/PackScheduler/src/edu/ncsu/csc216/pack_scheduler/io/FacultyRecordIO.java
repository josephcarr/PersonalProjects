/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;




/**
 * Read and write faculty records
 * This class will read and process faculty members to add them
 * to the faculty directory. Furthermore, it can write faculty
 * members to a file as well.
 * 
 * @author aahayles
 * @author Shreya Holikatti
 */
public class FacultyRecordIO {
	
	/**
	 * Processes the Faculties lines in the file by separating each String with 
	 * a comma. Creates a Faculty object from these Strings and the maxCredits.
	 * @param string The line in the file that represents a student
	 * @return record the student's record
	 * @throws IllegalArgumentException when file is invalid
	 */
	private static Faculty processFaculty(String string) {
		Scanner fileReader = new Scanner(string);
		fileReader.useDelimiter(",");
		try {
			String firstName = fileReader.next(); 
			String lastName = fileReader.next();
			String id = fileReader.next();
			String email = fileReader.next();
			String hashedPassword = fileReader.next();
			int maxCourses = fileReader.nextInt();
			
			Faculty record = new Faculty(firstName, lastName, id, email, hashedPassword, maxCourses);
			fileReader.close();
			return record; 
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Unexpected error reading ");
		}
	}


	/**
	 * Reads lines from the file that is provided, and creates an sorted list of student objects from 
	 * each line of the file.
	 * @param fileName the name of the file
	 * @return the SortedList of students
	 * @throws FileNotFoundException if file isn't found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> faculties = new LinkedList<Faculty>(); 
	    while (fileReader.hasNextLine()) { 
	        try {
	        	Faculty faculty = processFaculty(fileReader.nextLine()); 

	            boolean duplicate = false;
	            for (int i = 0; i < faculties.size(); i++) {
	            	User current = faculties.get(i);
	                if (faculty.getFirstName().equals(current.getFirstName()) &&
	                		faculty.getPassword().equals(current.getPassword())) {
	                    duplicate = true;
	                    break; 
	                }
	            }
	            if (!duplicate) {
	            	faculties.add(faculty); 
	            } 
	        } catch (IllegalArgumentException e) {
	        	//Skips because Line is invalid.
	        }
	    }
	    fileReader.close();
	    return faculties;

	}

	/**
	 * Writes the students in the FacultyDirectory to a file.
	 * @param fileName the name of the File
	 * @param facultyDirectory the Directory of Faculties
	 * @throws IOException if unable to write to a file.
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < facultyDirectory.size(); i++) {
    	    fileWriter.println(facultyDirectory.get(i).toString());
    	}

    	fileWriter.close(); 
		
	}

}


