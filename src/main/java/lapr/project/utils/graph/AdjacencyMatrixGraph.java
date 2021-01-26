
package lapr.project.utils.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author DEI-ESINF
 */

public class AdjacencyMatrixGraph<V, E> implements BasicGraph<V, E> {

    public static final int INITIAL_CAPACITY = 10;
    public static final float RESIZE_FACTOR = 1.5F;
	
    int numVertices;
    int numEdges;
    ArrayList<V> vertices;
    E[][] edgeMatrix;

    /**
    * Returns the edge reference associated with edgeMatrix x,y position
    * used as workaround to work with edgeMatrix from the EdgeAsDoubleGraphAlgorithm Class
    * as Java generic types are not available at runtime
    * 
    * @param x,y the position in the matrix
    * @return edge at position (x,y)
    */
    E privateGet(int x, int y){
	return edgeMatrix[x][y];
    }

    /**
    * Set the edge reference associated with edgeMatrix x,y position
    * used as workaround to work with edgeMatrix from the EdgeAsDoubleGraphAlgorithm Class
    * as Java generic types are not available at runtime
    * 
    * @param x,y the position in the matrix
    * @param e the new reference
    */
    void privateSet(int x, int y, E e){
	edgeMatrix[x][y] = e;
    }

    /**
    * Returns the index associated with a vertex
    * 
    * @param vertex vertex
    * @return vertex index, -1 if vertex does not exist in the graph
    */

    int toIndex(V vertex) {
	return vertices.indexOf(vertex);
    }
	
    /**
    * Resizes the matrix when a new vertex increases the length of ArrayList
    */
    private void resizeMatrix() {
	if(edgeMatrix.length < numVertices){
            int newSize = (int) (edgeMatrix.length * RESIZE_FACTOR);

            @SuppressWarnings("unchecked")
            E[][] temp = (E[][]) new Object [newSize][newSize];
            for (int i = 0; i < edgeMatrix.length; i++)
                temp[i] = Arrays.copyOf(edgeMatrix[i], newSize);
	    
            edgeMatrix = temp;
	}
    }

    /**
    * Constructs an empty graph.
    */
    public AdjacencyMatrixGraph() {
	this(INITIAL_CAPACITY);
    }

    /**
    * Constructs a graph with an initial capacity.
    */
    @SuppressWarnings("unchecked")
    public AdjacencyMatrixGraph(int initialSize) {
	vertices = new ArrayList<>(initialSize);
		
	edgeMatrix = (E[][]) new Object[initialSize][initialSize];
    }

    /**
    * Returns the number of vertices in the graph
    * @return number of vertices of the graph
    */
    public int numVertices() {
	return numVertices;
    }

    /**
    * Returns the number of edges in the graph
    * @return number of edges of the graph
    */
    public int numEdges() {
	return numEdges;
    }

    /**
    * Returns the actual vertices of the graph
    * @return an iterable collection of vertices
    */
    @SuppressWarnings("unchecked")
    public Iterable<V> vertices() {
	return (Iterable<V>) vertices.clone();
    }

    /**
     * Returns the edge between two vertices
     * @param vertexA
     * @param vertexB  
     * @return the edge or null if source and dest are not adjacent or do not
     *         exist in the graph.
     */
    public E getEdge(V vertexA, V vertexB) {
        int indexA = toIndex(vertexA);
        if (indexA == -1)
                return null;

        int indexB = toIndex(vertexB);
        if (indexB == -1)
                return null;

        return edgeMatrix[indexA][indexB];
    }

    /**
     * Inserts a new vertex with the given element.
     * fails if vertex already exists
     * @param newVertex (vertex contents)
     * @return false if vertex exists in the graph
     */
    public boolean insertVertex(V newVertex) {
        int index = toIndex(newVertex);
        if (index != -1)
            return false;

        vertices.add(newVertex);
        numVertices++;
        resizeMatrix();
        return true;
    }

    /**
     * Inserts a new edge between two vertices. 
     * Package level method is for use of algorithms class 
     * @param indexA 
     * @param indexB 
     * @param newEdge  
     * @return false if vertices are not in the graph or are the same vertex 
     *         or an edge already exists between the two.
     */
    void insertEdge(int indexA, int indexB, E newEdge){
        if (edgeMatrix[indexA][indexB] == null){
           edgeMatrix[indexA][indexB] = edgeMatrix[indexB][indexA] = newEdge; // undirected graph
           numEdges++;
        }
    }
	
    public boolean insertEdge(V vertexA, V vertexB, E newEdge) {

        if (vertexA.equals(vertexB))
            return false;

        int indexA = toIndex(vertexA);
        if (indexA == -1)
            return false;

        int indexB = toIndex(vertexB);
        if (indexB == -1)
            return false;

        if (edgeMatrix[indexA][indexB] != null)
            return false;

        insertEdge(indexA, indexB, newEdge);

        return true;
    }


    /**
     * Implementation of equals
     * @param oth other graph to test for equality
     * @return true if both objects represent the same graph
     */


    public boolean equals(Object oth) {

        if(oth == null) return false;

        if(this == oth) return true;

        if (!(oth instanceof AdjacencyMatrixGraph<?, ?>))
            return false;

        AdjacencyMatrixGraph<?, ?> other = (AdjacencyMatrixGraph<?, ?>) oth;

        if(numVertices != other.numVertices || numEdges != other.numEdges) return false;

        return (vertices.equals(other.vertices));

        // fails to recognise difference between objects with different <E> type
        // when vertices are the same and both graphs have no edges

    }

    @Override
    public int hashCode() {
        return Objects.hash(numVertices, numEdges, vertices);
    }
}