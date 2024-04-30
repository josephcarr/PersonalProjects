/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Custom implementation of Recursive Linked List
 * @author Joseph Carrasco
 * 
 * @param <E> generic type for Linked List
 */
public class LinkedListRecursive<E> {
	/** Front node of the list */
	ListNode front;
	/** Size of the List */
	private int size;
	
	/**
	 * LinkedLList Constructor which sets the front
	 * to null and the size to 0
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}
	
	/**
	 * Checks if the list is empty
	 * @return if the list is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns size of the list
	 * @return size size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Adds an element to the list
	 * @param element element data
	 * @return if element can be added
	 * @throws IllegalArgumentException if it contains an element
	 * @throws NullPointerException if the element is null
	 */
	public boolean add(E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		
		if (isEmpty()) {
			ListNode newNode = new ListNode(element, front);
			front = newNode;
			size++;
			return true;
		}
		
		add(size, element);
		
		return true;
	}
	
	/**
	 * Adds an element at a certain index
	 * @param idx index of list
	 * @param element element data
	 * @throws IllegalArgumentException if it contains the element
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size
	 * @throws NullPointerException if the element is null
	 */
	public void add(int idx, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		
		if (idx == 0) {
			ListNode newNode = new ListNode(element, front);
			front = newNode;
			size++;
			return;
		}
		
		front.add(idx - 1, element);
		
		size++;
	}
	
	/**
	 * Returns an element at index
	 * @param idx index of list
	 * @return element at index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size
	 */
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return front.get(idx);
	}
	
	/**
	 * Removes a certain element from the linked list
	 * @param element element data
	 * @return if the element can be removed
	 */
	public boolean remove(E element) {
		if (element == null) {
			return false;
		}
		
		if (isEmpty()) {
			return false;
		}
		
		if (front.data == element) {
			front = front.next;
			size--;
			return true;
		}
		
		boolean rtn = front.remove(element);
		size--;
		
		return rtn;
	}
	
	/**
	 * Removes an element at index
	 * @param idx index of list
	 * @return the value of removed element
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size
	 */
	public E remove(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		E value;
		
		if (idx == 0) {
			value = front.data;
			front = front.next;
			size--;
			return value;
		}
		
		value = front.remove(idx);
		size--;
		
		return value;
	}
	
	/**
	 * Sets an element at index
	 * @param idx index of list
	 * @param element new element
	 * @return value of replaced element
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size
	 * @throws NullPointerException if the element is null
	 * @throws IllegalArgumentException if it contains the element
	 */
	public E set(int idx, E element) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		
		return front.set(idx, element);
	}
	
	/**
	 * Determines if list contains element
	 * @param element element data
	 * @return if list contains element
	 */
	public boolean contains(E element) {
		if (isEmpty()) {
			return false;
		}
		
		return front.contains(element);
	}
	
	private class ListNode {
		/** Element in Node */
		public E data;
		/** Next Node */
		public ListNode next;
		
		/**
		 * Constructs a ListNode with given element and next node
		 * @param element element data
		 * @param next next node
		 */
		public ListNode(E element, ListNode next) {
			this.data = element;
			this.next = next;
		}
		
		/**
		 * Adds an element at a certain index
		 * @param idx index of list
		 * @param element element data
		 */
		public void add(int idx, E element) {
			if (idx < 0) {
				return;
			}
			
			if (idx == 0) {
				next = new ListNode(element, next);
			}
			
			next.add(idx - 1, element);
		}
		
		/**
		 * Returns an element at index
		 * @param idx index of list
		 * @return element at index
		 */
		public E get(int idx) {
			if (idx < 0) {
				return null;
			}
			
			E value;
			
			if (idx == 0) {
				value = data;
				return value;
			}
			
			value = next.get(idx - 1);
			
			return value;
		}
		
		/**
		 * Removes an element at index
		 * @param idx index of list
		 * @return the value of removed element
		 */
		public E remove(int idx) {
			if (idx <= 0) {
				return null;
			}
			
			E value = next.remove(idx - 1);
			
			if (idx == 1) {
				value = next.data;
				next = next.next;
			}
			
			return value;
		}
		
		/**
		 * Removes a certain element
		 * @param element element data
		 * @return if the element can be removed
		 */
		public boolean remove(E element) {
			if (next == null) {
				return false;
			}
			
			if (next.data == element) {
				next = next.next;
				return true;
			}
			
			return next.remove(element);
		}
		
		/**
		 * Sets an element at index.
		 * @param idx index of list
		 * @param element new element
		 * @return value of replaced element
		 */
		public E set(int idx, E element) {
			if (idx < 0) {
				return null;
			}
			
			E value;
			
			if (idx == 0) {
				value = data;
				data = element;
				return value;
			}
			
			value = next.set(idx - 1, element);
			
			return value;
		}
		
		/**
		 * Determines if list contains element. If it does,
		 * return true; if the next element is null, return
		 * false
		 * @param element element data
		 * @return if list contains element
		 */
		public boolean contains(E element) {
			if (data == element) {
				return true;
			}
			if (next == null) {
				return false;
			}
			
			return next.contains(element);
		}
	}
}
