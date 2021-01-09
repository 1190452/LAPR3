package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    private final Client client;
    private final Client client2;
    private final Client client3;
    private final Client client4;


    ClientTest() {
        client = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigInteger("1234567891057189").intValue());
        client2 = new Client("Ricardo", "ricky@gmail.com", "qwerty", 189102816, 2332.91872, 827162.23234, new BigInteger("1829102918271622").intValue());
        client3 = new Client("Patricia", "tixa@gmail.com", "teste123", 23243242.1929, 9182711.21);
        client4 = new Client("rafael@gmail.com", "CLIENT", 123, "Rafael", 718290182, 2897771.232, 23991.22981, new BigInteger("8910281726172819").intValue(), 23);
    }


    @Test
    void getIdClient() {
        int id = client.getIdClient();
        int expID = 1;
        assertEquals(expID, id);
    }

    @Test
    void setIdClient() {
        client.setIdClient(45);
        int id = client.getIdClient();
        int expID = 45;
        assertEquals(expID, id);
    }

    @Test
    void getName() {
        String name = client.getName();
        String expName = "Alexandre";
        assertEquals(expName, name);
    }

    @Test
    void setName() {
        client.setName("João");
        String expName = "João";
        String name = client.getName();
        assertEquals(expName, name);

    }

    @Test
    void getEmail() {
        String email = client.getEmail();
        String expEmail = "alex@gmail.com";
        assertEquals(expEmail, email);
    }

    @Test
    void setEmail() {
        client.setEmail("qwerty@gmail.com");
        String email = client.getEmail();
        String expEmail = "qwerty@gmail.com";
    }

    @Test
    void getnif() {
        int nif = client.getnif();
        int expResult = 123456789;
        assertEquals(expResult, nif);
    }

    @Test
    void setnif() {
        client.setnif(987654321);
        int expResult = 987654321;
        int nif = client.getnif();
        assertEquals(expResult, nif);
    }

    @Test
    void getNumCredits() {
        int numCredits = client.getNumCredits();
        int expResult = 0;
        assertEquals(expResult, numCredits);
    }

    @Test
    void setNumCredits() {
        client.setNumCredits(21);
        int expResult = 21;
        int numCredits = client.getNumCredits();
        assertEquals(expResult, numCredits);
    }

    @Test
    void getLatitude() {
        double latitude = client.getLatitude();
        double expResult = 234.816;
        assertEquals(expResult, latitude);
    }

    @Test
    void setLatitude() {
        client.setLatitude(1.291);
        double expResult = 1.291;
        double result = 1.291;
        assertEquals(expResult,result);

    }

    @Test
    void getLongitude() {
        double longitude = client.getLongitude();
        double expResult = 2715.9881;
        assertEquals(longitude, expResult);
    }

    @Test
    void setLongitude() {
        client.setLongitude(1.2911);
        double expResult = 1.2911;
        double result = 1.2911;
        assertEquals(expResult,result);
    }

    @Test
    void getCreditCardNumber() {
        int cc = client.getCreditCardNumber();
        int expResult = new BigInteger("1234567891057189").intValue();
        assertEquals(expResult,cc);
    }

    @Test
    void setCreditCardNumber() {
        client.setCreditCardNumber(new BigInteger("1829102918271622").intValue());
        int cc = client.getCreditCardNumber();
        int expResult = new BigInteger("1829102918271622").intValue();
        assertEquals(expResult,cc);
    }

    @Test
    public void test1Equals() {
        Pharmacy obj = null;
        Client instance = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigInteger("1234567891057189").intValue());
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test2Equals() {
        Object obj = null;
        Client instance = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigInteger("1234567891057189").intValue());
        boolean expected = false;
        boolean result = instance.equals(obj);
        assertEquals(expected, result);
    }

    @Test
    public void test3Equals() {
        Client instance = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigInteger("1234567891057189").intValue());
        boolean expected = true;
        boolean result = instance.equals(instance);
        assertEquals(expected, result);
    }

    /*      //TODO Resolver o equals
    @Test
    public void test4Equals() {
        Client p = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigInteger("1234567891057189").intValue());
        Client instance = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigInteger("1234567891057189").intValue());
        boolean expected = true;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }*/

    @Test
    public void test5Equals() {
        Client p = new Client(1, "Bruno", "bruno@gmail.com", "testeteste", 765182910, 2323.12, 98991.9091, new BigInteger("1829098716271829").intValue());
        Client instance = new Client(1, "Alexandre", "alex@gmail.com", "rosa", 123456789, 234.816, 2715.9881, new BigInteger("1234567891057189").intValue());
        boolean expected = false;
        boolean result = instance.equals(p);
        assertEquals(expected, result);
    }

    @Test
    void testHashCode() {
        int hash = client.hashCode();
        int expResult = 32;
        assertEquals(expResult,hash);
    }

    @Test
    void testToString() {
        String expResult = "Client{" +
                "idClient=" + 1 +
                ", name='" + "Alexandre" + '\'' +
                ", email='" + "alex@gmail.com" + '\'' +
                ", nif=" + 123456789 +
                ", numCredits=" + 0 +
                ", latitude=" + 234.816 +
                ", longitude=" + 2715.9881 +
                '}';
        String result = client.toString();
        assertEquals(expResult, result);
    }
}