/*
 * Created on 21.03.2014
 */
package ch.fhnw.algd.sortalgs;

import ch.fhnw.algd.sortdemo.framework.SortAlg;
import ch.fhnw.algd.sortdemo.framework.SortData;

/**
 * @author Wolfgang Weck
 */
public class HeapSort implements SortAlg {
	@Override
	public void run(SortData data) {
		int size = data.size();
		for (int i = size / 2 - 1; i >= 0; i--) {
			siftDown(data, i, size);
		}
		for (int i = size - 1; i > 0; i--) {
			data.swap(i, 0);
			siftDown(data, 0, i);
		}
	}

	private void siftDown(SortData data, int i, int size) {
		int j = (i + 1) * 2 - 1;
		if (j + 1 < size && data.less(j, j + 1)) j++;
		while (j < size && data.less(i, j)) {
			data.swap(i, j);
			i = j;
			j = (i + 1) * 2 - 1;
			if (j + 1 < size && data.less(j, j + 1)) j++;
		}
	}
}
