package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads a set of students from a file, writes the students in the student directory to
 * a file.
 * @author cbdocke2
 * @author dccicin
 * @author fttolliv
 * @author Sarah Heckman
 *
 */
public class StudentRecordIO {
	
	/**
	 * Processes the Students lines in the file by separating each String with 
	 * a comma. Creates a Student object from these Strings and the maxCredits.
	 * @param string The line in the file that represents a student
	 * @return record the student's record
	 * @throws IllegalArgumentException when file is invalid
	 */
	private static Student processStudent(String string) {
		Scanner fileReader = new Scanner(string);
		fileReader.useDelimiter(",");
		try {
			String firstName = fileReader.next(); 
			String lastName = fileReader.next();
			String id = fileReader.next();
			String email = fileReader.next();
			String hashedPassword = fileReader.next();
			int maxCredits = fileReader.nextInt();
			
			Student record = new Student(firstName, lastName, id, email, hashedPassword, maxCredits);
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
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<Student>(); 
	    while (fileReader.hasNextLine()) { 
	        try {
	        	Student student = processStudent(fileReader.nextLine()); 

	            boolean duplicate = false;
	            for (int i = 0; i < students.size(); i++) {
	            	User current = students.get(i);
	                if (student.getFirstName().equals(current.getFirstName()) &&
	                		student.getPassword().equals(current.getPassword())) {
	                    duplicate = true;
	                    break; 
	                }
	            }
	            if (!duplicate) {
	            	students.add(student); 
	            } 
	        } catch (IllegalArgumentException e) {
	        	//Skips because Line is invalid.
	        }
	    }
	    fileReader.close();
	    return students;

	}

	/**
	 * Writes the students in the StudentDirectory to a file.
	 * @param fileName the name of the File
	 * @param studentDirectory the Directory of Students
	 * @throws IOException if unable to write to a file.
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < studentDirectory.size(); i++) {
    	    fileWriter.println(studentDirectory.get(i).toString());
    	}

    	fileWriter.close(); 
		
	}

}
