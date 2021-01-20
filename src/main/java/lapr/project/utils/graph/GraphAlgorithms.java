
package lapr.project.utils.graph;

import lapr.project.model.Address;

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

    private GraphAlgorithms() {
        throw new IllegalStateException("GraphAlgorithms class");
    }

    /**
     * Transforms a graph into its transitive closure 
     * uses the Floyd-Warshall algorithm
     *
     * @param graph Graph object
     * @param dummyEdge object to insert in the newly created edges
     * @return the new graph 
     */
    public static AdjacencyMatrixGraph<Address, Double> transitiveClosure(AdjacencyMatrixGraph<Address, Double> graph, Double dummyEdge) {

        AdjacencyMatrixGraph<Address, Double> newGraph = graph;

        for (int k = 0; k < graph.numVertices; k++) {
            for (int i = 0; i < graph.numVertices; i++) {
                if (graph.getEdge(graph.vertices.get(i), graph.vertices.get(k)) != null && i != k) {
                    for (int j = 0; j < graph.numVertices; j++) {
                        if (graph.getEdge(graph.vertices.get(k), graph.vertices.get(j)) != null && i != j && k != j) {
                            dummyEdge = graph.getEdge(graph.vertices.get(k), graph.vertices.get(j)).doubleValue() + graph.getEdge(graph.vertices.get(i), graph.vertices.get(k)).doubleValue();
                            if(newGraph.getEdge(graph.vertices.get(i), graph.vertices.get(j)) == null){
                                newGraph.insertEdge(graph.vertices.get(i), graph.vertices.get(j), dummyEdge);
                            }
                            else if(dummyEdge < newGraph.getEdge(graph.vertices.get(i), graph.vertices.get(j)).doubleValue()){
                                newGraph.edgeMatrix[i][j] = dummyEdge;
                            }
                        }
                    }
                }
            }
        }
        return newGraph;
    }


}
