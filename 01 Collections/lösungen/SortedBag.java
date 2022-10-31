package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;
import java.util.Objects;

public class SortedBag<E extends Comparable<? super E>> extends AbstractArrayCollection<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private int size = 0;
	private E[] data;

	public SortedBag() {
		this(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public SortedBag(int capacity) {
		data = (E[])new Comparable[capacity];
	}

	private int indexOf(Object o) {
		/* Falls o in data[]: binarySearch gibt index von o zurück.
		Falls o nicht in data[]: binarySearch gibt (-(insertPosition) -1) zurück,
		wobei insertPosition die Stelle ist mit dem kleinsten Element das grösser als o ist. */
		checkNull(o);
		int left = 0;
		int right = size;
		return Arrays.binarySearch(data, left, right, o);
	}

	@Override
	public boolean add(E e) {
		if (size == data.length) throw new IllegalStateException("Collection is full");
		int i = indexOf(e);
		i = Math.max(i, -i-1);
		for(int j=size; j>i; j--) {
			data[j] = data[j-1]; 		// Elemente nach rechts schieben.
		}
		data[i] = e; 					// Neues Element einfügen
		size++;							// Collection vergrössern
		return true;
	}

	@Override
	public boolean remove(Object o) {
		int i = indexOf(o);
		if (i>=0) {
			for(int j=i; j<size-1; j++) {
				data[j] = data[j+1];	// Elemente nach links verschieben
			}
			data[--size] = null;		// Collection verkleinern
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
		SortedBag<Integer> bag = new SortedBag<Integer>();
		bag.add(2);
		bag.add(1);
		bag.add(2);
		System.out.println(Arrays.toString(bag.toArray()));
	}
}
