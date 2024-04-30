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
 * Tests the ArrayQueue class
 * @author aahayles
 * @author Shreya Holikatti
 *
 */
public class ArrayQueueTest {
	
	/**
	 * Test method for Arraystack.push().
	 */
	@Test
	void testEnqueue() {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>(5);
		queue.setCapacity(5);
		
		queue.enqueue(10);
		assertEquals(1, queue.size());
		
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		
		assertEquals(5, queue.size());
	}

	/**
	 * Test method for Arrayqueue.dequeue().
	 */
	@Test
	void testDequeue() {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>(5);
		queue.setCapacity(5);
		
		queue.enqueue(10);
		assertEquals(1, queue.size());
		
		queue.enqueue(4);
		queue.enqueue(3);
		queue.enqueue(2);
		queue.enqueue(1);
		
		assertEquals(5, queue.size());
		
		assertEquals(10, queue.dequeue());
		assertEquals(4, queue.size());
		
		assertEquals(4, queue.dequeue());
		assertEquals(3, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(1, queue.dequeue());
		
		assertEquals(0, queue.size());
		
		assertThrows(NoSuchElementException.class, () -> queue.dequeue());
	}

	/**
	 * Test method for Arrayqueue.isEmpty().
	 */
	@Test
	void testIsEmpty() {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>(5);
		queue.setCapacity(5);
		
		assertTrue(queue.isEmpty());
		
		queue.enqueue(10);		
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		assertFalse(queue.isEmpty());
		
		assertEquals(10, queue.dequeue());		
		assertEquals(1, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals(3, queue.dequeue());
		assertEquals(4, queue.dequeue());
		
		assertEquals(0, queue.size());
		assertTrue(queue.isEmpty());
	}

	/**
	 * Test method for Arrayqueue.setCapacity(int).
	 */
	@Test
	void testSetCapacity() {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>(2);
		assertThrows(IllegalArgumentException.class, () -> queue.setCapacity(-1));
		
		queue.setCapacity(2);
		
		queue.enqueue(10);		
		queue.enqueue(1);
		
		assertThrows(IllegalArgumentException.class, () -> queue.enqueue(2));
		
		queue.setCapacity(5);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		
		assertThrows(IllegalArgumentException.class, () -> queue.enqueue(5));
	}


}
