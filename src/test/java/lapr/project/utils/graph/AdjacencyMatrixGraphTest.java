package lapr.project.utils.graph;

import lapr.project.model.Address;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphGraphTest {
    private AdjacencyMatrixGraph instance;

    public AdjacencyMatrixGraphGraphTest() {
        instance = new AdjacencyMatrixGraph();
    }

    /**
     * Test of privateGet method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testPrivateGet() {
        System.out.println("privateGet");
        int x = 0;
        int y = 0;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        Object expResult = null;
        Object result = instance.privateGet(x, y);
        assertEquals(expResult, result);
    }

    /**
     * Test of privateSet method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testPrivateSet() {
        System.out.println("privateSet");
        int x = 0;
        int y = 0;
        Double e = null;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        instance.privateSet(x, y, e);
    }

    /**
     * Test of toIndex method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testToIndex() {
        System.out.println("toIndex");
        Address vertex = null;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph<>();
        int expResult = -1;
        int result = instance.toIndex(vertex);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of numVertices method, of class AdjacencyMatrixGraph.
//     */
//    @Test
//    public void testNumVertices() {
//        System.out.println("numVertices");
//        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
//        int expResult = 0;
//        int result = instance.numVertices();
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of numEdges method, of class AdjacencyMatrixGraph.
//     */
//    @Test
//    public void testNumEdges() {
//        System.out.println("numEdges");
//        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
//        int expResult = 0;
//        int result = instance.numEdges();
//        assertEquals(expResult, result);
//    }

    /**
     * Test of vertices method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testVertices() {
        System.out.println("vertices");
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        Address p = new Address(1, 231.321, "ponto1", 2, "242-02", "Porto");
        instance.insertVertex(p);
        Iterable<Address> expResult = instance.vertices();
        Iterable<Address> result = instance.vertices();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEdge method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testGetEdge() {
        System.out.println("getEdge");
        Address vertexA = null;
        Address vertexB = null;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        Double expResult = null;
        Object result = instance.getEdge(vertexA, vertexB);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertVertex method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testInsertVertex() {
        System.out.println("insertVertex");
        Address newVertex = null;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        boolean expResult = true;
        boolean result = instance.insertVertex(newVertex);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertEdge method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testInsertEdge_3args_1() {
        System.out.println("insertEdge");
        int indexA = 0;
        int indexB = 0;
        Double newEdge = null;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        instance.insertEdge(indexA, indexB, newEdge);
    }

    /**
     * Test of insertEdge method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testInsertEdge_3args_2() {
        System.out.println("insertEdge");
        Address vertexA = new Address(1.291,232.10, "rua1", 91, "8271-10", "Porto");
        Address vertexB = new Address(1.2291,10.102, "rua2", 96, "8271-10", "Porto");
        Double newEdge = 4.0;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        boolean expResult = false;
        boolean result = instance.insertEdge(vertexA, vertexB, newEdge);
        assertEquals(expResult, result);
    }


    /**
     * Test of hashCode method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testHashCode3() {
        AdjacencyMatrixGraph ad1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        Map<AdjacencyMatrixGraph, String> map = new HashMap<>();
        map.put(ad1, "dummy");
        Assert.assertEquals(null, map.get(ad2));
    }

}