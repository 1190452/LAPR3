package lapr.project.model.Graph;

import java.lang.reflect.Array;

/**
 *
 * @author DEI-ESINF
 * @param <V>
 * @param <E>
 */

public class Edge<V,E> implements Comparable {

    private E element;           // Edge information
    private double weight;       // Edge weight
    private Vertex<V,E> vOrig;  // vertex origin
    private Vertex<V,E> vDest;  // vertex destination

    /**
     *
     */
    public Edge() {
        element = null; weight= 0.0; vOrig=null; vDest=null; }

    /**
     *
     * @param eInf
     * @param ew
     * @param vo
     * @param vd
     */
    public Edge(E eInf, double ew, Vertex<V,E> vo, Vertex<V,E> vd) {
        element = eInf; weight= ew; vOrig=vo; vDest=vd;}

    /**
     *
     * @return
     */
    public E getElement() { return element; }

    /**
     *
     * @param eInf
     */
    public void setElement(E eInf) { element = eInf; }

    /**
     *
     * @return
     */
    public double getWeight() { return weight; }

    /**
     *
     * @param ew
     */
    public void setWeight(double ew) { weight= ew; }

    /**
     *
     * @return
     */
    public V getVOrig() {
        if (this.vOrig != null)
            return vOrig.getElement();
        return null;
    }

    /**
     *
     * @param vo
     */
    public void setVOrig(Vertex<V,E> vo) { vOrig= vo; }

    /**
     *
     * @return
     */
    public V getVDest() {
        if (this.vDest != null)
            return vDest.getElement();
        return null;
    }

    /**
     *
     * @param vd
     */
    public void setVDest(Vertex<V,E> vd) { vDest= vd; }

    /**
     *
     * @return
     */
    public V[] getEndpoints() {

        V oElem=null, dElem=null, typeElem=null;

        if (this.vOrig != null)
            oElem = vOrig.getElement();

        if (this.vDest != null)
            dElem = vDest.getElement();

        if (oElem == null && dElem == null)
            return null;

        if (oElem != null)          // To get type
            typeElem = oElem;

        if (dElem != null)
            typeElem = dElem;

        V[] endverts = (V [])Array.newInstance(typeElem.getClass(), 2);

        endverts[0]= oElem;
        endverts[1]= dElem;

        return endverts;
    }


    @Override
    public int compareTo(Object otherObject) {

        Edge<V,E> other = (Edge<V,E>) otherObject ;
        if (this.weight < other.weight)  return -1;
        if (this.weight == other.weight) return 0;
        return 1;
    }

    @Override
    public Edge<V,E> clone() {

        Edge<V,E> newEdge = new Edge<>();

        newEdge.element = element;
        newEdge.weight = weight;
        newEdge.vOrig = vOrig;
        newEdge.vDest = vDest;

        return newEdge;
    }

    @Override
    public String toString() {
        String st="";
        if (element != null)
            st= "      (" + element + ") - ";
        else
            st= "\t ";

        if (weight != 0)
            st += weight +" - " +vDest.getElement()+ "\n";
        else
            st += vDest.getElement()+ "\n";

        return st;
    }

}