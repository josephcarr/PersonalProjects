/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * An abstract LinkedList implementation
 * @author Joseph Carrrasco
 * @param <E> the generic element of the ArrayList
 *
 */
public class LinkedAbstractList<E> extends AbstractList<E> {
	
	/** Front of the LinkedList */
	private ListNode front;
	/** Size of the LinkedList */
	private int size;
	/** Capacity of the LinkedList */
	private int capacity;
	/** Back of the LinkedList */
	private ListNode back;
	
	
	/**
	 * Creates an Empty LinkedList of a certain capacity
	 * @param capacity the initial capacity for the list
	 * @throws IllegalArgumentException if the capacity is less than 0
	 */
	public LinkedAbstractList(int capacity) {
		front = null;
		back = front;
		size = 0;
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		setCapacity(capacity);
	}
	
	/** 
	 * Returns the current size of the ArrayList.
	 * @return the size
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Adds the given element at the given index of the list.
	 * @param idx the index at which to add
	 * @param data the element to add
	 * @throws IllegalArgumentException if the size is greater than the capacity
	 */
	@Override
	public void add(int idx, E data) {
		if (size >= capacity) {
			throw new IllegalArgumentException();
		}
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (data == null) {
			throw new NullPointerException();
		}
		
		if (size == 0) {
			front = new ListNode(data);
			back = front;
			size++;
			return;
		}
		
		ListNode current = front;
		
		while (current != null) {
			if (data.equals(current.data)) {
				throw new IllegalArgumentException();
			}
			
			current = current.next;
		}
		
		if (idx == 0) {
			front = new ListNode(data, front);
		}
		else if (idx == size) {
			ListNode node = new ListNode(data, null);
			back.next = node;
			back = node;
		}
		else {
		    current = front;
	
		    for (int i = 0; i < idx - 1; i++) {
		    	current = current.next;
		    }
		    
		    current.next = new ListNode(data, current.next);
		}
		
		size++;
	}
	
	/**
	 * Removes the element at the given index from the array.
	 * @param idx the index at which to remove
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size
	 */
	@Override
	public E remove(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (size == 0) {
			throw new IndexOutOfBoundsException();
		}
		
		E value = null;
		
		if(idx == size - 1) {
			value = back.data;
			ListNode current = front;
			while (current != null) {
				if (current.next == back) {
					back = current;
					back.next = null;
				}
				
				current = current.next;
			}
		}
		else if (idx == 0) {
			value = front.data;
			front = front.next;
		}
		else {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			
			value = current.next.data;
			current.next = current.next.next;
		}
		size--;
		return value;
	}
	
	/**
	 * Sets the given index to the given element.
	 * @param idx the index at which to set
	 * @param data the element to set to
	 * @return the element that was originally at that index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size
	 */
	@Override
	public E set(int idx, E data) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (data == null) {
			throw new NullPointerException();
		}
		if (size == 0) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode current = front;
		
		while (current != null) {
			if (data.equals(current.data)) {
				throw new IllegalArgumentException();
			}
			
			current = current.next;
		}
		
		E value = null;
		current = front;
		
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		
		value = current.data;
		current.data = data;
		
		return value;
	}
	
	/**
	 * Returns the element at the given index.
	 * @param idx the index of the element to return
	 * @return the element at the index
	 * @throws IndexOutOfBoundsException if the size is 0 or if the index is less than
	 * 0 or greater than the size
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (size == 0) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		
		return current.data;
	}
	
	/**
	 * Sets the capacity for a list
	 * @param capacity	for the maximum capacity
	 * @throws IllegalArgumentException if the capacity is less than 0 or if the capacity is less than the size
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		
		
		this.capacity = capacity;
		
		
	}
	
	/**
	 * Gets the capacity for a list
	 * 
	 * @return capacity of the list
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * The individual Node of the LinkedAbstractList
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
		
		/**
		 * Creates a Node with the data and the next Node
		 * @param data data for the node
		 * @param next the next node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
}
