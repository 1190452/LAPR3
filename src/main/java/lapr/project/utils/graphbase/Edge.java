package lapr.project.utils.graphbase;

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

    public Edge() {
        element = null; weight= 0.0; vOrig=null; vDest=null; }

    public Edge(E eInf, double ew, Vertex<V,E> vo, Vertex<V,E> vd) {
        element = eInf; weight= ew; vOrig=vo; vDest=vd;}

    public E getElement() { return element; }
    public void setElement(E eInf) { element = eInf; }

    public double getWeight() { return weight; }
    public void setWeight(double ew) { weight= ew; }

    public V getVOrig() {
        if (this.vOrig != null)
            return vOrig.getElement();
        return null;
    }
    public void setVOrig(Vertex<V,E> vo) { vOrig= vo; }

    public V getVDest() {
        if (this.vDest != null)
            return vDest.getElement();
        return null;
    }
    public void setVDest(Vertex<V,E> vd) { vDest= vd; }

    public V[] getEndpoints() {

        V oElem=null;
        V dElem=null;
        V typeElem=null;

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
