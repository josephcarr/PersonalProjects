/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of an AbbstractSequencial LinkedList
 * @author Joseph Carrasco
 * 
 * @param <E> generic type for Linked List
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/** Front node of LinkedList */
	private ListNode front;
	/** Back node of LinkedList */
	private ListNode back;
	/** Size of list */
	private int size;
	
	/**
	 * Constructor
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		
		front.next = back;
		back.prev = front;
		
		size = 0;
	}
	
	/**
	 * Returns new ListIterator
	 * @return linkedIterator new ListIterator
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		LinkedListIterator linkedIterator = new LinkedListIterator(index);
		return linkedIterator;
	}

	/**
	 * Adds element at index
	 * @param index index
	 * @param element element
	 */
	@Override
	public void add(int index, E element) {
//		if (contains(element)) {
//			throw new IllegalArgumentException();
//		}
		ListNode current = front.next;
		
		while (current != back) {
			if (current.data == element) {
				throw new IllegalArgumentException();
			}
			
			current = current.next;
		}
		
		super.add(index, element);
	}

	/**
	 * Sets index to element
	 * @param index index
	 * @param element element
	 */
	@Override
	public E set(int index, E element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
//		if (contains(element)) {
//			throw new IllegalArgumentException();
//		}
		ListNode current = front.next;
		
		while (current != back) {
			if (current.data == element) {
				throw new IllegalArgumentException();
			}
			
			current = current.next;
		}
		
		return super.set(index, element);
	}

	/**
	 * Returns list size
	 * @return size list size
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Nodes in the LinkedList
	 * @author Joseph Carrasco
	 *
	 */
	private class ListNode {
		/** Data in the node */
		private E data;
		/** Next node in the list */
		private ListNode next;
		/** Previous node in the list */
		private ListNode prev;
		
		/**
		 * Constructs a ListNode with given data
		 * @param data element data
		 */
		public ListNode(E data) {
			this.data = data;
			next = null;
			prev = null;
		}
		
		/**
		 * Constructs a ListNode with a given data, prev node, and next node
		 * @param data element data
		 * @param prev previous element
		 * @param next next element
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	/**
	 * Iterator class for Linked List
	 * @author Joseph Carrasco
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {
		/** Previous ListNode */
		private ListNode previous;
		/** Next ListNode */
		private ListNode next;
		/** previous index */
		private int previousIndex;
		/** next index */
		private int nextIndex;
		/** Last ListNode that was returned */
		private ListNode lastRetrieved;
		
		/**
		 * Constructs an Iterator with given index
		 * @param index index of iterator
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			
			ListNode current = front.next;
			
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			
			
			previous = current.prev;
			next = current;
			previousIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
		}
		
		/**
		 * Returns whether there is a next node
		 * @return if there is next node
		 */
		@Override
		public boolean hasNext() {
			return !(next == null);
		}

		/**
		 * returns next element
		 * @return next element
		 */
		@Override
		public E next() {
			if (next.data == null) {
				throw new NoSuchElementException();
			}
			
			E value = next.data;
			lastRetrieved = next;
			
			next = next.next;
			previous = previous.next;
			
			nextIndex++;
			previousIndex++;
			
			return value;
		}

		/**
		 * Returns whether there is a previous node
		 * @return if there is previous node
		 */
		@Override
		public boolean hasPrevious() {
			return !(previous == null);
		}

		/**
		 * Returns previous element
		 * @return previous element
		 */
		@Override
		public E previous() {
			if (previous.data == null) {
				throw new NoSuchElementException();
			}
			
			E value = previous.data;
			lastRetrieved = previous;
			
			previous = previous.prev;
			next = next.prev;
			
			nextIndex--;
			previousIndex--;
			
			return value;
		}

		/**
		 * Returns next index
		 * @return next index
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns previous index
		 * @return previous index
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * Removes last retrieved element
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			next.prev = lastRetrieved.prev;
			previous.prev.next = lastRetrieved.next;
			lastRetrieved = null;
			
			size--;
		}

		/**
		 * Sets the last retrieved node to the new element
		 * @param e new element
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			
			lastRetrieved.data = e;
		}

		/**
		 * Inserts element before next element
		 * @param e element
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			
			ListNode newNode = new ListNode(e, previous, next);
			
			previous.next = newNode;
			next.prev = newNode;
			
			lastRetrieved = null;
			
			size++;
		}
		
	}
}
