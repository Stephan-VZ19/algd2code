package ch.fhnw.algd2.heaptest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* Heap implementing a Priority Queue */
class Heap<K> implements PriorityQueue<K> {
	private HeapNode<K>[] heap; // Array to store the heap elements
	private int size; // Number of elements currently stored in heap

	/**
	 * Construct the binary heap.
	 *
	 * @param capacity
	 *          how many items the heap can store
	 */
	@SuppressWarnings("unchecked")
	Heap(int capacity) {
		heap = new HeapNode[capacity];
	}

	/**
	 * Construct the binary heap with the Floyd algorithm.
	 *
	 * @param elems
	 *          an array of HeapNode Elements
	 */
	Heap(HeapNode<K>[] elems) {
		this.heap = elems;
		this.size = elems.length;
		for(int i=size/2; i>= 0; i--){
			siftDown(i);
		}
	}

	/**
	 * Returns the number of elements in this priority queue.
	 *
	 * @return the number of elements in this priority queue.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Check whether the heap is empty.
	 *
	 * @return true if there are no items in the heap.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Check whether the heap is full.
	 *
	 * @return true if no further elements can be inserted into the heap.
	 */
	@Override
	public boolean isFull() {
		return size == heap.length;
	}

	/**
	 * Make the heap (logically) empty.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++)
			heap[i] = null;
		size = 0;
	}

	/**
	 * Add to the priority queue, maintaining heap order. Duplicates and null
	 * values are allowed. Small values of the argument priority means high
	 * priority, Large values means low priority.
	 *
	 * @param element
	 *          the item to insert
	 * @param priority
	 *          the priority to be assigned to the item element
	 * @exception QueueFullException
	 *              if the heap is full
	 */
	@Override
	public void add(K element, long priority) throws QueueFullException {
		if (size == heap.length) throw new QueueFullException();
		heap[size] = new HeapNode<K>(element, priority);
		siftUp(size);
		size++;
	}

	/**
	 * Removes and returns the item with highest priority (smallest priority
	 * value) from the queue, maintaining heap order.
	 *
	 * @return the item with highest priority (smallest priority value)
	 * @throws QueueEmptyException
	 *           if the queue is empty
	 */
	@Override
	public K removeMin() throws QueueEmptyException {
		if (size == 0) throw new QueueEmptyException();
		K res = heap[0].element;
		size--;
		heap[0] = heap[size];
		heap[size] = null;
		siftDown(0);
		return res;
	}

	/**
	 * Internal method to let an element sift up in the heap.
	 *
	 * @param start
	 *          the index at which the percolate begins
	 */
	private void siftUp(int start) {
		int j = (start - 1) / 2;
		while (start > 0 && heap[start].priority < heap[j].priority) {
			HeapNode<K> n = heap[start];
			heap[start] = heap[j];
			heap[j] = n;
			start = j;
			j = (start - 1) / 2;
		}
	}

	/**
	 * Internal method to let an element sift down in the heap.
	 *
	 * @param start
	 *          the index at which the percolate begins
	 */
	private void siftDown(int start) {
		int j = 2 * start + 1;
		if (j + 1 < size && heap[j].priority > heap[j + 1].priority) j++;
		while (j < size && heap[start].priority > heap[j].priority) {
			HeapNode<K> n = heap[start];
			heap[start] = heap[j];
			heap[j] = n;
			start = j;
			j = 2 * start + 1;
			if (j + 1 < size && heap[j].priority > heap[j + 1].priority) j++;
		}
	}

	/**
	 * Allocates a long[] Array and coppies the priority values from the heap
	 * array. The length of the returned array shall be equal to the number of
	 * elements in the heap (i.e. size()). The smallest element (root) shall be
	 * placed at index position 0.
	 *
	 * @return Array with all priority values
	 */
	@Override
	public long[] toLongArray() {
		long[] l = new long[size];
		for (int i = 0; i < size; i++)
			l[i] = heap[i].priority;
		return l;
	}

	/**
	 * Change the priority at position index by subtracting negChange
	 *
	 * @param index     the index of the element to change
	 * @param negChange the amount to be subtracted from the priority
	 */
	public void decreasePriority(int index, long negChange) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		K elem = heap[index].element;
		long prio = heap[index].priority - negChange;
		heap[index] = new HeapNode<K>(elem, prio);
		if (negChange > 0) {
			siftUp(index);
		} else {
			siftDown(index);
		}
	}

	/**
	 * Delete the element at the given index
	 *
	 * @param index the index of the element to delete
	 */
	public void delete(int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		heap[index] = heap[size-1];
		heap[size-1] = null;
		size--;
		siftDown(index);
		siftUp(index);
	}

	private static class HeapNode<K> {
		private final K element;
		private final long priority;

		HeapNode(K element, long priority) {
			this.element = element;
			this.priority = priority;
		}
	}

	/**
	 * For an implementation as a tree
	 */
	private static class HeapTreeNode<K> {
		private final K element;
		private long priority;
		private HeapTreeNode father, leftChild, rightChild;

		HeapTreeNode(K element, long priority) {
			this.element = element;
			this.priority = priority;
		}
	}
	private HeapTreeNode<K> root, last;

	public boolean addTree(K elem, long prio) {
		HeapTreeNode<K> node = new HeapTreeNode<K>(elem, prio);
		return true;
	}

}


