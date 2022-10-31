package ch.fhnw.algd2.collections.list.stack;

import org.w3c.dom.Node;

import java.util.NoSuchElementException;

public class MyQueue<E> {
    private MyStack<E> s_in = new MyStack<E>(), s_out = new MyStack<E>();

    public void enqueue(E element) {
        s_in.push(element);
    }

    public E dequeue() {
        if (s_out.empty()) restack(s_in, s_out);
        return s_out.pop();
    }

    public E peek() {
        if (s_out.empty()) restack(s_in, s_out);
        return s_out.peek();
    }

    public boolean empty() {
        return (s_in.empty() && s_out.empty());
    }

    private void restack(MyStack s1, MyStack s2) {
        while (!s1.empty()) {
            s2.push(s1.pop());
        }
    }

    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<Integer>();
        System.out.println("Enqueueing numbers to queue (0 to 9)");
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println("Dequeue numbers from queue");
        while (!queue.empty()) {
            System.out.println(queue.dequeue());
        }
    }
}