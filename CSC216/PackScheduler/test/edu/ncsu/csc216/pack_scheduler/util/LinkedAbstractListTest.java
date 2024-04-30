/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for LinkedAbstractList.
 * @author Joseph Carrasco
 * @author Faith Tolliver
 *
 */
class LinkedAbstractListTest {

	/**
	 * Tests the LinkedAbstractList Constructor
	 */
	@Test
	void testArrayList() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		
		assertEquals(0, list.size());
		
		//LinkedAbstractList<String> list1 = new LinkedAbstractList<String>(0);
		assertThrows(IllegalArgumentException.class, () -> new LinkedAbstractList<String>(-1));



	}

	/**
	 * Tests the LinkedAbstractList.add() method
	 */
	@Test
	void testAdd() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, "1"));
		
		list.add(0, "1");
		assertEquals(1, list.size());
		assertEquals("1", list.get(0));
		
		assertThrows(NullPointerException.class, () -> list.add(0, null));
		
		list.add(1, "2");
		assertEquals(2, list.size());
		assertEquals("2", list.get(1));
		
		list.add(2, "3");
		assertEquals(3, list.size());
		assertEquals("3", list.get(2));
		
		list.add(1, "4");
		assertEquals(4, list.size());
		assertEquals("4", list.get(1));

		
	}
	
	/**
	 * Tests the LinkedAbstractList remove method
	 */
	@Test
	void testRemove() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);

		//LinkedAbstractList list = new LinkedAbstractList(5);
		
		list.add(0, "3");
		list.add(1, "2");
		list.add(2, "5");
		list.add(3, "4");
		
		assertEquals(4, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));
		
		assertEquals("2", list.remove(1));
		assertEquals("4", list.remove(2));
		assertEquals("3", list.remove(0));
		
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> list.remove(12));
	}
	
	/**
	 * Tests the LinkedAbstractList Size
	 */
	@Test
	void testSize() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		
		
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
		//assertEquals(3, list.set(2, "Hello"));
		//assertEquals("Hello", list.get(2));
		assertThrows(NullPointerException.class, () -> list.set(5, null));
		assertThrows(IllegalArgumentException.class, () -> list.add(11, "11"));
		//assertThrows(IllegalArgumentException.class, () -> list.set(2, "Hello"));
		assertEquals(10, list.size());
		
	}
	
	/*
	 * Tests the ArrayList Set
	 */
	@Test
	void testSet() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		
		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, "1"));
		
		list.add(0, "orange");
		list.add(1, "apple");
		list.add(2, "banana");
		list.add(3, "kiwi");
		assertEquals("apple", list.set(1, "4"));
		assertEquals("4", list.get(1));
		
		assertEquals("orange", list.set(0, "blackberry"));
		assertEquals("blackberry", list.get(0));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "0"));
		assertThrows(NullPointerException.class, () -> list.set(0, null));	}
	
	/*
	 * Tests the ArrayList Get
	 */
	@Test
	void testGet() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(10);
		
		assertEquals(0, list.size());
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		assertEquals(3, list.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
	}

}
