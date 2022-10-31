package ch.fhnw.algd2.collections.list.linkedlist;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class DoublyLinkedListCyclic<E> extends MyAbstractList<E> {

	private Node<E> first;
	private int size = 0;

	public DoublyLinkedListSolutionCyclic() {
		first = new Node<E>(null);
		first.next = first;
		first.prev = first;
	}

	private void addNodeAfter(Node<E> n, E item) {
		Node<E> x = new Node<E>(item);
		x.prev = n;
		x.next = n.next;
		n.next.prev = x;
		n.next = x;
		size++;
		modCount++;
	}

	private void removeNode(Node<E> n) {
		n.prev.next = n.next;
		n.next.prev = n.prev;
		size--;
		modCount++;
	}

	private Node<E> thisNode(Object o) {
		Node<E> n = first.next;
		while (n != first && n.elem != o
				&& (n.elem == null || !n.elem.equals(o))) {
			n = n.next;
		}
		return n != first ? n : null;
	}

	private Node<E> indexedNode(int index) {
		Node<E> n = first.next;
		while (index > 0) {
			n = n.next;
			index--;
		}
		return n;
	}

	@Override
	public boolean add(E item) {
		addNodeAfter(first.prev, item);
		return true;
	}

	@Override
	public void add(int index, E item) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		Node<E> n = indexedNode(index);
		addNodeAfter(n.prev, item);
	}

	@Override
	public boolean remove(Object o) {
		Node<E> n = thisNode(o);
		if (n != null) {
			removeNode(n);
			return true;
		} else
			return false;
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		Node<E> n = indexedNode(index);
		removeNode(n);
		return n.elem;
	}

	@Override
	public boolean contains(Object o) {
		return thisNode(o) != null;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return indexedNode(index).elem;
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		int i = 0;
		Node<E> n = first.next;
		while (n != first) {
			arr[i++] = n.elem;
			n = n.next;
		}
		return arr;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return new MyListIterator(index);
	}

	@Override
	public ListIterator<E> listIterator() {
		return new MyListIterator(0);
	}

	private static class Node<E> {
		private E elem;
		private Node<E> prev, next;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(Node<E> prev, E elem, Node<E> next) {
			this.prev = prev;
			this.elem = elem;
			this.next = next;
		}
	}

	private class MyListIterator implements ListIterator<E> {
		private Node<E> next = first.next, returnedLast = null;
		private int myModCount = modCount, nextI = 0;

		MyListIterator(int index) {
			next = indexedNode(index);
			nextI = index;
		}

		@Override
		public boolean hasNext() {
			return next != first;
		}

		@Override
		public E next() {
			if (myModCount != modCount)
				throw new ConcurrentModificationException();
			if (next == first)
				throw new NoSuchElementException();
			E e = next.elem;
			returnedLast = next;
			next = next.next;
			nextI++;
			return e;
		}

		@Override
		public boolean hasPrevious() {
			return next.prev != first;
		}

		@Override
		public E previous() {
			if (myModCount != modCount)
				throw new ConcurrentModificationException();
			if (next.prev == first)
				throw new NoSuchElementException();
			next = next.prev;
			E e = next.elem;
			returnedLast = next;
			nextI--;
			return e;
		}

		@Override
		public int nextIndex() {
			return nextI;
		}

		@Override
		public int previousIndex() {
			return nextI - 1;
		}

		@Override
		public void remove() {
			if (myModCount != modCount)
				throw new ConcurrentModificationException();
			if (returnedLast == null)
				throw new IllegalStateException();
			removeNode(returnedLast);
			if (returnedLast == next) {
				next = next.next;
			} else {
				nextI--;
			}
			myModCount = modCount;
			returnedLast = null;
		}

		@Override
		public void set(E e) {
			if (myModCount != modCount)
				throw new ConcurrentModificationException();
			if (returnedLast == null)
				throw new IllegalStateException();
			returnedLast.elem = e;
		}

		@Override
		public void add(E e) {
			if (myModCount != modCount)
				throw new ConcurrentModificationException();
			addNodeAfter(next.prev, e);
			nextI++;
			myModCount = modCount;
		}
	}
}
