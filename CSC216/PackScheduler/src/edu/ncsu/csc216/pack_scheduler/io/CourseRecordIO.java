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

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files into an SortedList of Course objects, and writes SortedLists
 * of Course objects to files to store as records.
 * Contains two primary methods, with one to read course records from a file line by line
 * using a helper method and the other to write course records to a file. Creates and contains
 * Course objects when reading course records.
 * 
 * @author Braeden Coughlin
 */
public class CourseRecordIO {


	/**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    SortedList<Course> courses = new SortedList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
	            Course course = readCourse(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the list!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the list with all the courses we read!
	    return courses;
	}
	

	/**
	 * Takes a String input which is a line of a file with Course records, and stores the
	 * Course information from that line into a Course object.
	 * 
	 * @param line the next line to be read from a file with Course records
	 * @return Course object containing information read from a line of a file with Course records
	 * @throws IllegalArgumentException if nextLine parameter is invalid
	 */
    private static Course readCourse(String line) {
    	Scanner in = new Scanner(line);
    	in.useDelimiter(",");

    	
    	try {
        	String name = in.next();
        	String title = in.next();
        	String section = in.next();
        	int credits = in.nextInt();
        	String instructorId = in.next();
        	int enrollmentCap = in.nextInt();
        	String meetingDays = in.next();
        	
        	Course c = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
        	RegistrationManager manager = RegistrationManager.getInstance();
        	
        	if ("A".equals(meetingDays)) {
        		if (in.hasNext()) {
        			in.close();
        			throw new IllegalArgumentException("Error");
        			
        		}
        		else {
        			in.close();
        			
        			if (manager.getFacultyDirectory().getFacultyById(instructorId) != null) {
        				manager.getFacultyDirectory().getFacultyById(instructorId).getSchedule().addCourseToSchedule(c);
        			}
        			
        			return c;
        		}
        	}
        	else {
        		int startTime = in.nextInt();
        		int endTime = in.nextInt();
        		if (in.hasNext()) {
        			in.close();
        			throw new IllegalArgumentException("Error");
        		}
        		in.close();
        		
        		c = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
        		
        		if (manager.getFacultyDirectory().getFacultyById(instructorId) != null) {
    				manager.getFacultyDirectory().getFacultyById(instructorId).getSchedule().addCourseToSchedule(c);
    			}
    			
    			return c;
        	}
    	} catch (NoSuchElementException e){
    		throw new IllegalArgumentException("Error"); 
    	}
    	

	}

	/**
	 * Writes the given list of Courses to file fileName. If the file cannot be written to because
	 * it already exists, throws an IOException.
	 * 
	 * @param fileName file to write schedule of Courses to
	 * @param courses list of Courses to write
	 * @throws IOException if file cannot be written to
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
		    fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();

	}

}
