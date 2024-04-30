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
 * 
 * @author aahayles
 * @author Shreya Holikatti
 *
 */
public class ArrayQueue<E> implements Queue<E> {
	
	/** Array list of elements */
	private ArrayList<E> list;

	/** Capacity of an array list */
	private int capacity;
	
	
	
	/**
	 * Creates an ArrayQueue constructor that
	 * initializes a list and sets the initial
	 * capacity
	 * 
	 * @param capacity	capacity of the array list
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	
	/**
	 * Adds an element to the array list
	 * 
	 * @param element					element that will be added to the list
	 * @throws IllegalArgumentException	if the size of the list is greater than the capacity
	 */
	@Override
	public void enqueue(E element) {
		if (list.size() >= capacity) {
			throw new IllegalArgumentException();
		}
		list.add(element);
		
	}

	
	/**
	 * Removes an element from the list
	 * 
	 * @return element	element being removed from the list
	 * @throws NoSuchElementException	if there is no element to be removed
	 */
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
//		E element = list.remove(list.size() - capacity);
//		int size = size() - 1;
//		size--;
		
		return list.remove(0);
	}

	
	/**
	 * Checks if the size of the list is 0 or not
	 * 
	 * @return true or false based on if the list size is 0 or not
	 */
	public boolean isEmpty() {
		return list.size() == 0;
	}

	
	/**
	 * Size of the array list
	 * 
	 * @return size of the list
	 */
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
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		
	}
	
	/**
	 * Checks if the element is in the array list
	 * 
	 * @param element	element to be checked
	 * @return true		if the element is in the list
	 */
	public boolean contains(E element) {
		for(int i = 0; i < list.size(); i++) {
			if (element == list.get(i)) {
				return true;
			}		
		}
		
		return false;
	}

}
