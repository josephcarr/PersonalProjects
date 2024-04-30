package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for the Stack classes which can push and pop
 * elements, find the size of the stack or queue, check
 * if it is empty, and if it contains an element or not
 * 
 * @author shreyaholikatti
 *
 * @param <E>	element in the Stack
 */
public interface Stack<E> {
	
	/**
	 * Adds an element to the top of the stack
	 * @param element for the element to be added to the top of the stack
	 */
	 void push(E element);
	
	
	/**
	 * Removes the element from the top of the stack and returns its value
	 * @return value of element taken from the top of the stack
	 */
	 E pop();
	
	
	/**
	 * Checks if the stack is empty or not
	 * 
	 * @return true if the stack is empty
	 */
	 boolean isEmpty();
	
	
	/**
	 * Returns the number of elements in the stack
	 * 
	 * @return size for size of stack
	 */
	 int size();
	
	
	/**
	 * Sets the capacity of the stack
	 * @param capacity	stack capacity
	 */
	 void setCapacity(int capacity);
}
