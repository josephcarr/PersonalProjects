/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * This interface will be used in the Queue classes
 * which can enqueue and dequeue elements, check if
 * the queue is empty, find the size of the queue,
 * and set the capacity of the queue
 * 
 * @param <E>	element in the queue
 * @author aahayles
 *
 */
public interface Queue<E> {
	
	/**
	 * Adds the element to the back of the Queue
	 * @param element	element to be added
	 * @throws IllegalArgumentException once capacity has reached
	 */
	void enqueue(E element);
	
	/**
	 * 
	 * Removes the element at the front of the Queue
	 * @return element at the front of the Queue
	 * 
	 */
	E dequeue();
	
	/**
	 * Checks if the queue is empty 
	 * @return true if the Queue is empty
	 */
	boolean isEmpty();
	
	/**
	 * Finds the size of the queue
	 * @return the number of elements in the Queue
	 */
	int size();
	
	/**
	 * Sets the Queue's capacity
	 * @param capacity	capacity of the queue
	 * @throws IllegalArgumentException if parameter is negative
	 * or it is less than the number of elements in the Queue
	 */
	void setCapacity(int capacity);
	
	

}
