package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Maintains a directory of all students enrolled at NC State in alphabetical order. Loads students from file, 
 * as long as file can be found. Add a student if given all fields needed to create a student object in correct 
 * place in alphabetical order. Can remove student from directory if given student id. Return student directory
 * in 2D array with each row being a student and the columns are as listed first name, last name, and 
 * student's id.
 * All students have a unique id. So two students cannot be exactly the same when being put into the directory
 * @author Sarah Heckman
 * @author Braeden Coughlin
 */
public class StudentDirectory {
	
	/** List of students in the directory */
	private SortedList<Student> studentDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Creates an empty student directory.
	 */
	public StudentDirectory() {
		newStudentDirectory();
	}
	
	/**
	 * Creates an empty student directory.  All students in the previous
	 * list are list unless saved by the user.
	 */
	public void newStudentDirectory() {
		studentDirectory = new SortedList<Student>();
	}
	
	/**
	 * Constructs the student directory by reading in student information
	 * from the given file.  Throws an IllegalArgumentException if the 
	 * file cannot be found.
	 * @param fileName file containing list of students
	 * @throws IllegalArgumentException if file cannot be read
	 */
	public void loadStudentsFromFile(String fileName) {
		try {
			studentDirectory = StudentRecordIO.readStudentRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	
	/**
	 * Adds a Student to the directory.  Returns true if the student is added and false if
	 * the student is unable to be added because their id matches another student's id.
	 * 
	 * This method also hashes the student's password for internal storage.
	 * 
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param id student's id
	 * @param email student's email
	 * @param password student's password
	 * @param repeatPassword student's repeated password
	 * @param maxCredits student's max credits.
	 * @throws IllegalArgumentException if passwords do not match, are empty, or are null
	 * @return true if added
	 */
	public boolean addStudent(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCredits) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}
		
		hashPW = hashString(password);
		repeatHashPW = hashString(repeatPassword);
		
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		//If an IllegalArgumentException is thrown, it's passed up from Student
		//to the GUI
		Student student = null;
		if (maxCredits < 3 || maxCredits > Student.MAX_CREDITS) {
			student = new Student(firstName, lastName, id, email, hashPW);
		} else {
			student = new Student(firstName, lastName, id, email, hashPW, maxCredits);
		}
		
		for (int i = 0; i < studentDirectory.size(); i++) {
			Student s = studentDirectory.get(i);
			if (s.getId().equals(student.getId())) {
				return false;
			}
		}
		return studentDirectory.add(student);
	}
	
	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
	 * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash 
	 * @throws IllegalArgumentException if password cannot be hashed.
	 * @return the encoded digest of the hash algorithm in base64
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Removes the student with the given id from the list of students with the given id.
	 * Returns true if the student is removed and false if the student is not in the list.
	 * @param studentId student's id
	 * @return true if removed
	 */
	public boolean removeStudent(String studentId) {
		for (int i = 0; i < studentDirectory.size(); i++) {
			Student s = studentDirectory.get(i);
			if (s.getId().equals(studentId)) {
				studentDirectory.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns all students in the directory with a column for first name, last name, and id.
	 * @return String array containing students first name, last name, and id.
	 */
	public String[][] getStudentDirectory() {
		String [][] directory = new String[studentDirectory.size()][3];
		for (int i = 0; i < studentDirectory.size(); i++) {
			Student s = studentDirectory.get(i);
			directory[i][0] = s.getFirstName();
			directory[i][1] = s.getLastName();
			directory[i][2] = s.getId();
		}
		return directory;
	}
	
	/**
	 * Saves all students in the directory to a file.
	 * @param fileName name of file to save students to.
	 * @throws IllegalArgumentException if file cannot be written to
	 */
	public void saveStudentDirectory(String fileName) {
		try {
			StudentRecordIO.writeStudentRecords(fileName, studentDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
	
	/**
	 * Gets a student from the directory by the specified id
	 * @param id the student id
	 * @return the Student with specified ID
	 * @throws IllegalArgumentException if the student id is null
	 */
	public Student getStudentById(String id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < studentDirectory.size(); i++) {
			if (id.equals(studentDirectory.get(i).getId())) {
				return studentDirectory.get(i);
			}
		}
		
		return null;
	}

}