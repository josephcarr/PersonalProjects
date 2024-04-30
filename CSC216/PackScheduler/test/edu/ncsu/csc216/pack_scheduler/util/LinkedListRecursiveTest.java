/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests LinkedListRecursive Class
 * @author Joseph Carrasco
 *
 */
class LinkedListRecursiveTest {

	/**
	 * Tests the LinkedListRecursive Constructor
	 */
	@Test
	void testLinkedListRecusive() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		
		assertEquals(0, list.size());
	}

	/**
	 * Tests the LinkedList.add() method
	 */
	@Test
	void testAdd() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, "1"));
		
		list.add("1");
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
		
		assertThrows(IllegalArgumentException.class, () -> list.add("4"));
		
	}
	
	/**
	 * Tests the LinkedList Size
	 */
	@Test
	void testSize() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		
		assertEquals(0, list.size());
		list.add(0, "1");
		assertEquals(1, list.size());
		list.add(1, "2");
		assertEquals(2, list.size());
		
	}
	
	/*
	 * Tests the LinkedList Set
	 */
	@Test
	void testSet() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		
		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, "1"));
		
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		assertEquals(3, list.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "4"));
		
		assertEquals("2", list.set(1, "4"));
		assertEquals("4", list.get(1));
		
		assertEquals("1", list.set(0, "6"));
		assertEquals("6", list.get(0));
		
		assertEquals("3", list.set(2, "7"));
		assertEquals("7", list.get(2));
		
		assertThrows(IllegalArgumentException.class, () -> list.set(1, "4"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "0"));
		assertThrows(NullPointerException.class, () -> list.set(0, null));
	}
	
	/*
	 * Tests the LinkedList Get
	 */
	@Test
	void testGet() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		
		assertEquals(0, list.size());
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		assertEquals(3, list.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
	}
	
	/*
	 * Tests the LinkedList Remove
	 */
	@Test
	void testRemove() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		
		assertEquals(0, list.size());
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		list.add("4");
		assertEquals(4, list.size());
		
		list.remove(1);
		assertEquals(3, list.size());
		assertEquals("1", list.get(0));
		assertEquals("3", list.get(1));
		assertEquals("4", list.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));
		
		assertTrue(list.remove("3"));
		assertEquals("1", list.get(0));
		assertEquals("4", list.get(1));
		
		LinkedListRecursive<String> list2 = new LinkedListRecursive<String>();
		
		list2.add("orange");
		list2.add("banana");
		list2.add("apple");
		list2.add("kiwi");
		
		assertEquals("banana", list2.remove(1));
		assertEquals("kiwi", list2.remove(2));
		assertEquals("orange", list2.remove(0));
	}
}
