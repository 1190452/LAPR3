package lapr.project.model.Graph;

import java.util.Iterator;
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
    private Map<V, Edge<V,E>> outVerts; //adjacent vertices

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

        for (V vert : outVerts.keySet())
            if (edge.equals(outVerts.get(vert)))
                return vert;

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
    public boolean equals(Object otherObj) {

        if (this == otherObj){
            return true;
        }

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        Vertex<V,E> otherVertex = (Vertex<V,E>) otherObj;

        if (this.key != otherVertex.key)
            return false;

        if (this.element != null && otherVertex.element != null &&
                !this.element.equals(otherVertex.element))
            return false;

        //adjacency vertices should be equal
        if (this.numAdjVerts() != otherVertex.numAdjVerts())
            return false;

        //and edges also
        Iterator<Edge<V,E>> it1 = this.getAllOutEdges().iterator();
        while (it1.hasNext()){
            Iterator<Edge<V,E>> it2 = otherVertex.getAllOutEdges().iterator();
            boolean exists=false;
            while (it2.hasNext()){
                if (it1.next().equals(it2.next()))
                    exists=true;
            }
            if (!exists)
                return false;
        }
        return true;
    }

    @Override
    public Vertex<V,E> clone() {

        Vertex<V,E> newVertex = new Vertex<>();

        newVertex.setKey(key);
        newVertex.setElement(element);

        for (V vert : outVerts.keySet())
            newVertex.addAdjVert(vert, this.getEdge(vert));

        return newVertex;
    }

    @Override
    public String toString() {
        String st="";
        if (element != null)
            st= element + " (" + key + "): \n";
        if (!outVerts.isEmpty())
            for (V vert : outVerts.keySet())
                st += outVerts.get(vert);

        return st;
    }

}

