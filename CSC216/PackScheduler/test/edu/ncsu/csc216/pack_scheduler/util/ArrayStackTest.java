/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests the ArrayStack class
 * @author Joseph Carrasco
 *
 */
class ArrayStackTest {

	/**
	 * Test method for ArrayStack.push().
	 */
	@Test
	void testPush() {
		ArrayStack<Integer> stack = new ArrayStack<Integer>(10);
		stack.setCapacity(5);
		
		stack.push(10);
		assertEquals(1, stack.size());
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		
		assertEquals(5, stack.size());
	}

	/**
	 * Test method for ArrayStack.pop().
	 */
	@Test
	void testPop() {
		ArrayStack<Integer> stack = new ArrayStack<Integer>(10);
		stack.setCapacity(5);
		
		stack.push(10);
		assertEquals(1, stack.size());
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		
		assertEquals(5, stack.size());
		
		assertEquals(4, stack.pop());
		assertEquals(4, stack.size());
		
		assertEquals(3, stack.pop());
		assertEquals(2, stack.pop());
		assertEquals(1, stack.pop());
		assertEquals(10, stack.pop());
		
		assertEquals(0, stack.size());
		
		assertThrows(EmptyStackException.class, () -> stack.pop());
	}

	/**
	 * Test method for ArrayStack.isEmpty().
	 */
	@Test
	void testIsEmpty() {
		ArrayStack<Integer> stack = new ArrayStack<Integer>(10);
		stack.setCapacity(5);
		
		assertTrue(stack.isEmpty());
		
		stack.push(10);		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		assertFalse(stack.isEmpty());
		
		assertEquals(4, stack.pop());		
		assertEquals(3, stack.pop());
		assertEquals(2, stack.pop());
		assertFalse(stack.isEmpty());
		assertEquals(1, stack.pop());
		assertEquals(10, stack.pop());
		
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
	}

	/**
	 * Test method for ArrayStack.setCapacity(int).
	 */
	@Test
	void testSetCapacity() {
		ArrayStack<Integer> stack = new ArrayStack<Integer>(10);
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(-1));
		
		stack.setCapacity(2);
		
		stack.push(10);		
		stack.push(1);
		
		assertThrows(IllegalArgumentException.class, () -> stack.push(2));
		
		stack.setCapacity(5);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		
		assertThrows(IllegalArgumentException.class, () -> stack.push(5));
	}

}
