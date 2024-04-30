/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests Linked List Class
 * @author Joseph Carrasco
 *
 */
class LinkedListTest {

	/**
	 * Tests the LinkedList Constructor
	 */
	@Test
	void testLinkedList() {
		LinkedList<String> list = new LinkedList<String>();
		
		assertEquals(0, list.size());
	}

	/**
	 * Tests the LinkedList.add() method
	 */
	@Test
	void testAdd() {
		LinkedList<String> list = new LinkedList<String>();
		
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
		
		assertThrows(IllegalArgumentException.class, () -> list.add("4"));
		
	}
	
	/**
	 * Tests the LinkedList Size
	 */
	@Test
	void testSize() {
		LinkedList<String> list = new LinkedList<String>();
		
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
		LinkedList<String> list = new LinkedList<String>();
		
		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, "1"));
		
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		assertEquals(3, list.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "4"));
		
		assertEquals("2", list.set(1, "4"));
		assertEquals("4", list.get(1));
		
		assertThrows(IllegalArgumentException.class, () -> list.set(1, "4"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "0"));
		assertThrows(NullPointerException.class, () -> list.set(0, null));	}
	
	/*
	 * Tests the LinkedList Get
	 */
	@Test
	void testGet() {
		LinkedList<String> list = new LinkedList<String>();
		
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
		LinkedList<String> list = new LinkedList<String>();
		
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
	
	/**
	 * Tests the ListIterator Class
	 */
	@Test
	void testLinkedIterator() {
		LinkedList<String> list = new LinkedList<String>();
		ListIterator<String> iterator = list.listIterator(0);
		
		assertTrue(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
		assertThrows(NoSuchElementException.class, () -> iterator.previous());
		assertThrows(IllegalStateException.class, () -> iterator.set("1"));
	}
}
