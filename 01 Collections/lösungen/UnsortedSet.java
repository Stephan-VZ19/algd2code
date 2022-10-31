package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;
import java.util.Set;

public class UnsortedSet<E> extends AbstractArrayCollection<E> implements Set<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private int size = 0;
	private E[] data;

	public UnsortedSet() {
		this(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public UnsortedSet(int capacity) {
		data = (E[])new Object[capacity];
	}

	private int indexOf(Object o) {
		/* Falls o in data[]: return Position von o
		Falls o nicht in data[]: return -1 */
		checkNull(o);
		int i = size - 1;
		while (i >= 0 && !data[i].equals(o)) {
			i--;
		}
		return i;
	}

	@Override
	public boolean add(E e) {
		if (indexOf(e) < 0) {
			if (size == data.length) throw new IllegalStateException("Collection is full");
			data[size++] = e;
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		int i = indexOf(o);
		if (i >= 0){
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
		UnsortedSet<Integer> bag = new UnsortedSet<Integer>();
		bag.add(2);
		bag.add(2);
		bag.add(1);
		System.out.println(Arrays.toString(bag.toArray()));
	}
}
