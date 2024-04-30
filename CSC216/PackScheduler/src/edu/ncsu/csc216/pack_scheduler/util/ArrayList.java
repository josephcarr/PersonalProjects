/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Generic implementation of ArrayList.
 * @author Joseph Carrasco
 * @param <E> the generic element of the ArrayList
 *
 */
public class ArrayList<E> extends AbstractList<E> {
	
	/** the intial size of the array */
	private final static int INIT_SIZE = 10;
	/** the array */
	private E [] list;
	/** the current size of the array */
	private int size;
	/** the capacity of the array */
	private int capacity;
	
	/** 
	 * Constructs an ArrayList with no parameters.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E []) new Object[INIT_SIZE];
		size = 0;
		capacity = INIT_SIZE;
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
	 * Adds the given element at the given index of the array.
	 * @param idx the index at which to add
	 * @param element the element to add
	 * @throws NullPointerException if the element is null
	 */
	@Override
	public void add(int idx, E element) {
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		
		for (int i = size - 1; i >= idx; i--) {
			list[i + 1] = list[i];
		}
		
		list[idx] = element;
		size++;
		
		if (size >= capacity) {
			growArray();
		}
	}
	
	/**
	 * Grows the Array.
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		capacity *= 2;
		E [] newList = (E []) new Object[capacity];
		
		for (int i = 0; i < size; i++) {
			newList[i] = list[i];
		}
		
		list = newList;
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
		
		E element = list[idx];
		
		for (int i = idx; i < size; i++) {
			list[i] = list[i + 1];
		}
		
		list[size - 1] = null;
		size--;
		return element;
	}
	
	/**
	 * Sets the given index to the given element.
	 * @param idx the index at which to set
	 * @param element the element to set to
	 * @return the element that was originally at that index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size
	 */
	@Override
	public E set(int idx, E element) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (size == 0) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		
		E rtn = list[idx];
		list[idx] = element;
		
		return rtn;
	}
	
	/**
	 * Returns the element at the given index.
	 * @param idx the index of the element to return
	 * @return the element at the index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		return list[idx];
	}
}
