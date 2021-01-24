package lapr.project.data.assessment;

import lapr.project.controller.PharmacyController;
import lapr.project.controller.UserController;
import lapr.project.data.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Facade {


    public Facade(){

    }

    public boolean addClients(String s){
        boolean added=false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        UserController uc = new UserController(new UserDataHandler(), new CourierDataHandler(), new ClientDataHandler(), new AddressDataHandler(), new CreditCardDataHandler());
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {

                    String[] clientInfo = line.split(cvsSplitBy);

                    String name = clientInfo[0];
                    String email = clientInfo[1];
                    String password = clientInfo[2];
                    int nif = Integer.parseInt(clientInfo[3]);
                    BigDecimal creditCardNumber = BigDecimal.valueOf(Long.parseLong(clientInfo[4]));
                    int creditCardMonthExpiration = Integer.parseInt(clientInfo[5]);
                    int creditCardNumberYearExpiration = Integer.parseInt(clientInfo[6]);
                    int ccv = Integer.parseInt(clientInfo[7]);
                    double latitude = Double.parseDouble(clientInfo[8]);
                    double longitude = Double.parseDouble(clientInfo[9]);
                    String street = clientInfo[10];
                    int doorNum = Integer.parseInt(clientInfo[11]);
                    String zipcod = clientInfo[12];
                    String locality = clientInfo[13];
                    double altitude = Double.parseDouble(clientInfo[14]);




                    added=uc.addUserAsClient(name, email, password, nif, creditCardNumber, creditCardMonthExpiration,creditCardNumberYearExpiration,
                            ccv, latitude, longitude, street, doorNum, zipcod, locality, altitude);
                }

            }
            DataHandler.getInstance().getConnection().commit();
        } catch (SQLException e) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            System.err.println(e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                }
            }
        }
        return added;

    }

    public boolean addPharmacy(String s) {
        boolean added=false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        PharmacyController pc = new PharmacyController(new PharmacyDataHandler(), new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
        int count = 0;
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {

                    String[] pharmacyInfo = line.split(cvsSplitBy);

                    String name = pharmacyInfo[0].trim();
                    double latitude = Double.parseDouble(pharmacyInfo[1]);
                    double longitude = Double.parseDouble(pharmacyInfo[2]);
                    String street = pharmacyInfo[3].trim();
                    int doorNumber = Integer.parseInt(pharmacyInfo[4]);
                    String zipCode = pharmacyInfo[5].trim();
                    String locality = pharmacyInfo[6].trim();
                    int maxCpacityS = Integer.parseInt(pharmacyInfo[7]);
                    int maxCpacityD = Integer.parseInt(pharmacyInfo[8]);
                    int maxChargingCapacity = Integer.parseInt(pharmacyInfo[9]);
                    double power = Double.parseDouble(pharmacyInfo[10]);
                    int idParkType = Integer.parseInt(pharmacyInfo[11]);
                    String emailAdmin = pharmacyInfo[12].trim();
                    String emailP = pharmacyInfo[13].trim();
                    double altitude = Double.parseDouble(pharmacyInfo[14]);


                    added = pc.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, maxCpacityS, maxCpacityD, maxChargingCapacity, power, idParkType, emailAdmin, emailP, altitude);

                }

            }
            DataHandler.getInstance().getConnection().commit();
        } catch (SQLException e) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            System.err.println(e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                }
            }
        }
        return added;
    }

}
