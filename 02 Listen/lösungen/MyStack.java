package ch.fhnw.algd2.collections.list.stack;

import java.util.EmptyStackException;
import java.util.List;

import ch.fhnw.algd2.collections.list.linkedlist.MyLinkedList;

public class MyStack<E> implements IStack<E> {
	private List<E> list = new MyLinkedList<E>();

	@Override
	public E pop() {
		if (empty()) throw new EmptyStackException();
		return list.remove(0);
	}

	@Override
	public E push(E elem) {
		list.add(0,elem);
		return elem;
	}

	@Override
	public E peek() {
		if (empty()) throw new EmptyStackException();
		return list.get(0);
	}

	@Override
	public boolean empty() {
		return list.size() == 0;
	}

	public static void main(String[] args) {
		MyStack<Integer> stack = new MyStack<Integer>();
		System.out.println("Pushing numbers to stack (0 to 9)");
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		System.out.println("Pop numbers from stack");
		while (!stack.empty()) {
			System.out.println(stack.pop());
		}
	}
}
