/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
<<<<<<< HEAD
 * Tests the FacultyRecordIO Class
=======
 * Tests the FacultyRecordIO class
 * 
>>>>>>> branch 'main' of https://github.ncsu.edu/engr-csc216-spring2022/csc217-LLL-214-6.git
 * @author aahayles
 *
 */
public class FacultyRecordIOTest {

	/** Valid faculty records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid faculty records */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
	/** Strings of valid Faculty */
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	/** Strings of valid Faculty */
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	/** Strings of valid Faculty */
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	/** Strings of valid Faculty */
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	/** Strings of valid Faculty */
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	/** Strings of valid Faculty */
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	/** Strings of valid Faculty */
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	/** Strings of valid Faculty */
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";
	/** Array of valid faculty Strings */
	private String [] validFaculties = {validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
	        validFaculty5, validFaculty6, validFaculty7};

	/** The Faculty's hash Password */
	private String hashPW;
	/** The String of the algorithm for Hash */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Sets up the hashPassword, fails if unable to create hash
	 */
	@BeforeEach
	public void setUp() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = Base64.getEncoder().encodeToString(digest.digest());
	        
	        for (int i = 0; i < validFaculties.length; i++) {
	            validFaculties[i] = validFaculties[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}

	/**
	 * Tests the readFacultyRecords method, fails if error reading file.
	 */
	@Test
	void testReadValidFacultyRecords() {
		try {
			LinkedList<Faculty> faculties = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, faculties.size());
			
			for (int i = 0; i < faculties.size(); i++) {
				assertEquals(validFaculties[i], faculties.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}
	
	/**
	 * Test readFacultyRecords with an invalid test file
	 */
	@Test
	void testReadInvalidFacultyRecords() {
		try {
			LinkedList<Faculty> faculties = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, faculties.size());

			}
		catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests the writeFacultyRecords method, fails if file cannot be written to.
	 */
	@Test
	void testWriteFacultyRecords() {
		LinkedList<Faculty> faculties = new LinkedList<Faculty>();
		faculties.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));
		
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculties);
		} catch (IOException e) {
			fail("Cannot write to faculty records file");
		}
		
		
	}
	
	/**
	 * Tests the writeFacultyRecords method with no permissions.
	 */
	@Test
	public void testWriteFacultyRecordsNoPermissions() {
		LinkedList<Faculty> faculties = new LinkedList<Faculty>();
		faculties.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));
		
		Exception exception = assertThrows(IOException.class, 
				() -> FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_faculty_records.txt", faculties));
		assertEquals("/home/sesmith5/actual_faculty_records.txt (Permission denied)", exception.getMessage());
	}

}


