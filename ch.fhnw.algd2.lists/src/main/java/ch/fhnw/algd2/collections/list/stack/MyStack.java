package ch.fhnw.algd2.collections.list.stack;

import java.util.ArrayList;
import java.util.List;

public class MyStack<E> implements IStack<E> {

	private List<E> stackList = new ArrayList<>();

	@Override
	public E pop() {
		// TODO implement this operation

		E tmp = stackList.get(stackList.size()-1);
		stackList.remove(stackList.size()-1);
		return tmp;
	}

	@Override
	public E push(E elem) {
		// TODO implement this operation

		stackList.add(elem);
		return elem;
	}

	@Override
	public E peek() {
		// TODO implement this operation

		return stackList.get(stackList.size()-1);
	}

	@Override
	public boolean empty() {
		// TODO implement this operation

		if (stackList.size() > 0) {
			return true;
		}
		return false;
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
