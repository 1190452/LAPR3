
package lapr.project.model.adjacencyMatrixGraph;

import lapr.project.model.Graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Implementation of graph algorithms for a (undirected) graph structure 
 * Considering generic vertex V and edge E types
 * 
 * Works on AdjancyMatrixGraph objects
 * 
 * @author DEI-ESINF
 * 
 */
public class GraphAlgorithms {

    /**
     * Reverses the path
     * @param path stack with path
     */
    private static<V,E> LinkedList<V> revPath(LinkedList<V> path){

        LinkedList<V> pathcopy = new LinkedList<>(path);
        LinkedList<V> pathrev = new LinkedList<>();

        while (!pathcopy.isEmpty())
            pathrev.push(pathcopy.pop());

        return pathrev ;
    }
    /**
     * Returns all paths from vOrig to vDest
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param vDest Vertex that will be the end of the path
     * @param path stack with vertices of the current path (the path is in reverse order)
     * @param paths ArrayList with all the paths (in correct order)
     */
    private static<V,E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                      LinkedList<V> path, ArrayList<LinkedList<V>> paths){
        visited[g.getKey(vOrig)] = true;
        path.push(vOrig);
        for (V vAdj : g.adjVertices(vOrig)) {
            if (vAdj.equals(vDest)) {
                path.push(vDest);
                paths.add(revPath(path));
                path.pop();
            } else {
                if (!visited[g.getKey(vAdj)]) {
                    allPaths(g, vAdj, vDest, visited, path, paths);
                }
            }
        }
        V vertex = path.pop();
        visited[g.getKey(vertex)] = false;
    }

    /**
     * @param g Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from voInf to vdInf
     */
    public static<V,E> ArrayList<LinkedList<V>> allPaths(Graph<V,E> g, V vOrig, V vDest){
        if(!g.validVertex(vOrig) || !g.validVertex(vDest)) return null;

        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        LinkedList<V> path = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        if (g.validVertex(vOrig) && g.validVertex(vDest)) {
            allPaths(g, vOrig, vDest, visited, path, paths);
        }

        return paths;

    }
    /**
     * Performs depth-first search of the graph starting at vertex.
     * Calls package recursive version of the method.
     *
     * @param graph  Graph object
     * @param vertex Vertex of graph that will be the source of the search
     * @return queue of vertices found by search (including vertex), null if vertex does not exist
     */
    public static <V, E> LinkedList<V> BFS(AdjacencyMatrixGraph<V, E> graph, V vertex) {

        int index = graph.toIndex(vertex);
        if (index == -1)
            return null;

        LinkedList<V> resultQueue = new LinkedList<V>();
        LinkedList<Integer> auxQueue = new LinkedList<Integer>();

        resultQueue.add(graph.vertices.get(index));
        auxQueue.add(index);

        while (!auxQueue.isEmpty()) {
            index = auxQueue.remove();
            for (int i = 0; i < graph.numVertices; i++) {
                if (graph.edgeMatrix[index][i] != null) {
                    if (!resultQueue.contains(graph.vertices.get(i))) {
                        resultQueue.add(graph.vertices.get(i));
                        auxQueue.add(i);
                    }
                }
            }
        }
        return resultQueue;
    }


    /**
     * Performs depth-first search of the graph starting at vertex.
     * Calls package recursive version of the method.
     *
     * @param graph  Graph object
     * @param vertex Vertex of graph that will be the source of the search
     * @return queue of vertices found by search (empty if none), null if vertex does not exist
     */
    public static <V, E> LinkedList<V> DFS(AdjacencyMatrixGraph<V, E> graph, V vertex) {
        int index = graph.toIndex(vertex);
        if (index == -1) {
            return null;
        }
        LinkedList<V> vert = new LinkedList<>();
        vert.add(vertex);
        DFS(graph, index, vert);

        return vert;
    }

    /**
     * Actual depth-first search of the graph starting at vertex.
     * The method adds discovered vertices (including vertex) to the queue of vertices
     *
     * @param graph         Graph object
     * @param index         Index of vertex of graph that will be the source of the search
     * @param verticesQueue queue of vertices found by search
     */
    static <V, E> void DFS(AdjacencyMatrixGraph<V, E> graph, int index, LinkedList<V> verticesQueue) {
        for (int i = 0; i < graph.numVertices; i++) {
            if (graph.edgeMatrix[index][i] != null && !verticesQueue.contains(graph.vertices.get(i))) {
                verticesQueue.add(graph.vertices.get(i));
                DFS(graph, i, verticesQueue);
            }
        }
    }


    /**
     * Transforms a graph into its transitive closure
     * uses the Floyd-Warshall algorithm
     *
     * @param graph     Graph object
     * @param dummyEdge object to insert in the newly created edges
     * @return the new graph
     */

    public static <V, E> AdjacencyMatrixGraph<V, E> transitiveClosure(AdjacencyMatrixGraph<V, E> graph, E dummyEdge) {

        AdjacencyMatrixGraph<V, E> newGraph = (AdjacencyMatrixGraph<V, E>) graph.clone();

        for (int k = 0; k < newGraph.numVertices; k++)
            for (int i = 0; i < newGraph.numVertices; i++)
                if (i != k && newGraph.edgeMatrix[i][k] != null) {
                    for (int j = 0; j < newGraph.numVertices; j++)
                        if (i != j && j != k && newGraph.edgeMatrix[k][j] != null)
                            if (newGraph.edgeMatrix[i][j] == null)
                                newGraph.insertEdge(i, j, dummyEdge);
                }
        return newGraph;
    }

}




