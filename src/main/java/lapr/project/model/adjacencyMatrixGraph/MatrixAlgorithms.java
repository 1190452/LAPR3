package lapr.project.model.adjacencyMatrixGraph;

import lapr.project.model.Address;

public class MatrixAlgorithms {

    /**
     * Transforms a graph into its transitive closure uses the Floyd-Warshall algorithm
     * (Slides teóricos - Graphs)
     *
     * @param graph     Graph object
     * @return the new graph
     */
    public static AdjacencyMatrixGraph transitiveClosure(AdjacencyMatrixGraph<Address, Double> graph) {
        double maxDist = 0;
        for (int k = 0; k < graph.numVertices; k++) {
            for (int i = 0; i < graph.numVertices; i++) {
                if (i != k && (graph.privateGet(i, k)) != null) {
                    for (int j = 0; j < graph.numVertices; j++) {
                        if (i != j && k != j && graph.privateGet(k, j) != null) {
                            if (graph.privateGet(i, j) == null) {
                                double dist = (graph.privateGet(i, k)) + (graph.privateGet(k, j));
                                graph.privateSet(i, j, dist);
                                if (dist > maxDist) {
                                    maxDist = dist;
                                }
                            }
                        }
                    }
                }
            }
        }
        return (int) maxDist;

    }

}

