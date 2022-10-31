package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.Arrays;
import java.util.Objects;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class MyLinkedList<E> extends MyAbstractList<E> {
	private int size = 0;
	private Node<E> first;
	private Node<E> last;

	@Override
	public boolean add(E e) {
		return addTail(e);
	}

	private boolean addHead(E e) {
		Node<E> node = new Node<E>(e, null);
		if (isEmpty()) {		// equivalent to test size==0 | first==null
			last = node;		// the list is empty, i.e., first == last == null
		} else {
			node.next = first;
		}
		first = node;
		size++;
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
		return true;
	}

	private boolean addTailSlow(E e) {
		// Methode, wenn kein last Zeiger benutzt wird.
		Node<E> node = new Node<E>(e, null);
		if (isEmpty()) {
			first = node;
		} else {
			Node<E> current = first;
			while( current.next != null) current = current.next;
			current.next = node;
		}
		size++;
		return true;
	}

	@Override
	public boolean contains(Object o) {
		Node<E> current = first;
		// lineare Suche durch die Liste nach o
		while (current != null && !Objects.equals(o, current.elem)) {
			current = current.next;
		}
		return current != null;
	}

	@Override
	public boolean remove(Object o) {
		Node<E> current = first, prev = null;
		// lineare Suche durch die Liste nach o.
		// Um bei erfolgreicher Suche den Vorgänger zu kennen, müssen immer zwei Nodes zwischengespeichert werden.
		while (current != null && !Objects.equals(o, current.elem)) {
			prev = current;
			current = current.next;
		}
		// Node aus Liste entfernen und Zeiger richtig setzen
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

	public static void main(String[] args) {
		MyLinkedList<Integer> list = new MyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		boolean x;
		System.out.println(Arrays.toString(list.toArray()));
	}
}
