package lapr.project.utils.graph;

import lapr.project.utils.graphbase.Graph;
import lapr.project.utils.graphbase.GraphAlgorithms;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphAlgorithmsTest {
    Graph<String,String> completeMap = new Graph<>(false);

    public GraphAlgorithmsTest() {

        completeMap.insertVertex("Porto");
        completeMap.insertVertex("Braga");
        completeMap.insertVertex("Vila Real");
        completeMap.insertVertex("Aveiro");
        completeMap.insertVertex("Coimbra");
        completeMap.insertVertex("Leiria");

        completeMap.insertVertex("Viseu");
        completeMap.insertVertex("Guarda");
        completeMap.insertVertex("Castelo Branco");
        completeMap.insertVertex("Lisboa");
        completeMap.insertVertex("Faro");

        completeMap.insertEdge("Porto","Aveiro","A1",75);
        completeMap.insertEdge("Porto","Braga","A3",60);
        completeMap.insertEdge("Porto","Vila Real","A4",100);
        completeMap.insertEdge("Viseu","Guarda","A25",75);
        completeMap.insertEdge("Guarda","Castelo Branco","A23",100);
        completeMap.insertEdge("Aveiro","Coimbra","A1",60);
        completeMap.insertEdge("Coimbra","Lisboa","A1",200);
        completeMap.insertEdge("Coimbra","Leiria","A34",80);
        completeMap.insertEdge("Aveiro","Leiria","A17",120);
        completeMap.insertEdge("Leiria","Lisboa","A8",150);

        completeMap.insertEdge("Aveiro","Viseu","A25",85);
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
        completeMap.insertEdge("Lisboa","Faro","A2",280);

    }

    /**
     * Test of shortestPath method, of class GraphAlgorithmsBB.
     */
    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");

        LinkedList<String> shortPath = new LinkedList<>();
        double lenpath;
        lenpath= GraphAlgorithms.shortestPath(completeMap,"Porto","LX",shortPath);
        assertEquals(lenpath, 0, "Length path should be 0 if vertex does not exist");

        GraphAlgorithms.shortestPath(completeMap, "Porto", "Porto", shortPath);
        assertEquals(shortPath.size(), 1, "Number of nodes should be 1 if source and vertex are the same");

        lenpath= GraphAlgorithms.shortestPath(completeMap,"Porto","Lisboa",shortPath);
        assertEquals(lenpath, 335, "Path between Porto and Lisboa should be 335 Km");

        Iterator<String> it = shortPath.iterator();

        assertEquals(it.next().compareTo("Porto"), 0, "First in path should be Porto");
        assertEquals(it.next().compareTo("Aveiro"), 0, "then Aveiro");
        assertEquals(it.next().compareTo("Coimbra"), 0, "then Coimbra");
        assertEquals(it.next().compareTo("Lisboa"), 0, "then Lisboa");

        lenpath= GraphAlgorithms.shortestPath(completeMap,"Braga","Leiria",shortPath);
        assertEquals(lenpath, 255, "Path between Braga and Leiria should be 255 Km");

        it = shortPath.iterator();

        assertEquals(it.next().compareTo("Braga"), 0, "First in path should be Braga");
        assertEquals(it.next().compareTo("Porto"), 0, "then Porto");
        assertEquals(it.next().compareTo("Aveiro"), 0, "then Aveiro");
        assertEquals(it.next().compareTo("Leiria"), 0, "then Leiria");

        shortPath.clear();
        lenpath= GraphAlgorithms.shortestPath(completeMap,"Porto","Castelo Branco",shortPath);
        assertEquals(lenpath, 335, "Path between Porto and Castelo Branco should be 335 Km");
        assertEquals(shortPath.size(), 5, "N. cities between Porto and Castelo Branco should be 5 ");

        it = shortPath.iterator();

        assertEquals(it.next().compareTo("Porto"), 0, "First in path should be Porto");
        assertEquals(it.next().compareTo("Aveiro"), 0, "then Aveiro");
        assertEquals(it.next().compareTo("Viseu"), 0, "then Viseu");
        assertEquals(it.next().compareTo("Guarda"), 0, "then Guarda");
        assertEquals(it.next().compareTo("Castelo Branco"), 0, "then Castelo Branco");

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco

        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
        shortPath.clear();
        lenpath= GraphAlgorithms.shortestPath(completeMap,"Porto","Castelo Branco",shortPath);
        assertEquals(lenpath, 365, "Path between Porto and Castelo Branco should now be 365 Km");
        assertEquals(shortPath.size(), 4, "Path between Porto and Castelo Branco should be 4 cities");

        it = shortPath.iterator();

        assertEquals(it.next().compareTo("Porto"), 0, "First in path should be Porto");
        assertEquals(it.next().compareTo("Aveiro"), 0, "then Aveiro");
        assertEquals(it.next().compareTo("Leiria"), 0, "then Leiria");
        assertEquals(it.next().compareTo("Castelo Branco"), 0, "then Castelo Branco");
    }


    /**
     * Test of shortestPaths method, of class GraphAlgorithmsBB.
     */
    @Test
    public void testShortestPaths() {
        System.out.println("Test of shortest paths");

        ArrayList<LinkedList<String>> paths = new ArrayList<>();
        ArrayList<Double> dists = new ArrayList<>();

        GraphAlgorithms.shortestPaths(completeMap, "Porto", paths, dists);

        assertEquals(paths.size(), dists.size());
        assertEquals(completeMap.numVertices(), paths.size());
        assertEquals(1, paths.get(completeMap.getKey("Porto")).size());
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.getKey("Lisboa")));
        assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"), paths.get(completeMap.getKey("Castelo Branco")));
        assertEquals(335, dists.get(completeMap.getKey("Castelo Branco")), 0.01);

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria", "Castelo Branco", "A23", 170);
        GraphAlgorithms.shortestPaths(completeMap, "Porto", paths, dists);
        assertEquals(365, dists.get(completeMap.getKey("Castelo Branco")), 0.01);
        assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"), paths.get(completeMap.getKey("Castelo Branco")));


        GraphAlgorithms.shortestPaths(completeMap, "Porto", paths, dists);
        assertEquals(615.0, dists.get(completeMap.getKey("Faro")), 0.01);
        assertEquals(335, dists.get(completeMap.getKey("Lisboa")), 0.01);
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.getKey("Lisboa")));
        assertEquals(335, dists.get(completeMap.getKey("Lisboa")), 0.01);

        GraphAlgorithms.shortestPaths(completeMap, "Braga", paths, dists);
        assertEquals(255, dists.get(completeMap.getKey("Leiria")), 0.01);
        assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), paths.get(completeMap.getKey("Leiria")));
    }

    @Test
    public void testShortestPathsFirstCondition(){
        completeMap.insertVertex("R");
        completeMap.insertVertex("C");
        completeMap.insertVertex("10");

        ArrayList<LinkedList<String>> listTest = new ArrayList<>();
        ArrayList<Double> distsTest = new ArrayList<>();

        boolean expResult = false;
        boolean result = GraphAlgorithms.shortestPaths(completeMap, "SHOULD_BE_FALSE1", listTest, distsTest);
        boolean result2 = GraphAlgorithms.shortestPaths(completeMap, "SHOULD_BE_FALSE2", listTest, distsTest);
        boolean result3 = GraphAlgorithms.shortestPaths(completeMap, "SHOULD_BE_FALSE3", listTest, distsTest);
        boolean result4 = GraphAlgorithms.shortestPaths(completeMap, "SHOULD_BE_FALSE4", listTest, distsTest);
        assertEquals(expResult, result);
        assertEquals(expResult, result2);
        assertEquals(expResult, result3);
        assertEquals(expResult, result4);
    }

}