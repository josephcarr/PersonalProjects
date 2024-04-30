package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * Test class that tests SortedList. Checks for all scenarios: adding, removing, empty,
 *  clearing, indexOf, hashCode, getting an element, equals, and contains.
 * @author faithtolliver
 * @author cbdocke2
 * @author dccicin
 *
 */
public class SortedListTest {

	/**
	 * Tests that the list grows 
	 */
	@Test
	public void testSortedList() {
		//Constructs valid sortedList
		SortedList<String> list = new SortedList<String>();
		
		//Checks list size is empty
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		assertTrue(list.isEmpty());
		
		//Adds elements to the list
		list.add("app");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("f");
		list.add("g");
		list.add("h");
		list.add("i");
		list.add("k");
		list.add("l");
		
		//Checks that the list now is the same size as the number of elements
		assertEquals(11, list.size());				
	}

	/**
	 * Tests adding an element to the list.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		//Adds elements to list and checks they are added in alphabetical order.
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		list.add("grape");
		assertEquals(3, list.size());
		assertEquals("grape", list.get(2));
		
		list.add("date");
		assertEquals(4, list.size());
		assertEquals("date", list.get(2));
		
		//Tries to add null element and checks for correct exception
		Exception exception = assertThrows(NullPointerException.class, 
			() -> list.add(null));
		assertEquals(null, exception.getMessage(), "Test an empty string being added.");
		
		//Tries to add duplicate element and checks for correct exception
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> list.add("date"));
		assertEquals("Element already in list.", e.getMessage(), "Test adding duplicate.");
	}
	
	/**
	 * Tests getting an element from the list.
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		//Tests get method on empty list
		Exception e = assertThrows(IndexOutOfBoundsException.class, 
				() -> list.get(0));
		assertEquals(null, e.getMessage(), "Test index out of bounds.");
		
		//Adds elements to list
		list.add("app");
		list.add("b");
		list.add("c");
		
		//Tests get with out of bounds indexes
		Exception e2 = assertThrows(IndexOutOfBoundsException.class, 
				() -> list.get(-2));
		assertEquals(null, e2.getMessage(), "Test index out of bounds.");
		
		Exception e3 = assertThrows(IndexOutOfBoundsException.class, 
				() -> list.get(list.size()));
		assertEquals(null, e3.getMessage(), "Test index out of bounds.");

		
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		
		
	}
	
	/**
	 * Tests removing an element from the list.
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		//Tests remove on an empty list
		Exception e = assertThrows(IndexOutOfBoundsException.class, 
				() -> list.remove(0)); //checks throws message is correct
		assertEquals(null, e.getMessage(), "Test index out of bounds.");
		
		//Adds elements to list
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		
		//Tests for an out of bounds index in remove
		Exception e2 = assertThrows(IndexOutOfBoundsException.class, 
				() -> list.remove(-2));
		assertEquals(null, e2.getMessage(), "Test index out of bounds.");

		Exception e3 = assertThrows(IndexOutOfBoundsException.class, 
				() -> list.remove(list.size()));
		assertEquals(null, e3.getMessage(), "Test index out of bounds.");
		
		//Removes element from list
		list.remove(2);
		
		//Checks list size after element is removed 
		assertEquals(3, list.size());
		//Checks correct element is at the index of the list
		assertEquals("e", list.get(2));
		
		//Removes element from list
		list.remove(2);
		//Checks list size after element is removed 
		assertEquals(2, list.size());
		//Checks correct element is at the index of the list
		assertEquals("c", list.get(1));
		
		//Removes element from list
		list.remove(0);
		
		//Checks list size after element is removed 
		assertEquals(1, list.size());
		//Checks correct element is at the index of the list
		assertEquals("c", list.get(0));
		
		//Removes element from list
		list.remove(0);
		
		//Checks list size after element is removed 
		assertEquals(0, list.size());
		
		
		
	
		
	}
	/**
	 * Tests the indexOf on an empty list
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		//Checks that the element doesn't appear in an empty list
		assertEquals(-1, list.indexOf(""));
		//Adds elements to list
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		//Tests for elements in the list
		assertEquals(0, list.indexOf("b"));
		assertEquals(3, list.indexOf("e"));
		assertEquals(1, list.indexOf("c"));
		assertEquals(2, list.indexOf("d"));
		//Tests for element not in the lsit
		assertEquals(-1, list.indexOf(""));
		
	}
	/**
	 * Tests that the list is empty after clearing
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		//Adds elements to list
		list.add("monday");
		list.add("tuesday");
		list.add("wednesday");
		list.add("thursday");
		list.add("friday");
		//Clears list
		list.clear();
		//Checks list is empty
		assertTrue(list.isEmpty());
	}

	/**
	 * Tests whether the list is empty
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		//Checks if list is empty
		assertTrue(list.isEmpty());
		//Adds elements to list
		list.add("name");
		list.add("name2");
		list.add("name3");
		//Checks list is not empty.
		assertFalse(list.isEmpty());
	}
	
	/**
	 * Tests whether the list contains elements
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		assertTrue(list.isEmpty());
		list.add("coffee");
		list.add("water");
		list.add("soda");
		//Tests contains method with elements not in the list
		assertFalse(list.contains("lemonade"));
		assertFalse(list.contains("nail polish remover"));
		//Tests contains method with elements in list
		assertTrue(list.contains("coffee"));
		assertTrue(list.contains("water"));
		
	}
	
	/**
	 * Testing equals method
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("money");
		list1.add("computer");
		list1.add("phone");
		
		list2.add("money");
		list2.add("computer");
		list2.add("phone");
		
		list3.add("necklace");
		list3.add("ring");
		list3.add("earrings");
		
		//compares equal lists
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		//compares lists that aren't equal
		assertFalse(list1.equals(list3));
		assertFalse(list2.equals(list3));
	}
	
	/**
	 * Testing hashCode method
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("hash");
		list1.add("code");
		list1.add("test");
		
		list2.add("csc216");
		list2.add("cscs226");
		list2.add("csc217");
		
		list3.add("hash");
		list3.add("code");
		list3.add("test");
		
		//compares equal lists
		assertTrue(list1.equals(list3));
		assertTrue(list3.equals(list1));
		//compares lists that aren't equal
		assertFalse(list1.equals(list2));
		assertFalse(list2.equals(list3));
	}

}
 