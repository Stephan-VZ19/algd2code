package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.Arrays;
import java.util.Iterator;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class MyLinkedList<E> extends MyAbstractList<E> {
	private int size = 0;
	private Node<E> first;
	private Node<E> last;

	@Override
	public boolean add(E e) {
		// TODO implement this operation (part A)

		Node<E> node = new Node<E>(e, null);
		if (isEmpty()) {
			first = node;
		} else {
			Node<E> curr = first;
			while (curr.next != null) {
				curr = curr.next;
			}
			curr.next = node;
		}
		size++;
		return true;
	}

	@Override
	public boolean contains(Object o) {
		// TODO implement this operation (part B)

		Node<E> node = new Node<>((E)o, null);

		node.next = first;
		boolean found = false;

		while (!found && node.next != null) {
			if (node.next.elem.equals((E)o)) {
				found = true;
			}
			node = node.next;
		}
		return found;
	}

	@Override
	public boolean remove(Object o) {
		// TODO implement this operation (part C)

		Node<E> curr = first;
		Node<E> prev = null;	// null, weil am Anfang es kein previous gibt

		while (size > 0 && curr.next != null) {
			if (curr.elem.equals((E)o)) {
				if (prev != null) {
					prev.next = curr.next;	// delete, skip current
				} else {
					first.next = curr.next;
				}
				curr.next = null;
				size--;
				return true;	// true, weil Liste geändert
			}
			prev = curr;
			curr = curr.next;
		}
		return false;	// false, weil Liste nicht geändert

	}

	@Override
	public E get(int index) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();

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

	public static void main(String[] args) {
		MyLinkedList<Integer> list = new MyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
