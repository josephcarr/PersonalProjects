/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the linked queue class
 * @author aahayles
 * @author Shreya Holikattii
 *
 */
public class LinkedQueueTest {

	/**
	 * Test method for Linkedstack.push().
	 */
	@Test
	void testEnqueue() {
		LinkedQueue<Integer> queue = new LinkedQueue<Integer>(10);
		assertTrue(queue.isEmpty());
		
		//queue.setCapacity(5);
		
		queue.enqueue(10);
		assertEquals(1, queue.size());
		
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		
		assertEquals(5, queue.size());
	}

	/**
	 * Test method for LinkedQueue.dequeue().
	 */
	@Test
	void testDequeue() {
		LinkedQueue<Integer> stack = new LinkedQueue<Integer>(10);
		stack.setCapacity(5);
		
		stack.enqueue(10);
		assertEquals(1, stack.size());
		
		stack.enqueue(1);
		stack.enqueue(2);
		stack.enqueue(3);
		stack.enqueue(4);
		
		assertEquals(5, stack.size());
		
		assertEquals(10, stack.dequeue());
		assertEquals(4, stack.size());
		
		assertEquals(1, stack.dequeue());
		assertEquals(2, stack.dequeue());
		assertEquals(3, stack.dequeue());
		assertEquals(4, stack.dequeue());
		
		assertEquals(0, stack.size());
		
		assertThrows(NoSuchElementException.class, () -> stack.dequeue());
	}

	/**
	 * Test method for LinkedQueue.isEmpty().
	 */
	@Test
	void testIsEmpty() {
		LinkedQueue<Integer> stack = new LinkedQueue<Integer>(10);
		stack.setCapacity(5);
		
		assertTrue(stack.isEmpty());
		
		stack.enqueue(10);		
		stack.enqueue(1);
		stack.enqueue(2);
		stack.enqueue(3);
		stack.enqueue(4);
		assertFalse(stack.isEmpty());
		
		assertEquals(10, stack.dequeue());		
		assertEquals(1, stack.dequeue());
		assertEquals(2, stack.dequeue());
		assertFalse(stack.isEmpty());
		assertEquals(3, stack.dequeue());
		assertEquals(4, stack.dequeue());
		
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
	}

	/**
	 * Test method for LinkedQueue.setCapacity(int).
	 */
	@Test
	void testSetCapacity() {
		LinkedQueue<Integer> stack = new LinkedQueue<Integer>(10);
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(-1));
		
		stack.setCapacity(2);
		
		stack.enqueue(10);		
		stack.enqueue(1);
		
		assertThrows(IllegalArgumentException.class, () -> stack.enqueue(2));
		
		stack.setCapacity(5);
		stack.enqueue(2);
		stack.enqueue(3);
		stack.enqueue(4);
		
		assertThrows(IllegalArgumentException.class, () -> stack.enqueue(5));
	}
	
}


