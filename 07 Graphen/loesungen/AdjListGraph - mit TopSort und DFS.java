package ch.fhnw.algd2.graphedit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AdjListGraph<K> extends AbstractGraph<K> implements
		GraphAlgorithms.TopSort, GraphAlgorithms.DFS<K> {
	private static class Vertex<K> {
		K data;
		int indegree = 0, deg;
		boolean visited;
		List<Vertex<K>> adjList = new LinkedList<Vertex<K>>();

		Vertex(K vertex) {
			data = vertex;
		}

		boolean addEdgeTo(Vertex<K> to) {
			return (adjList.contains(to)) ? false : adjList.add(to);
		}
	}
	private Map<K, Vertex<K>> vertices;
	private int nofEdges = 0;

	public AdjListGraph() { // default constructor
		this(false);
	}

	public AdjListGraph(boolean directed) {
		super(directed);
		vertices = new HashMap<K, Vertex<K>>();
	}

	public AdjListGraph(AdjListGraph<K> orig) { // copy constructor
		this(orig.isDirected());
		for (K k : orig.vertices.keySet()) {
			addVertex(k);
		}
		for (Vertex<K> v : orig.vertices.values()) {
			for (Vertex<K> w : v.adjList) {
				addEdge(v.data, w.data);
			}
		}
	}

	public boolean addVertex(K vertex) {
		if (vertex != null && !vertices.containsKey(vertex)) {
			vertices.put(vertex, new Vertex<K>(vertex));
			return true;
		} else {
			return false;
		}
	}

	public boolean addEdge(K from, K to) {
		Vertex<K> vf = vertices.get(from);
		Vertex<K> vt = vertices.get(to);
		if (vf != null && vt != null && vf.addEdgeTo(vt)) {
			vt.indegree++;
			if (!isDirected()) {
				vt.addEdgeTo(vf);
				vf.indegree++;
			}
			nofEdges++;
			return true;
		} else {
			return false;
		}
	}

	public boolean removeEdge(K from, K to) {
		Vertex<K> vf = vertices.get(from);
		Vertex<K> vt = vertices.get(to);
		if (vf != null && vt != null && vf.adjList.contains(vt)) {
			vf.adjList.remove(vt);
			vt.indegree--;
			if (!isDirected()) {
				vt.adjList.remove(vf);
				vf.indegree--;
			}
			nofEdges--;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getNofVertices() {
		return vertices.size();
	}

	@Override
	public int getNofEdges() {
		return nofEdges;
	}

	public Set<K> getVertices() {
		return new HashSet<K>(vertices.keySet());
	}

	public Set<K> getAdjacentVertices(K vertex) {
		Set<K> set = new HashSet<K>();
		Vertex<K> vt = vertices.get(vertex);
		if (vt != null) {
			for (Vertex<K> v : vt.adjList) {
				set.add(v.data);
			}
		}
		return set;
	}

	@Override
	public Object clone() {
		return new AdjListGraph<K>(this);
	}

	/**
	 * Topologically sort the vertices of this graph. All output is done on the
	 * console. If this graph is not a DAG (i.e. it has cycles) then a
	 * corresponding message will appear on the console. Otherwise, the console
	 * will display a possible topological order of the vertices.
	 */
	public void sort() {
		StringBuffer sb = new StringBuffer();
		if (isDirected()) {
			LinkedList<Vertex<K>> queue = new LinkedList<Vertex<K>>();
			int counter = 0;
			for (Vertex<K> v : vertices.values()) {
				v.deg = v.indegree; // set indegree of each vertex and define start set
				if (v.deg == 0) queue.addFirst(v);
			}
			while (!queue.isEmpty()) {
				Vertex<K> v = queue.removeLast();
				sb.append(v.data + "  ");
				counter++; // count processed vertices
				for (Vertex<K> w : v.adjList)
					if (--w.deg == 0) // decrease indegree of adjecent vertices.
					queue.addFirst(w); // If it drops to 0, schedule for processing
			}
			if (counter != vertices.size()) {
				sb.replace(0, sb.length(), "Cycle found");
			}
		} else {
			sb.append("Graph is not directed, TopSort not possible.");
		}
		System.out.println(sb);
	}

	/**
	 * A helper method doing the actual work of depth-first-traversing the graph.
	 * It prints the vertices visited to the console and constructs a new graph,
	 * which represents the spanning tree constructed by this traversal. Works
	 * recursively.
	 * 
	 * @param root
	 *          staring vertex for this level of the recursion.
	 * @param spanningTree
	 *          the graph that represents the spanning tree to construct.
	 */
	private void dfs(Vertex<K> root, Graph<K> spanningTree) {
		System.out.print(root.data + "  ");
		root.visited = true;
		for (Vertex<K> v : root.adjList) {
			if (!v.visited) {
				spanningTree.addVertex(v.data);
				spanningTree.addEdge(root.data, v.data);
				dfs(v, spanningTree);
			}
		}
	}

	/**
	 * Perform a depth-first-search on this graph, starting at the given vertex. A
	 * DFS order will be printed to the console and a spanning tree (represented
	 * by a newly constructed graph object) will be returned. This method works on
	 * both, directed and undirected graphs!
	 * 
	 * @param startVertex
	 *          begin the depth-first-search here.
	 */
	public Graph<K> traverse(K startVertex) {
		Graph<K> spanningTree = new AdjListGraph<K>(isDirected());
		Vertex<K> v = vertices.get(startVertex);
		if (v != null) {
			for (Vertex<K> w : vertices.values())
				w.visited = false;
			spanningTree.addVertex(startVertex);
			dfs(v, spanningTree);
			System.out.println();
		} else {
			System.out.println("unknown starting vertex");
		}
		return spanningTree;
	}
}