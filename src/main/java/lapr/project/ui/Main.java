package lapr.project.ui;

import lapr.project.data.assessment.Facade;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author Nuno Bettencourt <nmb@isep.ipp.pt> on 24/05/16.
 */
class Main {

    /**
     * Private constructor to hide implicit public one.
     */
    private Main() {
    }

    /**
     * Application main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException {

        //load database properties

        try {
            Properties properties =
                    new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Facade fc=new Facade();

        //fc.addClients("ClientsFinal.csv"); JA ADICIONOU
        //fc.addPharmacy("PharmaciesFinal.csv"); JA ADICIONOU
       // fc.addMedicine("Products.csv");
        //fc.addCourier("Couriers.csv");
        //fc.addVehicle("Vehicles.csv");
        //fc.addPath("pathsRestricoesTerrestres.csv"); JA ADICIONOU
        //fc.addPath("pathsRestricoesAereas.csv"); JA ADICIONOU

        LoginUI login = new LoginUI();
        login.loginInterface();

    }
}

