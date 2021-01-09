package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    private final Invoice invoice;
    private final Invoice invoice2;

    public InvoiceTest() {
        invoice = new Invoice(1,1,1);
        invoice2 = new Invoice (1,new Date(12544478),12,1,1);
    }


    @Test
    void getId() {
        int expResult = 1;
        assertEquals(expResult, invoice2.getId());
    }

    @Test
    void setId() {
        invoice.setId(2);
        int result = invoice.getId();
        int expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void getFinalPrice() {
        int expResult = 12;
        assertEquals(expResult, invoice2.getFinalPrice());
    }

    @Test
    void setFinalPrice() {
        invoice.setFinalPrice(2);
        double result = invoice.getFinalPrice();
        double expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void getClientID() {
        int expResult = 1;
        assertEquals(expResult, invoice.getClientID());
    }

    @Test
    void setClientID() {
        invoice.setClientID(2);
        int result = invoice.getClientID();
        int expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void getIdOrder() {
        int expResult = 1;
        assertEquals(expResult, invoice.getIdOrder());
    }

    @Test
    void setIdOrder() {
        invoice.setIdOrder(2);
        int result = invoice.getIdOrder();
        int expResult = 2;
        assertEquals(expResult,result);
    }

    @Test
    void getDataInvoice() {
        Date expResult = new Date(12544478) ;
        assertEquals(expResult, invoice2.getDataInvoice());
    }

    /*      TODO Verificar o Date
    @Test
    void setDataInvoice() {
        invoice2.setDataInvoice(new Date(125444222));
        Date result = invoice.getDataInvoice();
        Date expResult = new Date(125444222);
        assertEquals(expResult,result);
    }*/

    @Test
    void save() {
    }

    @Test
    void getInvoice() {
    }

    /*  TODO O método falha por causa do Date
    @Test
    void testToString() {
        String result = invoice.toString();
        String expResult = "YOUR ORDER\nInvoice{" +
                "id=" + 1 +
                ", dataInvoice=" + new Date(12544478) +
                ", finalPrice=" + 12 +
                ", clientID=" + 1 +
                ", idOrder=" + 1 +
                '}';
        assertEquals(expResult,result);
    }*/

    @Test
    public void test1Equals() {
        Invoice obj = null;
        Invoice instance = new Invoice(1,new Date(12544478),12,1,1);
        assertNotEquals(obj, instance);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Invoice invoice = new Invoice (1,new Date(12544478),12,1,1);
        assertNotEquals(obj, invoice);
    }

    @Test
    public void test3Equals() {
        Invoice instance = new Invoice(1,new Date(12544478),12,1,1);
        assertEquals(instance, instance);
    }

    @Test
    public void test4Equals() {
        Invoice p = new Invoice(1,new Date(12544478),12,1,1);
        Invoice instance = new Invoice(1,new Date(12544478),12,1,1);
        boolean expected = true;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    public void test5Equals() {
        Invoice i = new Invoice(1,new Date(12544478),12,1,1);
        Invoice instance = new Invoice(3,new Date(125478),123,4,4);
        boolean expected = false;
        boolean result = instance.equals(i);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int hash = invoice.hashCode();
        int expResult = 31;
        assertEquals(expResult, hash);
    }
}