/*
 * Created on Feb 25, 2005
 */
package ch.fhnw.algd2.graphedit;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Wolfgang Weck
 */
public final class Dijkstra {

	private static class VertexInfo<K> {
		boolean known = false;
		double dist = Double.MAX_VALUE; // infinity
		K source, via = null;
		String label = null;

		VertexInfo(K source) {
			this.source = source;
			label = source.toString();
		}
	}

	static <K> Graph<K> getShortestPath(WeightedGraph<K> g, K from, K to) {
	
		// Map von "VertexID" zu "VertexInfo"
		// sozusagen die Infotabelle aus der Vorlesung
		Map<K, VertexInfo<K>> m = new HashMap<K, VertexInfo<K>>();
		
		// Prioritätswarteschlange um den nächsten Knoten zu finden 
		// anstatt linearer Suche über die Infotabelle
		TreeSet<VertexInfo<K>> t = initVertices(g, from, m);
		
		// Finde den ersten Knoten (hier "from")
		VertexInfo<K> v = nearestUnknownVertex(t);
		while (v != null) {
			v.known = true;
			for (K adj : g.getAdjacentVertices(v.source)) {
				VertexInfo<K> w = m.get(adj);
				if (!w.known) {
					double weight = g.getEdgeWeight(v.source, w.source);
					if (v.dist + weight < w.dist) {
						decrease(t, w, v.dist + weight);
						w.via = v.source;
					}
				}
			}
			v = nearestUnknownVertex(t);
		}
		return constructPath(g, to, m);
	}

	private static <K> TreeSet<VertexInfo<K>> initVertices(WeightedGraph<K> g, K s,
			Map<K, VertexInfo<K>> m) {
		for (K x : g.getVertices()) {
			m.put(x, new VertexInfo<K>(x));
		}
		m.get(s).dist = 0;
		TreeSet<VertexInfo<K>> set = new TreeSet<VertexInfo<K>>(
				new Comparator<VertexInfo<K>>() {
					public int compare(VertexInfo<K> v1, VertexInfo<K> v2) {
						if (v1.dist < v2.dist)
							return -1;
						else if (v1.dist > v2.dist)
							return 1;
						else
							return v1.label.compareTo(v2.label);
					}
				});
		set.addAll(m.values());
		return set;
	}

	private static <K> VertexInfo<K> nearestUnknownVertex(TreeSet<VertexInfo<K>> t) {
		if (!t.isEmpty()) {
			VertexInfo<K> n = t.first();
			t.remove(n);
			return n;
		} else
			return null;
	}

	private static <K> void decrease(TreeSet<VertexInfo<K>> t, VertexInfo<K> w,
			double newDist) {
		if (t.remove(w)) {
			w.dist = newDist;
			t.add(w);
		}
	}

	private static <K> Graph<K> constructPath(WeightedGraph<K> g, K target,
			Map<K, VertexInfo<K>> m) {
		WeightedGraph<K> path = new WeightedGraphImpl<K>(new ch.fhnw.algd2.graphedit.AdjListGraph<K>(g
				.isDirected()));
		double totalWeight = 0.0;
		K current = target;
		path.addVertex(current);
		K via = m.get(current).via;
		while (via != null) {
			path.addVertex(via);
			double w = g.getEdgeWeight(via, current);
			path.addEdge(via, current, w);
			totalWeight += w;
			current = via;
			via = m.get(current).via;
		}
		System.out.println("The minimum distance from " + current + " to " + target
				+ " is: " + totalWeight);
		return path;
	}
}
