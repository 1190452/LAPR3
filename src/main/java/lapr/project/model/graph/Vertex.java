package lapr.project.model.graph;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author DEI-ESINF
 * @param <V>
 * @param <E>
 */
public class Vertex<V, E> {

    private int key ;                     //Vertex key number
    private V  element ;                 //Vertex information
    private final Map<V, Edge<V,E>> outVerts; //adjacent vertices

    /**
     *
     */
    public Vertex () {
        key = -1; element = null; outVerts = new LinkedHashMap<>();}

    /**
     *
     * @param k
     * @param vInf
     */
    public Vertex (int k, V vInf) {
        key = k; element = vInf; outVerts = new LinkedHashMap<>(); }

    /**
     *
     * @param v
     */
    public Vertex (Vertex<V,E> v) {
        key = v.getKey(); element = v.getElement();
        outVerts = new LinkedHashMap<>();
        for (V vert : v.outVerts.keySet()){
            Edge<V,E> edge = v.outVerts.get(vert);
            outVerts.put(vert, edge);
        }
    }

    /**
     *
     * @return
     */
    public int getKey() { return key; }

    /**
     *
     * @param k
     */
    public void setKey(int k) { key = k; }

    /**
     *
     * @return
     */
    public V getElement() { return element; }

    /**
     *
     * @param vInf
     */
    public void setElement(V vInf) { element = vInf; }

    /**
     *
     * @param vAdj
     * @param edge
     */
    public void addAdjVert(V vAdj, Edge<V,E> edge){ outVerts.put(vAdj, edge); }

    /**
     *
     * @param edge
     * @return
     */
    public V getAdjVert(Edge<V,E> edge){

        for (Map.Entry<V, Edge<V, E>> vert : outVerts.entrySet())
            if (edge.equals(outVerts.get(vert.getKey())))
                return vert.getKey();

        return null;
    }

    /**
     *
     * @param vAdj
     */
    public void remAdjVert(V vAdj){ outVerts.remove(vAdj); }

    /**
     *
     * @param vAdj
     * @return
     */
    public Edge<V,E> getEdge(V vAdj){ return outVerts.get(vAdj); }

    /**
     *
     * @return
     */
    public int numAdjVerts() { return outVerts.size();}

    /**
     *
     * @return
     */
    public Iterable<V> getAllAdjVerts() {  return outVerts.keySet(); }

    /**
     *
     * @return
     */
    public Iterable<Edge<V,E>> getAllOutEdges() {  return outVerts.values(); }


    @Override
    public Vertex<V,E> clone() {

        Vertex<V,E> newVertex = new Vertex<>();

        newVertex.setKey(key);
        newVertex.setElement(element);

        for (Map.Entry<V, Edge<V, E>> vert : outVerts.entrySet())
            newVertex.addAdjVert(vert.getKey(), this.getEdge(vert.getKey()));

        return newVertex;
    }

    @Override
    public String toString() {
        StringBuilder st= new StringBuilder();
        if (element != null)
            st = new StringBuilder(element + " (" + key + "): \n");
        if (!outVerts.isEmpty())
            for (V vert : outVerts.keySet())
                st.append(outVerts.get(vert));

        return st.toString();
    }

}

