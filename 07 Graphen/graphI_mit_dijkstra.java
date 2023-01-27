import java.util.*;

public class GraphI {
    private final int[][] adjMatrix;
    public final int n;

    public GraphI(int numNodes) {
        if (numNodes < 1)
            throw new IllegalArgumentException();
        this.adjMatrix = new int[numNodes][numNodes];
        this.n = numNodes;
    }

    class PriorityVertex {
        int id;
        int dist;
        int via = Integer.MIN_VALUE;

        PriorityVertex(int id, int dist){
            this.id = id;
            this.dist = dist;
        }
    }

    private int[] dijkstra(int s) {
        PriorityVertex[] mynodes = new PriorityVertex[n];
        for (int i = 0; i < n; i++) {
            mynodes[i] = new PriorityVertex(i, Integer.MAX_VALUE);
        }
        mynodes[s].dist = 0;

        PriorityQueue<PriorityVertex> R = new PriorityQueue<>(n, Comparator.comparingInt(o -> o.dist));
        R.add(new PriorityVertex(s, 0));

        while (!R.isEmpty()) {
            PriorityVertex node = R.remove();
            int v = node.id;
            for (int i = 0; i < n; i++) {
                if (adjMatrix[v][i] < Integer.MAX_VALUE) {

                    if (mynodes[i].dist > mynodes[v].dist + adjMatrix[v][i]) {
                        R.remove(mynodes[i]);
                        mynodes[i].dist = mynodes[v].dist + adjMatrix[v][i];
                        mynodes[i].via = v;
                        R.add(mynodes[i]);
                    }
                }
            }
        }        
        int[] result = new int[n];
        for (int i =0; i<n; i++) result[i] = mynodes[i].dist;
        return result;
    }
}
