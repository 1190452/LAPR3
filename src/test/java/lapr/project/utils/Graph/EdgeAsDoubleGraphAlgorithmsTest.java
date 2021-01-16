package lapr.project.utils.graph;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class EdgeAsDoubleGraphAlgorithmsTest {

    AdjacencyMatrixGraph <String, Double> distanceMap = new AdjacencyMatrixGraph<>();

    public EdgeAsDoubleGraphAlgorithmsTest() {
    }

    @BeforeEach
    void setUp() {
        distanceMap.insertVertex("Porto");
        distanceMap.insertVertex("Braga");
        distanceMap.insertVertex("Vila Real");
        distanceMap.insertVertex("Aveiro");
        distanceMap.insertVertex("Coimbra");
        distanceMap.insertVertex("Leiria");

        distanceMap.insertVertex("Viseu");
        distanceMap.insertVertex("Guarda");
        distanceMap.insertVertex("Castelo Branco");
        distanceMap.insertVertex("Lisboa");
        distanceMap.insertVertex("Faro");
        distanceMap.insertVertex("Évora");


        distanceMap.insertEdge("Porto", "Aveiro", 75.0);
        distanceMap.insertEdge("Porto", "Braga", 60.0);
        distanceMap.insertEdge("Porto", "Vila Real", 100.0);
        distanceMap.insertEdge("Viseu", "Guarda", 75.0);
        distanceMap.insertEdge("Guarda",  "Castelo Branco", 100.0);
        distanceMap.insertEdge("Aveiro", "Coimbra", 60.0);
        distanceMap.insertEdge("Coimbra", "Lisboa", 200.0);
        distanceMap.insertEdge("Coimbra",  "Leiria",  80.0);
        distanceMap.insertEdge("Aveiro", "Leiria", 120.0);
        distanceMap.insertEdge("Leiria", "Lisboa", 150.0);


        distanceMap.insertEdge("Aveiro", "Viseu", 85.0);
        distanceMap.insertEdge("Leiria", "Castelo Branco", 170.0);
        distanceMap.insertEdge("Lisboa", "Faro", 280.0);
    }


    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");

        LinkedList<String> path = new LinkedList<>();

        assertEquals(-1, EdgeAsDoubleGraphAlgorithms.shortestPath(distanceMap, "Porto", "LX", path), "Should be -1 if vertex does not exist");

        assertEquals(-1, EdgeAsDoubleGraphAlgorithms.shortestPath(distanceMap, "Porto", "Évora", path), "Should be -1 if there is no path");

        assertEquals(EdgeAsDoubleGraphAlgorithms.shortestPath(distanceMap, "Porto", "Porto", path), 0, "Should be 0 if source and vertex are the same");

        assertEquals(path.size(), 1, "Path should be single vertex if source and vertex are the same");

        assertEquals(EdgeAsDoubleGraphAlgorithms.shortestPath(distanceMap, "Porto", "Lisboa", path), 335, "Path between Porto and Lisboa should be 335 Km");

        Iterator<String> it = path.iterator();

        assertEquals(it.next().compareTo("Porto"), 0, "First in path should be Porto");
        assertEquals(it.next().compareTo("Aveiro"), 0, "then Aveiro");
        assertEquals(it.next().compareTo("Coimbra"), 0, "then Coimbra");
        assertEquals(it.next().compareTo("Lisboa"), 0, "then Lisboa");

        assertEquals(EdgeAsDoubleGraphAlgorithms.shortestPath(distanceMap, "Braga", "Leiria", path), 255, "Path between Braga and Leiria should be 255 Km");

        it = path.iterator();

        assertEquals(it.next().compareTo("Braga"), 0, "First in path should be Braga");
        assertEquals(it.next().compareTo("Porto"), 0, "then Porto");
        assertEquals(it.next().compareTo("Aveiro"), 0, "then Aveiro");
        assertEquals(it.next().compareTo("Leiria"), 0, "then Leiria");

        assertEquals(EdgeAsDoubleGraphAlgorithms.shortestPath(distanceMap, "Porto", "Castelo Branco", path), 335, "Path between Porto and Castelo Branco should be 335 Km");
        assertEquals(path.size(), 5, "Path between Porto and Castelo Branco should be 5 cities");

        it = path.iterator();

        assertEquals(it.next().compareTo("Porto"), 0, "First in path should be Porto");
        assertEquals(it.next().compareTo("Aveiro"), 0, "then Aveiro");
        assertEquals(it.next().compareTo("Viseu"), 0, "then Viseu");
        assertEquals(it.next().compareTo("Guarda"), 0, "then Guarda");
        assertEquals(it.next().compareTo("Castelo Branco"), 0, "then Castelo Branco");

        // Changing Viseu to Guarda should change shortest path between Porto and Castelo Branco

        distanceMap.removeEdge("Viseu", "Guarda");
        distanceMap.insertEdge("Viseu", "Guarda", 125.0);

        assertEquals(EdgeAsDoubleGraphAlgorithms.shortestPath(distanceMap, "Porto", "Castelo Branco", path), 365, "Path between Porto and Castelo Branco should now be 365 Km");
        assertEquals(path.size(), 4, "Path between Porto and Castelo Branco should be 4 cities");

        it = path.iterator();

        assertEquals(it.next().compareTo("Porto"), 0, "First in path should be Porto");
        assertEquals(it.next().compareTo("Aveiro"), 0, "then Aveiro");
        assertEquals(it.next().compareTo("Leiria"), 0, "then Leiria");
        assertEquals(it.next().compareTo("Castelo Branco"), 0, "then Castelo Branco");
    }

}