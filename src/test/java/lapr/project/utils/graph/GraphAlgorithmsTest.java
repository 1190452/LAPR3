package lapr.project.utils.graph;

import lapr.project.model.Address;
import lapr.project.model.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgorithmsTest {

    @Test
    void transitiveClosure() {
        AdjacencyMatrixGraph completeMap2 = new AdjacencyMatrixGraph();

        Client c1 = new Client(1, "teste1", "teste1@gmail.com", "qw", 123456789, 2324.816, 27125.9881, new BigDecimal("1234567891057189"));
        Client c2 = new Client(2, "teste2", "teste2@gmail.com", "qw", 123456789, 2834.816, 21715.9881, new BigDecimal("1234567891057189"));
        Client c3 = new Client(3, "teste3", "teste3@gmail.com", "qw", 123456789, 2314.816, 3715.9881, new BigDecimal("1234567891057189"));
        Client c4 = new Client(4, "teste4", "teste4@gmail.com", "qw", 123456789, 234.8916, 4715.9881, new BigDecimal("1234567891057189"));


        completeMap2.insertVertex(c1);
        completeMap2.insertVertex(c2);
        completeMap2.insertVertex(c3);
        completeMap2.insertVertex(c4);

        completeMap2.insertEdge(c1, c2, 2.2);
        completeMap2.insertEdge(c2, c1, 2.2);
        completeMap2.insertEdge(c2, c4, 9.3);
        completeMap2.insertEdge(c4, c2, 9.3);
        completeMap2.insertEdge(c2, c3, 2.3);
        completeMap2.insertEdge(c3, c2, 2.3);
        completeMap2.insertEdge(c3, c4, 10.3);
        completeMap2.insertEdge(c4, c3, 10.3);

        GraphAlgorithms.transitiveClosure(completeMap2, null);

        boolean teste = true;
        int i = 0, j = 0;
        while (i < completeMap2.numVertices && teste == true) {
            while (j < completeMap2.numVertices && teste == true) {
                if (i != j) {
                    if (completeMap2.getEdge(completeMap2.vertices.get(i), completeMap2.vertices.get(j)) == null) {
                        teste = false;
                    }
                }
                j++;
            }
            i++;
        }

        System.out.println("Teste: transitiveClosure");
        System.out.println(completeMap2);

        assertTrue(teste);
    }
}