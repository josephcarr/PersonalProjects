/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * This class implements the Queue interface to add (enqueue) and 
 * remove (dequeue) elements from a an array list. Furthermore, it 
 * can check if the array list is empty and if an element exists in 
 * an array list.
 * 
 * @param <E> element in the ArrayQueue
 * @author aahayles
 *
 */
public class LinkedQueue<E> implements Queue<E> {
	
	/** Creates a LinkedAbstractList of elements */
	private LinkedAbstractList<E> list;

	//Capacity of the linked queue
	//private int capacity;
	
	/**
	 * Creates the LinkedQueue object and sets
	 * the capacity of the list.
	 * 
	 * @param capacity	capacity of the linked queue
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}

	
	/**
	 * Adds an element to the linked list
	 * 
	 * @param element					element that will be added to the list
	 * @throws IllegalArgumentException	if the size of the list is greater than the capacity
	 */
	@Override
	public void enqueue(E element) {
		if (list.size() >= list.getCapacity()) {
			throw new IllegalArgumentException();
		}
		list.add(list.size(), element);
		
	}

	
	/**
	 * Removes an element from the list
	 * 
	 * @return element					element being removed from the list
	 * @throws NoSuchElementException	if there is no element to be removed
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		//E element = list.remove(list.size() - capacity);
		
		return list.remove(0);
	}

	
	/**
	 * Checks if the size of the list is 0 or not
	 * 
	 * @return true or false based on if the list size is 0 or not
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	
	/**
	 * Size of the array list
	 * 
	 * @return size of the list
	 */
	@Override
	public int size() {
		
		return list.size();
	}

	
	/**
	 * Sets the capacity of the array list. If the capacity
	 * is less than 0 or less than the size of the list,
	 * it will throw an exception
	 * 
	 * @param capacity	capacity of the list
	 * @throws IllegalArgumentException if the capacity is less than 0 or less than the size of the list
	 */
	@Override
	public void setCapacity(int capacity) {
		//list = new LinkedAbstractList<E>(capacity);
		list.setCapacity(capacity);
		
		
	}

}
