package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;

public class UnsortedBag<E> extends AbstractArrayCollection<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private int size = 0;
	private E[] data;

	public UnsortedBag() {
		this(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public UnsortedBag(int capacity) {
		data = (E[])new Object[capacity];
	}

	private int indexOf(Object o) {
		/* Falls o in data[]: return Position von o
		Falls o nicht in data[]: return -1 */
		checkNull(o);
		int i = size - 1;
		while (i >= 0 && !data[i].equals(o)) { i--;	}
		return i;
	}

	@Override
	public boolean add(E e) {
		checkNull(e);
		if (size == data.length) throw new IllegalStateException("Collection is full");
		data[size++] = e;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		int i = indexOf(o);
		if (i >= 0) {
			data[i] = data[size-1];
			data[--size] = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size());
	}

	@Override
	public int size() {
		return size;
	}

	public static void main(String[] args) {
		UnsortedBag<Integer> bag = new UnsortedBag<Integer>();
		bag.add(2);
		bag.add(1);
		bag.add(1);
		bag.add(2);
		System.out.println(Arrays.toString(bag.toArray()));
	}
}
