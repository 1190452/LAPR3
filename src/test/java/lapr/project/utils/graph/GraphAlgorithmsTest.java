package lapr.project.utils.graph;

import lapr.project.model.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphAlgorithmsTest {

    @Test
    void transitiveClosure() {
        AdjacencyMatrixGraph completeMap2 = new AdjacencyMatrixGraph();

        Address c1 = new Address(34, 45,"rua xpto", 2, "4500", "espinho");
        Address c2 = new Address(67, 45,"rua xpto", 2, "4500", "espinho");
        Address c3 = new Address(64347, 425,"rua xpto", 2, "4500", "espinho");
        Address c4 = new Address(2342, 453,"rua xpto", 2, "4500", "espinho");


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
        int k=0, i = 0, j = 0;

        while (k < completeMap2.numVertices && teste == true){
        while (i < completeMap2.numVertices && teste == true) {
            if (completeMap2.getEdge(completeMap2.vertices.get(i), completeMap2.vertices.get(k)) != null && i != k)
                while (j < completeMap2.numVertices && teste == true) {
                    if ( completeMap2.getEdge(completeMap2.vertices.get(k), completeMap2.vertices.get(j)) != null && i != j && k !=j) {
                        if (completeMap2.getEdge(completeMap2.vertices.get(i), completeMap2.vertices.get(j)) == null) {
                            teste = false;
                        }
                    }
                    j++;
                }
            i++;
        }
        k++;
        }

        System.out.println("Teste: transitiveClosure");
        System.out.println(completeMap2);

        assertTrue(teste);
    }
}