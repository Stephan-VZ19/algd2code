package ch.fhnw.algd2.hashing;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Hash Set with Open Addressing collision resolution
 * 
 * Open Addressing strategy: table length prime, step = 1 + h % (table.length-2)
 */
public class MyHashSetSolutionPrime<E> implements Set<E> {
	private Object[] table;
	private static final Object sentinel = new Object();
	private int size = 0;

	public MyHashSetSolutionPrime(int minCapacity) {
		if (minCapacity < 4) throw new IllegalArgumentException("At least table size 4 required");
		table = new Object[nextPrime(minCapacity)];
	}

	private int nextPrime(int x) { // see algd1...
		if ((x & 1) == 0) x++; // x & 1 is equivalent with x % 2
		while (!isPrime(x)) {
			x = x + 2;
		}
		return x;
	}

	private boolean isPrime(int x) { // see algd1...
		assert x > 2 && (x & 1) != 0; // x & 1 is equivalent with x % 2
		int t = 3;
		while (t * t <= x && x % t != 0) {
			t = t + 2;
		}
		return t * t > x;
	}

	@Override
	public boolean contains(Object o) {
		if (o == null) throw new NullPointerException("Null are not allowed in this collection.");
		int h = o.hashCode() & 0x7FFFFFFF;
		int i = h % table.length, step = 1 + h % (table.length - 2);
		int cnt = 0;
		while (table[i] != null && !o.equals(table[i]) && cnt != table.length) {
			i = i + step;
			if (i >= table.length) i = i - table.length;
			cnt++;
		}
		return cnt != table.length && table[i] != null;
	}

	@Override
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	public Object[] copyOfArray() {
		return Arrays.copyOf(table, table.length);
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		if (e == null) throw new NullPointerException("Null are not allowed in this collection.");
		int h = e.hashCode() & 0x7FFFFFFF;
		int i = h % table.length, step = 1 + h % (table.length - 2);
		int cnt = 0;
		while (table[i] != null && table[i] != sentinel && !e.equals(table[i]) && cnt != table.length) {
			i = i + step;
			if (i >= table.length) i = i - table.length;
			cnt++;
		}
		if (cnt == table.length) throw new IllegalStateException();
		int j = i;
		if (table[i] == sentinel) {
			while (table[i] != null && !e.equals(table[i]) && cnt != table.length) {
				i = i + step;
				if (i >= table.length) i = i - table.length;
				cnt++;
			}
		}
		if (table[i] == null || cnt == table.length) { // else: e.equals(table[i])
			table[j] = e;
			size++;
			return true;
		} else return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean hasChanged = false;
		for (E elem : c) {
			if (add(elem)) hasChanged = true;
		}
		return hasChanged;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object o : c) {
			if (remove(o)) changed = true;
		}
		return changed;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) throw new NullPointerException("Null not allowed");
		int h = o.hashCode() & 0x7FFFFFFF;
		int i = h % table.length, step = 1 + h % (table.length - 2);
		int cnt = 0;
		while (table[i] != null && !o.equals(table[i]) && cnt != table.length) {
			i = i + step;
			if (i >= table.length) i = i - table.length;
			cnt++;
		}
		if (cnt != table.length && table[i] != null) {
			table[i] = sentinel;
			size--;
			return true;
		} else return false;
	}

	@Override
	public void clear() {
		table = new Object[table.length];
		size = 0;
	}
}
