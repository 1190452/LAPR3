package lapr.project.utils.graph;

import lapr.project.utils.graphbase.Edge;
import lapr.project.utils.graphbase.Graph;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    Graph<String, String> instance = new Graph<>(true) ;

    public GraphTest() {
    }

    @Before
    public void setUp() {

    }

    /**
     * Test of numVertices method, of class Graph.
     */
    @Test
    public void testNumVertices() {
        System.out.println("Test numVertices");

        assertTrue((instance.numVertices()==0), "result should be zero");

        instance.insertVertex("A");
        assertTrue((instance.numVertices()==1), "result should be one");

        instance.insertVertex("B");
        assertTrue((instance.numVertices()==2), "result should be two");

        instance.removeVertex("A");
        assertTrue((instance.numVertices() == 1), "result should be one");

        instance.removeVertex("B");
        assertTrue((instance.numVertices() == 0), "result should be zero");
    }

    /**
     * Test of vertices method, of class Graph.
     */
    @Test
    public void testVertices() {
        System.out.println("Test vertices");

        Iterator<String> itVerts = instance.vertices().iterator();

        assertFalse(itVerts.hasNext(), "vertices should be empty");

        instance.insertVertex("A");
        instance.insertVertex("B");

        itVerts = instance.vertices().iterator();

        assertTrue((itVerts.next().compareTo("A") == 0), "first vertice should be A");
        assertTrue((itVerts.next().compareTo("B")==0), "second vertice should be B");

        instance.removeVertex("A");

        itVerts = instance.vertices().iterator();
        assertEquals((itVerts.next().compareTo("B")), 0, "first vertice should now be B");

        instance.removeVertex("B");

        itVerts = instance.vertices().iterator();
        assertFalse(itVerts.hasNext(), "vertices should now be empty");
    }

    /**
     * Test of numEdges method, of class Graph.
     */
    @Test
    public void testNumEdges() {
        System.out.println("Test numEdges");

        assertTrue((instance.numEdges() == 0), "result should be zero");

        instance.insertEdge("A","B","Edge1",6);
        assertTrue((instance.numEdges() == 1), "result should be one");

        instance.insertEdge("A","C","Edge2",1);
        assertTrue((instance.numEdges()==2), "result should be two");

        instance.removeEdge("A","B");
        assertTrue((instance.numEdges()==1), "result should be one");

        instance.removeEdge("A","C");
        assertTrue((instance.numEdges()==0), "result should be zero");
    }

    /**
     * Test of edges method, of class Graph.
     */
    @Test
    public void testEdges() {
        System.out.println("Test Edges");

        Iterator<Edge<String,String>> itEdge = instance.edges().iterator();

        assertTrue((!itEdge.hasNext()), "edges should be empty");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        itEdge = instance.edges().iterator();

        itEdge.next(); itEdge.next();
        assertEquals(itEdge.next().getElement(), "Edge3", "third edge should be B-D");

        itEdge.next(); itEdge.next();
        assertEquals(itEdge.next().getElement(), "Edge6", "sixth edge should be D-A");

        instance.removeEdge("A","B");

        itEdge = instance.edges().iterator();
        assertEquals(itEdge.next().getElement(), "Edge2", "first edge should now be A-C");

        instance.removeEdge("A","C"); instance.removeEdge("B","D");
        instance.removeEdge("C","D"); instance.removeEdge("C","E");
        instance.removeEdge("D","A"); instance.removeEdge("E","D");
        instance.removeEdge("E","E");
        itEdge = instance.edges().iterator();
        assertTrue((!itEdge.hasNext()), "edges should now be empty");
    }


    /**
     * Test of getEdge method, of class Graph.
     */
    @Test
    public void testGetEdge() {
        System.out.println("Test getEdge");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        assertNull(instance.getEdge("A", "E"), "edge should be null");

        assertEquals(instance.getEdge("B", "D").getElement(), "Edge3", "edge between B-D");
        assertNull(instance.getEdge("D", "B"), "edge should be null");

        instance.removeEdge("D","A");
        assertNull(instance.getEdge("D", "A"), "edge should be null");

        assertEquals(instance.getEdge("E", "E").getElement(), "Edge8", "edge should be edge8");
    }

    /**
     * Test of endVertices method, of class Graph.
     */
    @Test
    public void testEndVerticesStandard() {
        System.out.println("Test endVertices");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        //assertTrue("endVertices should be null", instance.endVertices(edge0)==null);

        Edge<String,String> edge1 = instance.getEdge("A","B");
        //vertices = instance.endVertices(edge1);
        assertEquals(instance.endVertices(edge1)[0], "A", "first vertex should be A");
        assertEquals(instance.endVertices(edge1)[1], "B", "second vertex should be B");
    }

    /**
     * Test of endVertices method, of class Graph.
     */
    @Test
    public void testEndVertices() {
        System.out.println("Test endVertices");

        instance.insertEdge("A","B","Edge1",1);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","C","Edge3",1);

        Edge<String,String> edgeTest = instance.getEdge("NON_EXISTENT_1","NON_EXISTENT_2");
        Object[] expResult = instance.endVertices(edgeTest);
        assertArrayEquals(expResult, null);

        Edge<String,String> edgeTest2 = instance.getEdge("A","NON_EXISTENT");
        Object[] expResult2 = instance.endVertices(edgeTest2);
        assertArrayEquals(expResult2, null);

        Edge<String,String> edgeTest3 = instance.getEdge("NON_EXISTENT","B");
        Object[] expResult3 = instance.endVertices(edgeTest3);
        assertArrayEquals(expResult3, null);
    }

    /**
     * Test of opposite method, of class Graph.
     */
    @Test
    public void testOpposite() {
        System.out.println("Test opposite");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Edge<String,String> edge5 = instance.getEdge("C","E");
        String vert = instance.opposite("A", edge5);
        assertNull(vert, "opposite should be null");

        Edge<String,String> edge1 = instance.getEdge("A","B");
        vert = instance.opposite("A", edge1);
        assertEquals(vert, "B", "opposite should be B");

        Edge<String,String> edge8 = instance.getEdge("E","E");
        vert = instance.opposite("E", edge8);
        assertEquals(vert, "E", "opposite should be E");
    }

    /**
     * Test of outDegree method, of class Graph.
     */
    @Test
    public void testOutDegree() {
        System.out.println("Test outDegree");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        int outdeg = instance.outDegree("G");
        assertEquals(-1, outdeg, "degree should be -1");

        outdeg = instance.outDegree("A");
        assertEquals(outdeg, 2, "degree should be 2");

        outdeg = instance.outDegree("B");
        assertEquals(outdeg, 1, "degree should be 1");

        outdeg = instance.outDegree("E");
        assertEquals(outdeg, 2, "degree should be 2");
    }

    /**
     * Test of inDegree method, of class Graph.
     */
    @Test
    public void testInDegree() {
        System.out.println("Test inDegree");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        int indeg = instance.inDegree("G");
        assertEquals(-1, indeg, "in degree should be -1");

        indeg = instance.inDegree("A");
        assertEquals(indeg, 1, "in degree should be 1");

        indeg = instance.inDegree("D");
        assertEquals(indeg, 3, "in degree should be 3");

        indeg = instance.inDegree("E");
        assertEquals(indeg, 2, "in degree should be 2");
    }

    /**
     * Test of outgoingEdges method, of class Graph.
     */
    @Test
    public void testOutgoingEdges() {
        System.out.println(" Test outgoingEdges");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Iterator<Edge<String,String>> itEdge = instance.outgoingEdges("C").iterator();
        Edge<String,String> first = itEdge.next();
        Edge<String,String> second = itEdge.next();
        assertTrue(( (first.getElement().equals("Edge4") && second.getElement().equals("Edge5")) ||
                                (first.getElement().equals("Edge5") && second.getElement().equals("Edge4")) ),
                "Outgoing Edges of vert C should be Edge4 and Edge5");

        instance.removeEdge("E","E");

        itEdge = instance.outgoingEdges("E").iterator();
        assertEquals(itEdge.next().getElement(), "Edge7", "first edge should be Edge7");

        instance.removeEdge("E","D");

        itEdge = instance.outgoingEdges("E").iterator();
        assertTrue((!itEdge.hasNext()), "edges should be empty");
    }

    /**
     * Test of outgoingEdges method, of class Graph.
     */
    @Test
    public void testOutgoingEdges2(){
        System.out.println(" Test outgoingEdges");

        instance.insertVertex("A");
        instance.insertVertex("B");

        ArrayList<Edge> expResult = null;
        Iterable<Edge<String, String>> result = instance.outgoingEdges("NON_EXISTENT");
        assertEquals(expResult, result);
    }

    /**
     * Test of incomingEdges method, of class Graph.
     */
    @Test
    public void testIncomingEdges2(){
        System.out.println(" Test incomingEdges");

        instance.insertVertex("A");
        instance.insertVertex("B");

        ArrayList<Edge> expResult = null;
        Iterable<Edge<String, String>> result = instance.incomingEdges("NON_EXISTENT");
        assertEquals(expResult, result);
    }
    /**
     * Test of incomingEdges method, of class Graph.
     */
    @Test
    public void testIncomingEdges() {

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Iterator<Edge<String,String>> itEdge = instance.incomingEdges("D").iterator();

        assertEquals(itEdge.next().getElement(), "Edge3", "first edge should be edge3");
        assertEquals(itEdge.next().getElement(), "Edge4", "second edge should be edge4");
        assertEquals(itEdge.next().getElement(), "Edge7", "third edge should be edge7");

        itEdge = instance.incomingEdges("E").iterator();

        assertEquals(itEdge.next().getElement(), "Edge5", "first edge should be Edge5");
        assertEquals(itEdge.next().getElement(), "Edge8", "second edge should be Edge8");

        instance.removeEdge("E","E");

        itEdge = instance.incomingEdges("E").iterator();

        assertEquals(itEdge.next().getElement(), "Edge5", "first edge should be Edge5");

        instance.removeEdge("C","E");

        itEdge = instance.incomingEdges("E").iterator();
        assertTrue((!itEdge.hasNext()), "edges should be empty");
    }

    /**
     * Test of insertVertex method, of class Graph.
     */
    @Test
    public void testInsertVertex() {
        System.out.println("Test insertVertex");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        Iterator <String> itVert = instance.vertices().iterator();

        assertEquals(itVert.next(), "A", "first vertex should be A");
        assertEquals(itVert.next(), "B", "second vertex should be B");
        assertEquals(itVert.next(), "C", "third vertex should be C");
        assertEquals(itVert.next(), "D", "fourth vertex should be D");
        assertEquals(itVert.next(), "E", "fifth vertex should be E");
    }

    /**
     * Test of insertVertex method, of class Graph.
     */
    @Test
    public void testInsertVertex2() {
        System.out.println("Test insertVertex");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");

        boolean expResult = false;
        boolean result = instance.insertVertex("A");
        //false because the vertex "A" already exists
        assertEquals(expResult, result);

        boolean expResult2 = true;
        boolean result2 = instance.insertVertex("NEW_VERTEX");
        assertEquals(expResult2, result2);
    }

    /**
     * Test of insertEdge method, of class Graph.
     */
    @Test
    public void testInsertEdge() {
        System.out.println("Test insertEdge");

        assertTrue((instance.numEdges()==0), "num. edges should be zero");

        instance.insertEdge("A","B","Edge1",6);
        assertTrue((instance.numEdges()==1), "num. edges should be 1");

        instance.insertEdge("A","C","Edge2",1);
        assertTrue((instance.numEdges()==2), "num. edges should be 2");

        instance.insertEdge("B","D","Edge3",3);
        assertTrue((instance.numEdges()==3), "num. edges should be 3");

        instance.insertEdge("C","D","Edge4",4);
        assertTrue((instance.numEdges()==4), "num. edges should be 4");

        instance.insertEdge("C","E","Edge5",1);
        assertTrue((instance.numEdges()==5), "num. edges should be 5");

        instance.insertEdge("D","A","Edge6",2);
        assertTrue((instance.numEdges()==6), "num. edges should be 6");

        instance.insertEdge("E","D","Edge7",1);
        assertTrue((instance.numEdges()==7), "num. edges should be 7");

        instance.insertEdge("E","E","Edge8",1);
        assertTrue((instance.numEdges()==8), "num. edges should be 8");

        Iterator <Edge<String,String>> itEd = instance.edges().iterator();

        itEd.next(); itEd.next();
        assertEquals(itEd.next().getElement(), "Edge3", "third edge should be Edge3");
        itEd.next(); itEd.next();
        assertEquals(itEd.next().getElement(), "Edge6", "sixth edge should be Edge6");
    }

    /**
     * Test of insertEdge method, of class Graph.
     */
    @Test
    public void testInsertEdge2() {
        System.out.println("Test insertEdge");

        instance.insertVertex("1");
        instance.insertVertex("2");
        instance.insertVertex("3");
        instance.insertVertex("4");
        instance.insertVertex("5");
        instance.insertVertex("6");
        instance.insertVertex("7");

        boolean expResult = true;
        //true because its a new edge
        boolean result = instance.insertEdge("1", "2", "edge_test1", 1);
        assertEquals(expResult, result);

        boolean expResult2 = false;
        //false because this edge already exists
        boolean result2 = instance.insertEdge("1", "2", "edge_test2", 1);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
    public void testRemoveVertex() {
        System.out.println("Test removeVertex");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.removeVertex("C");
        assertTrue((instance.numVertices()==4), "Num vertices should be 4");

        Iterator<String> itVert = instance.vertices().iterator();
        assertEquals(itVert.next(), "A", "first vertex should be A");
        assertEquals(itVert.next(), "B", "second vertex should be B");
        assertEquals(itVert.next(), "D", "third vertex should be D");
        assertEquals(itVert.next(), "E", "fourth vertex should be E");

        instance.removeVertex("A");
        assertTrue((instance.numVertices()==3), "Num vertices should be 3");

        itVert = instance.vertices().iterator();
        assertEquals(itVert.next(), "B", "first vertex should be B");
        assertEquals(itVert.next(), "D", "second vertex should be D");
        assertEquals(itVert.next(), "E", "third vertex should be E");

        instance.removeVertex("E");
        assertTrue((instance.numVertices()==2), "Num vertices should be 2");

        itVert = instance.vertices().iterator();

        assertEquals(itVert.next(), "B", "first vertex should be B");
        assertEquals(itVert.next(), "D", "second vertex should be D");

        instance.removeVertex("B"); instance.removeVertex("D");
        assertTrue((instance.numVertices()==0), "Num vertices should be 4");
    }

    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
    public void testRemoveVertex2() {
        System.out.println("Test removeVertex");
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        boolean expResult = false;
        boolean result = instance.removeVertex("This vertex doesn't exist");
        assertEquals(expResult, result);
        assertEquals(instance.numVertices(), 5);

        boolean expResult2 = true;
        boolean result2 = instance.removeVertex("D");
        assertEquals(expResult2, result2);
        assertEquals(instance.numVertices(), 4);
        assertEquals(instance.getKey("E"), 3);  //key was 4 but with removal of "D", its now 3

    }

    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdgeStandard() {
        System.out.println("Test removeEdge");

        assertTrue((instance.numEdges()==0), "num. edges should be zero");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        assertTrue((instance.numEdges()==8), "Num. edges should be 8");

        instance.removeEdge("E","E");
        assertTrue((instance.numEdges()==7), "Num. edges should be 7");

        Iterator <Edge<String,String>> itEd = instance.edges().iterator();

        itEd.next(); itEd.next();
        assertEquals(itEd.next().getElement(), "Edge3", "third edge should be Edge3");
        itEd.next(); itEd.next();
        assertEquals(itEd.next().getElement(), "Edge6", "sixth edge should be Edge6");

        instance.removeEdge("C","D");
        assertTrue((instance.numEdges()==6), "Num. edges should be 6");

        itEd = instance.edges().iterator();
        itEd.next(); itEd.next();
        assertEquals(itEd.next().getElement(), "Edge3", "third edge should be Edge3");
        assertEquals(itEd.next().getElement(), "Edge5", "fourth edge should be Edge5");
        assertEquals(itEd.next().getElement(), "Edge6", "fifth edge should be Edge6");
        assertEquals(itEd.next().getElement(), "Edge7", "...last edge should be Edge7");
    }

    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("Test removeEdge");
        instance.insertEdge("A","B","Edge1",1);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","C","Edge3",1);
        instance.insertEdge("C","D","Edge4",4);

        boolean expResult = false;
        boolean result = instance.removeEdge("A", "NON_EXISTENT");
        boolean expResult2 = false;
        boolean result2 = instance.removeEdge("NON_EXISTENT", "B");
        boolean expResult3 = false;
        boolean result3 = instance.removeEdge("NON_EXISTENT", "NON_EXISTENT");
        boolean expResult4 = true;
        boolean result4 = instance.removeEdge("C", "D");

        Edge<String, String> expResult5 = instance.getEdge("4", "6");
        //test for the if condition where null is returned if edge is null

        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertNull(expResult5);

    }



    /**
     * Test of validVertex method, of class Graph.
     */
    @Test
    public void testValidVertex() {
        System.out.println("validVertex");
        String vert = null;
        instance.insertVertex(vert);

        boolean expResult = false;
        boolean result = instance.validVertex("");
        assertEquals(expResult, result);
    }

    /**
     * Test of validVertex method, of class Graph.
     */
    @Test
    public void testValidVertex2() {
        System.out.println("validVertex2");
        String vert = "Hateful of the Horde";
        instance.insertVertex(vert);

        boolean expResult = true;
        boolean result = instance.validVertex(vert);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKey method, of class Graph.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        String v1 = "Lok'tar";
        String v2 = "Ogar";
        String v3 = "For the Horde!";
        instance.insertVertex(v1);
        instance.insertVertex(v2);
        instance.insertVertex("expectedKey: 2");
        instance.insertVertex("expectedKey: 3");
        instance.insertVertex("expectedKey: 4");
        instance.insertVertex("expectedKey: 5");
        instance.insertVertex("expectedKey: 6");
        instance.insertVertex("expectedKey: 7");
        instance.insertVertex("expectedKey: 8");
        instance.insertVertex(v3);

        int expResult = 0;
        int result = instance.getKey(v1);
        assertEquals(expResult, result);

        int expResult2 = 1;
        int result2 = instance.getKey(v2);
        assertEquals(expResult2, result2);

        int expResult3 = 9;
        int result3 = instance.getKey(v3);
        assertEquals(expResult3, result3);
    }

    /**
     * Test of allkeyVerts method, of class Graph.
     */
    @Test
    public void testAllkeyVerts() {
        System.out.println("allkeyVerts");

        String[] expResult = (String[]) Array.newInstance(String.class, 3);
        expResult[0] = "2012";
        expResult[1] = "Mito";
        expResult[2] = "THIRD_VERTEX";

        instance.insertVertex("2012");
        instance.insertVertex("Mito");
        instance.insertVertex("THIRD_VERTEX");

        instance.insertEdge("2012", "Mito", ".", 1);
        instance.insertEdge("2012", "THIRD_VERTEX", ".", 1);
        instance.insertEdge("Mito", "THIRD_VERTEX", ".", 1);

        String[] result = instance.allkeyVerts();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of adjVertices method, of class Graph. (vertex non existent)
     */
    @Test
    public void testAdjVertices() {
        System.out.println("adjVertices");
        Iterable expResult = null;
        Iterable result = instance.adjVertices("Vertex_Not_Found");
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals() {
        System.out.println("Test Equals");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        assertNotEquals(instance, null);

        assertEquals(instance, instance);

        assertEquals(instance.clone(), instance);

        Graph<String, String> other = instance.clone();

        other.removeEdge("E", "E");
        assertNotEquals(other, instance);

        other.insertEdge("E", "E", "Edge8", 1);
        assertEquals(other, instance);

        other.removeVertex("D");
        assertNotEquals(other, instance);

    }

    /**
     * Test of toString method, of class Graph.
     */
    @Test
    public void testClone() {
        System.out.println("Test Clone");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        Graph<String, String> instClone = instance.clone();

        assertTrue(instance.numVertices() == instClone.numVertices());
        assertTrue(instance.numEdges() == instClone.numEdges());

        //vertices should be equal
        Iterator<String> itvertClone = instClone.vertices().iterator();
        Iterator<String> itvertSource = instance.vertices().iterator();
        while (itvertSource.hasNext())
            assertTrue((itvertSource.next().equals(itvertClone.next()) == true));
    }

}