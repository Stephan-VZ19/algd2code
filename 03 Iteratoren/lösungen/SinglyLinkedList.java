package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.*;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class SinglyLinkedList<E> extends MyAbstractList<E> {
	// variable int modCount already declared by AbstractList<E>
	// private int modCount = 0;
	private int size = 0;
	private Node<E> first;
	private Node<E> last;

	@Override
	public boolean add(E e) {
		return addTail(e);
	}

	private boolean addHead(E e) {
		Node<E> node = new Node<E>(e, null);
		if (isEmpty()) {
			last = node;		// the list is empty, i.e., first == last == null
		} else {
			node.next = first;
		}
		first = node;
		size++;
		modCount++;
		return true;
	}

	private boolean addTail(E e) {
		Node<E> node = new Node<E>(e, null);
		if (isEmpty()) {
			first = node;		// the list is empty, i.e., first == last == null
		} else {
			last.next = node;
		}
		last = node;
		size++;
		modCount++;
		return true;
	}

	@Override
	public boolean contains(Object o) {
		Node<E> current = first;
		while (current != null && !Objects.equals(o, current.elem)) {
			current = current.next;
		}
		return current != null;
	}

	@Override
	public boolean remove(Object o) {
		Node<E> current = first, prev = null;
		while (current != null && !Objects.equals(o, current.elem)) {
			prev = current;
			current = current.next;
		}
		if (current != null) {
			if (current == first) {
				first = current.next;
			} else {
				prev.next = current.next;
			}
			if (current == last) {
				last = prev;
			}
			size--;
			modCount++;
			return true;
		} else return false;
	}

	@Override
	public E get(int index) {
		return getNodeAt(index).elem;
	}

	private Node<E> getNodeAt(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
		Node<E> current = first;
		for(int i=0; i< index; i++) current = current.next;
		return current;
	}

	@Override
	public void add(int index, E element) {
		if (index == 0) {
			addHead(element);
		}
		else if (index == size) {
			addTail(element);
		}
		else{
			addAt(index, element);
		}
	}

	private boolean addAt(int index, E element) {
		// index != 0, index != size
		Node<E> node = new Node<E>(element, null);
		Node<E> prev = getNodeAt(index-1);
		node.next = prev.next;
		prev.next = node;
		size++;
		modCount++;
		return true;
	}

	@Override
	public E remove(int index) {
		E element = get(index);
		if (index == 0) {
			first = first.next;
			if (size == 1) last = null;
		}
		else {
			Node<E> prev = getNodeAt(index -1);
			prev.next = prev.next.next;
			if (index == size -1) last = prev;
		}
		size--;
		modCount++;
		return element;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first;
		while (current != null) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	@Override
	public int size() {
		return size;
	}

	private static class Node<E> {
		private final E elem;
		private Node<E> next;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(E elem, Node<E> next) {
			this.elem = elem;
			this.next = next;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<E> {
		private Node<E> next= first, p = null, pp = null;
		private int myModCount = modCount;
		private boolean mayRemove = false;

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public E next() {
			if (modCount != myModCount) throw new ConcurrentModificationException();
			if (next == null) throw new NoSuchElementException();
			E e = next.elem;
			if (p != null) pp = p;
			p = next;
			next = next.next;
			mayRemove = true;
			return e;
		}

		@Override
		public void remove() {
			if (modCount != myModCount) throw new ConcurrentModificationException();
			if (!mayRemove) throw new IllegalStateException();
			if (pp != null) pp.next = next;
			else first = next;
			if (next == null) last = pp;
			p = pp;
			mayRemove = false;
			size--;
			modCount++;
			myModCount = modCount;
		}
	}

	public static void main(String[] args) {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
