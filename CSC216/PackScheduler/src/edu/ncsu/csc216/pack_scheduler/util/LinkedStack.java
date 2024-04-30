/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * A generic stack implementation using linked lists
 * @author Joseph Carrasco
 * 
 * @param <E> the generic element of the ArrayList
 */
public class LinkedStack<E> implements Stack<E> {

	/** Front of the LinkedList */
	private ListNode front;
	/** Size of the LinkedList */
	private int size;
	/** Capacity of the LinkedList */
	private int capacity;
	
	/**
	 * Creates an Empty LinkedStack of a certain capacity
	 * @param capacity the initial capacity for the list
	 * @throws IllegalArgumentException if the capacity is less than 0
	 */
	public LinkedStack(int capacity) {
		front = null;
		size = 0;
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		setCapacity(capacity);
	}

	/**
	 * Adds an element to the top of the stack
	 * for our LinkedStack implementation, the top of the stack will be the
	 * front of the list
	 * @param element for the element to be added to the top of the stack
	 * @throws IllegalArgumentException if the size is greater than or equal to the capacity
	 */
	@Override
	public void push(E element) {
		if (size >= capacity) {
			throw new IllegalArgumentException();
		}
		
		ListNode newNode = new ListNode(element);
		newNode.next = front;
		front = newNode;
		size++;
	}

	/**
	 * Removes the element from the top of the stack and returns its value
	 * @return value of element taken from the top of the stack
	 * @throws EmptyStackException if the array stack is empty
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		
		E rtn = front.data;
		front = front.next;
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
	 * @throws IllegalArgumentException if the capacity is less than 0 or less than the size of the array stack
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
	}
	
	/**
	 * The individual Node of the LinkedStack
	 * @author Joseph Carrasco
	 *
	 */
	private class ListNode {
		/** Data of the Node */
		private E data;
		/** The next Node */
		private ListNode next;
		
		/**
		 * Creates a Node with the data
		 * @param data data for the node
		 */
		public ListNode(E data) {
			this.data = data;
			this.next = null;
		}
		
//		/**
//		 * Creates a Node with the data and the next Node
//		 * @param data data for the node
//		 * @param next the next node
//		 */
//		public ListNode(E data, ListNode next) {
//			this.data = data;
//			this.next = next;
//		}
	}

}
