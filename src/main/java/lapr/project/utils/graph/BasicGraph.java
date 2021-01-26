
package lapr.project.utils.graph;

/**
 *
 * @author DEI-ESINF
 */
public interface BasicGraph<V,E> {	

  /** Returns the number of vertices in the graph 
   * @return number of vertices of the graph
   * */
  int numVertices();


  /** Returns the actual vertices of the graph 
   * @return an iterable collection of vertices
   * */
  Iterable<V> vertices();


  /** Returns the edge between two vertices
   * @param the two vertices 
   * @return the edge or null if source and dest are not adjacent or do not exist in the graph. 
   */
  E getEdge(V va, V vb);

  /** Inserts a new vertex with the given element. 
   * @param the vertex contents
   * @return false if vertex exists
  */
  boolean insertVertex(V newVertex);

  /** Inserts a new edge between two vertices 
   * @param the two vertices and the new edge contents
   * @return false if either vertices are not in the graph or an edge already exists between the two.
   */
  boolean insertEdge(V va, V vb, E newEdge);

  
}

