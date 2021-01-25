/*
 * A collection of graph algorithms.
 */
package lapr.project.utils.graphbase;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author DEI-ESINF
 */

public class GraphAlgorithms {

    private GraphAlgorithms() {
        // GraphAlgorithms class
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with nonnegative edge weights
     * This implementation uses Dijkstra's algorithm
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param visited set of discovered vertices
     * @param pathKeys minimum path vertices keys
     * @param dist minimum distances
     */
    public static<V,E> void shortestPathLength(Graph<V,E> g, V vOrig,
                                                  boolean[] visited, int[] pathKeys, double[] dist){

        dist[g.getKey(vOrig)] = 0;
        while (vOrig != null) {
            visited[g.getKey(vOrig)] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                Edge e = g.getEdge(vOrig, vAdj);
                if (!visited[g.getKey(vAdj)] && dist[g.getKey(vAdj)] > (dist[g.getKey(vOrig)] + e.getWeight())) {
                    dist[g.getKey(vAdj)] = dist[g.getKey(vOrig)] + e.getWeight();
                    pathKeys[g.getKey(vAdj)] = g.getKey(vOrig);
                }
            }
            vOrig = null;
            Double minDistance = Double.MAX_VALUE;
            for (V vert : g.allkeyVerts()) {
                if (!visited[g.getKey(vert)] && dist[g.getKey(vert)] < minDistance) {
                    vOrig = vert;
                    minDistance = dist[g.getKey(vert)];
                }
            }
        }

    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     * @param g Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path stack with the minimum path (correct order)
     */
    protected static<V,E> void getPath(Graph<V,E> g, V vOrig, V vDest, V[] verts, int[] pathKeys, LinkedList<V> path){
        path.push(vDest);
        int vKey = pathKeys[g.getKey(vDest)];
        if(vKey != -1) {
            vDest = verts[vKey];
            getPath(g,vOrig,vDest,verts,pathKeys,path);
        }
    }

    //shortest-path between vOrig and vDest
    public static<V,E> double shortestPath(Graph<V,E> g, V vOrig, V vDest, LinkedList<V> shortPath){

        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return 0;
        shortPath.clear();
        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, visited, pathKeys, dist);
        if (dist[g.getKey(vDest)] != Double.MAX_VALUE) {
            getPath(g, vOrig, vDest, vertices, pathKeys, shortPath);
        }
        return shortPath.isEmpty() ? 0 : dist[g.getKey(vDest)];

    }

    //shortest-path between voInf and all other
    public static<V,E> boolean shortestPaths(Graph<V,E> g, V vOrig, List<LinkedList<V>> paths, List<Double> dists){
        if (!g.validVertex(vOrig)) return false;

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, visited, pathKeys, dist);

        dists.clear();
        paths.clear();
        for (int i = 0; i < nverts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (int i = 0; i < nverts; i++) {
            LinkedList<V> shortPath = new LinkedList<>();
            if (dist[i] != Double.MAX_VALUE)
                getPath(g, vOrig, vertices[i], vertices, pathKeys, shortPath);
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;
    }
}