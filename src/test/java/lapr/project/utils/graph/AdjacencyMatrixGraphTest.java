package lapr.project.utils.graph;

import lapr.project.model.Address;
import lapr.project.model.Park;
import lapr.project.model.Vehicle;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph instance;

    public AdjacencyMatrixGraphTest() {
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

    /**
     * Test of numVertices method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testNumVertices() {
        System.out.println("numVertices");
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        int expResult = 0;
        int result = instance.numVertices();
        assertEquals(expResult, result);
    }

    /**
     * Test of numVertices method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testNumVertices2() {
        System.out.println("numVertices");
        AdjacencyMatrixGraph<Integer, Integer> instance = new AdjacencyMatrixGraph<>();
        instance.insertVertex(4);
        int expResult = 1;
        int result = instance.numVertices();
        assertEquals(expResult, result);
    }

    /**
     * Test of numEdges method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testNumEdges() {
        System.out.println("numEdges");
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        int expResult = 0;
        int result = instance.numEdges();
        assertEquals(expResult, result);
    }

    /**
     * Test of numEdges method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testNumEdges2() {
        System.out.println("numEdges");
        AdjacencyMatrixGraph<Integer, Integer> instance = new AdjacencyMatrixGraph<>();
        instance.insertVertex(4);
        instance.insertVertex(5);
        Integer a = 3;
        instance.insertEdge(4,5,a);
        int expResult = 1;
        int result = instance.numEdges();
        assertEquals(expResult, result);
    }

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
        Address vertexA = new Address(1, 231.321, "ponto1", 2, "242-02", "Porto");
        Address vertexB = null;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        Double expResult = null;
        Object result = instance.getEdge(vertexA, vertexB);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetEdge2() {
        System.out.println("getEdge");
        Address vertexA = null;
        Address vertexB = new Address(1, 231.321, "ponto1", 2, "242-02", "Porto");
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
     * Test of insertVertex method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testInsertVertex2() {
        System.out.println("insertVertex");
        Address newVertex = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        instance.insertVertex(newVertex);
        boolean expResult = false;
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
     * Test of equals method, of class AdjacencyMatrix. (comparing same object/reference)
     */
    @Test
    public void testEquals() {
        boolean expected = true;
        boolean obtained = instance.equals(instance);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (first If condition, object being null)
     */
    @Test
    public void testEquals2() {
        AdjacencyMatrixGraph ad1 = null;
        AdjacencyMatrixGraph ad2 = instance = new AdjacencyMatrixGraph(10);   //constructor 1
        AdjacencyMatrixGraph ad3 = new AdjacencyMatrixGraph();                //constructor 2

        boolean expected = false;
        boolean obtained = ad2.equals(ad1);
        boolean obtained2 = ad3.equals(ad1);

        assertEquals(expected, obtained);
        assertEquals(expected, obtained2);
    }

    /**
     * Test of equals method, of class AdjacencyMatrixGraph. (different classes)
     */
    @Test
    public void testEquals3() {
        Address a = new Address(-34.6131500, -58.3772300, "rua", 23, "2381-10", "porto", 12);
        Park park = new Park(1, 20, 10, 20, 12, 210.01, 1, 2);
        Vehicle scoot = new Vehicle(1,"AH-87-LK",400,350,0,1,500,8.0,5000.0,430,4, 1,10,2.3);
        AdjacencyMatrixGraph ad1 = instance = new AdjacencyMatrixGraph(10);
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();

        boolean expected = false;
        boolean obtained = park.equals(ad1);
        boolean obtained2 = park.equals(ad2);
        boolean obtained3 = scoot.equals(ad1);
        boolean obtained4 = scoot.equals(ad2);

        assertEquals(expected, obtained);
        assertEquals(expected, obtained2);
        assertEquals(expected, obtained3);
        assertEquals(expected, obtained4);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (false 'instanceOf')
     */
    @Test
    public void testEquals4() {
        Object objectTest = new Object();

        boolean expected = false;
        boolean obtained = objectTest instanceof AdjacencyMatrixGraph;

        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (first condidition after cast - numEdges different, but vertices equal)
     */
    @Test
    public void testEquals5() {
        AdjacencyMatrixGraph ad1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        ad1.numEdges = 15;
        ad2.numEdges = 20;
        ad1.numVertices = 30;
        ad2.numVertices = 30;

        boolean expected = false;
        boolean result = ad1.equals(ad2);

        assertEquals(expected, result);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (first condidition after cast - numEdges equal, but vertices different)
     */
    @Test
    public void testEquals6() {
        AdjacencyMatrixGraph ad1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        ad1.numEdges = 15;
        ad2.numEdges = 15;
        ad1.numVertices = 30;
        ad2.numVertices = 35;

        boolean expected = false;
        boolean result = ad1.equals(ad2);

        assertEquals(expected, result);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (first condidition after cast - both different)
     */
    @Test
    public void testEquals7() {
        AdjacencyMatrixGraph ad1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        ad1.numEdges = 15;
        ad2.numEdges = 20;
        ad1.numVertices = 30;
        ad2.numVertices = 35;

        boolean expected = false;
        boolean result = ad1.equals(ad2);

        assertEquals(expected, result);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (first condidition after cast(both equal) but second condition - false)
     */
    @Test
    public void testEquals8() {
        AdjacencyMatrixGraph ad1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        //Both numEdges and numVertices equal
        ad1.numEdges = 0;
        ad2.numEdges = 0;
        ad1.numVertices = 0;
        ad2.numVertices = 0;

        Address instance1 = new Address(-34.6131500, -58.3772300, "rua", 23, "2381-10", "porto", 12);
        Address instance2 = new Address(-31.231500, -10.972300, "rua", 20, "2381-10", "porto", 12);

        ad1.insertVertex(instance1);
        ad2.insertVertex(instance2);

        boolean expected = false;
        boolean result = ad1.equals(ad2);

        assertEquals(expected, result);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (first 3 conditions false)
     */
    @Test
    public void testEquals9() {
        AdjacencyMatrixGraph ad1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        //Both numEdges and numVertices equal
        ad1.numEdges = 0;
        ad2.numEdges = 0;
        ad1.numVertices = 0;
        ad2.numVertices = 0;

        Address instance1 = new Address(-10.1500, -58.3772300, "rua", 23, "2381-10", "porto", 12);
        Address instance2 = new Address(-15.6131500, -58.3772300, "rua", 23, "2381-10", "porto", 12);
        Address instance3 = new Address(-20.6131500, -58.3772300, "rua", 23, "2381-10", "porto", 12);
        Address instance4 = new Address(-25.6131500, -58.3772300, "rua", 23, "2381-10", "porto", 12);

        ad1.insertVertex(instance1);
        ad2.insertVertex(instance2);
        ad1.insertEdge(instance1, instance2, 2.0);
        ad2.insertEdge(instance3, instance4, 3.0);

        boolean expected = false;
        boolean result = ad1.equals(ad2);

        assertEquals(expected, result);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (both adjacencyMatrix are equal)
     */
    @Test
    public void testEquals10() {
        AdjacencyMatrixGraph ad1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        //Both numEdges and numVertices equal
        ad1.numEdges = 0;
        ad2.numEdges = 0;
        ad1.numVertices = 0;
        ad2.numVertices = 0;

        Address instance1 = new Address(-10.1500, -58.3772300, "rua", 23, "2381-10", "porto", 12);
        Address instance2 = new Address(-10.1500, -58.3772300, "rua", 23, "2381-10", "porto", 12);

        ad1.insertVertex(instance1);
        ad2.insertVertex(instance2);
        ad1.insertEdge(instance1, instance2, 2.0);
        ad2.insertEdge(instance1, instance2, 2.0);

        boolean expected = true;
        boolean result = ad1.equals(ad2);

        assertEquals(expected, result);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (objects of different types)
     */
    @Test
    public void testEquals11() {
        Address address = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        //Both numEdges and numVertices equal
        ad2.numEdges = 0;
        ad2.numVertices = 0;

        Address instance1 = new Address(-10.1500, -58.3772300, "rua", 23, "2381-10", "porto", 12);
        Address instance2 = new Address(-10.1500, -58.3772300, "rua", 23, "2381-10", "porto", 12);

        ad2.insertVertex(instance2);
        ad2.insertEdge(instance1, instance2, 2.0);

        boolean expected = false;
        boolean result = ad2.equals(address);

        assertEquals(expected, result);
    }


    /**
     * Test of hashCode method, of class AdjacencyMatrixGraph.
     */
    @Test
    public void testHashCode() {
        AdjacencyMatrixGraph ad1 = new AdjacencyMatrixGraph();
        AdjacencyMatrixGraph ad2 = new AdjacencyMatrixGraph();
        Map<AdjacencyMatrixGraph, String> map = new HashMap<>();
        map.put(ad1, "dummy");
        Assert.assertEquals(null, map.get(ad2));
    }

    @Test
    public void testRemoveEdge() {
        Address vertexA = new Address(1, 231.321, "ponto1", 2, "242-02", "Porto");
        Address vertexB = null;
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        Double expResult = null;
        Object result = instance.removeEdge(vertexA, vertexB);
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveEdge2() {
        Address vertexA = null;
        Address vertexB = new Address(1, 231.321, "ponto1", 2, "242-02", "Porto");
        AdjacencyMatrixGraph instance = new AdjacencyMatrixGraph();
        Double expResult = null;
        Object result = instance.removeEdge(vertexA, vertexB);
        assertEquals(expResult, result);
    }

}