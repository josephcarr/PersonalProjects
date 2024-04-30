/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * This class will work with an ArrayStack //FIX THIS
 * 
 * @author shreyaholikatti
 * @author Joseph Carrasco
 * @param <E> generic type for ArrayStack
 *
 */
public class ArrayStack<E> implements Stack<E> {
	
	/** the intial size of the array */
	private final static int INIT_SIZE = 10;
	/** the array */
	private E [] list;
	/** the current size of the array */
	private int size;
	/** the capacity of the array */
	private int capacity;
	
	/** 
	 * Constructs an ArrayStack with no parameters.
	 * Initializes the list to a new object and
	 * sets the size of the array stack to 0.
	 * 
	 * @param capacity		class capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack(int capacity) {
		list = (E []) new Object[INIT_SIZE];
		size = 0;
		setCapacity(capacity);
	}

	/**
	 * Adds an element to the top of the stack
	 * For our ArrayStack implementation, the top of the stack
	 * will be the back of the list
	 * @param element for the element to be added to the top of the stack
	 * @throws IllegalArgumentException if the size of the array stack is greater than the capacity
	 */
	@Override
	public void push(E element) {
		if (size >= capacity) {
			throw new IllegalArgumentException();
		}
		
		list[size] = element;
		size++;
	}

	/**
	 * Removes the element from the top of the stack and returns its value
	 * @return value of element taken from the top of the stack
	 * @throws EmptyStackException	if the array stack is empty
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		
		E rtn = list[size - 1];
		list[size - 1] = null; 
		size--;
		
		return rtn;
	}

	/**
	 * Checks if the stack is empty or not
	 * 
	 * @return true if the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of elements in the stack
	 * 
	 * @return size for size of stack
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Sets the capacity of the stack
	 * @param capacity	stack capacity
	 * @throws IllegalArgumentException	if the capacity is less than 0 or less than the size of the array stack
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
	}

}
