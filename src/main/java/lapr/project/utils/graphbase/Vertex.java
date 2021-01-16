package lapr.project.utils.graphbase;

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
    private final Map<V, Edge<V,E>> outVerts; //adjacent vertices

    public Vertex () {
        key = -1; element = null; outVerts = new LinkedHashMap<>();}

    public Vertex (int k, V vInf) {
        key = k; element = vInf; outVerts = new LinkedHashMap<>(); }

    public Vertex (Vertex<V,E> v) {
        key = v.getKey(); element = v.getElement();
        outVerts = new LinkedHashMap<>();
        for (V vert : v.outVerts.keySet()){
            Edge<V,E> edge = v.outVerts.get(vert);
            outVerts.put(vert, edge);
        }
    }

    public int getKey() { return key; }
    public void setKey(int k) { key = k; }

    public V getElement() { return element; }
    public void setElement(V vInf) { element = vInf; }

    public void addAdjVert(V vAdj, Edge<V,E> edge){ outVerts.put(vAdj, edge); }

    public V getAdjVert(Edge<V,E> edge){

        for (Map.Entry<V, Edge<V, E>> vert : outVerts.entrySet())
            if (edge.equals(outVerts.get(vert.getKey())))
                return vert.getKey();

        return null;
    }

    public void remAdjVert(V vAdj){ outVerts.remove(vAdj); }

    public Edge<V,E> getEdge(V vAdj){ return outVerts.get(vAdj); }

    public int numAdjVerts() { return outVerts.size();}

    public Iterable<V> getAllAdjVerts() {  return outVerts.keySet(); }

    public Iterable<Edge<V,E>> getAllOutEdges() {  return outVerts.values(); }


}
