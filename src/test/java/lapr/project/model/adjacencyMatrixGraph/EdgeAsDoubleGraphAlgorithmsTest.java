package lapr.project.model.adjacencyMatrixGraph;

import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.LinkedList;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assert.*;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

public class EdgeAsDoubleGraphAlgorithmsTest {

    AdjacencyMatrixGraph <String, Double> distanceMap = new AdjacencyMatrixGraph<>();

    public EdgeAsDoubleGraphAlgorithmsTest() {
    }

    @Before
    public void setUp() throws Exception {
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
    void shortestPath() {
    }
}