/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for ArrayList.
 * @author Joseph Carrasco
 * @author Faith Tolliver
 *
 */
class ArrayListTest {

	/**
	 * Tests the ArrayList Constructor
	 */
	@Test
	void testArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		
		assertEquals(0, list.size());
	}

	/**
	 * Tests the ArrayList.add() method
	 */
	@Test
	void testAdd() {
		ArrayList<String> list = new ArrayList<String>();
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, "1"));
		
		list.add(0, "1");
		assertEquals(1, list.size());
		assertEquals("1", list.get(0));
		
		assertThrows(NullPointerException.class, () -> list.add(0, null));
		
		list.add(1, "2");
		assertEquals(2, list.size());
		assertEquals("2", list.get(1));
		
		list.add(0, "3");
		assertEquals(3, list.size());
		assertEquals("3", list.get(0));
		
		list.add(1, "4");
		assertEquals(4, list.size());
		assertEquals("4", list.get(1));
		
	}
	
	/**
	 * Tests the ArrayList Size
	 */
	@Test
	void testSize() {
		ArrayList<String> list = new ArrayList<String>();
		
		assertEquals(0, list.size());
		list.add(0, "1");
		assertEquals(1, list.size());
		list.add(1, "2");
		assertEquals(2, list.size());
		
	}
	
	/*
	 * Tests the ArrayList GrowArray
	 */
	@Test
	void testGrowArray() {
		ArrayList<String> list = new ArrayList<String>();
		
		assertEquals(0, list.size());
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		list.add(3, "4");
		list.add(4, "5");
		list.add(5, "6");
		list.add(6, "7");
		list.add(7, "8");
		list.add(8, "9");
		list.add(9, "10");
		assertEquals(10, list.size());
		list.add(10, "11");
		assertEquals(11, list.size());
		
	}
	
	/*
	 * Tests the ArrayList Set
	 */
	@Test
	void testSet() {
		ArrayList<String> list = new ArrayList<String>();
		
		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, "1"));
		
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		assertEquals(3, list.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "4"));
		
		assertEquals("2", list.set(1, "4"));
		assertEquals("4", list.get(1));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "0"));
		assertThrows(NullPointerException.class, () -> list.set(0, null));	}
	
	/*
	 * Tests the ArrayList Get
	 */
	@Test
	void testGet() {
		ArrayList<String> list = new ArrayList<String>();
		
		assertEquals(0, list.size());
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		assertEquals(3, list.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
	}
	
	/*
	 * Tests the ArrayList Remove
	 */
	@Test
	void testRemove() {
		ArrayList<String> list = new ArrayList<String>();
		
		assertEquals(0, list.size());
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		assertEquals(3, list.size());
		
		list.remove(1);
		assertEquals(2, list.size());
		assertEquals("1", list.get(0));
		assertEquals("3", list.get(1));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));
	}
	
}
