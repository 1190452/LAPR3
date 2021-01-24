package lapr.project.data.assessment;

import lapr.project.controller.PharmacyController;
import lapr.project.controller.ProductController;
import lapr.project.controller.UserController;
import lapr.project.controller.VehicleController;
import lapr.project.data.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Facade {


    public Facade() {
    }

    public boolean addClients(String s) {
        boolean added = false;
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


                    added = uc.addUserAsClient(name, email, password, nif, creditCardNumber, creditCardMonthExpiration, creditCardNumberYearExpiration,
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
        boolean added = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        PharmacyController pc = new PharmacyController(new PharmacyDataHandler(), new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
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

    public boolean addVehicle(String s) {
        boolean added = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {

                    String[] vehicleInformation = line.split(cvsSplitBy);

                    String licensePlate = vehicleInformation[0].trim();
                    double maxBattery = Double.parseDouble(vehicleInformation[1]);
                    double enginePower = Double.parseDouble(vehicleInformation[2]);
                    double ahBattery = Double.parseDouble(vehicleInformation[3]);
                    double vBattery = Double.parseDouble(vehicleInformation[4]);
                    int idPharmacy = Integer.parseInt(vehicleInformation[5]);
                    int typeVehicle = Integer.parseInt(vehicleInformation[6]);


                    added = vc.addVehicle(licensePlate, maxBattery, enginePower, ahBattery, vBattery, idPharmacy, typeVehicle);

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

    public boolean removeVehicle(String s) {
        boolean removed = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {

                    String[] vehicleInformation = line.split(cvsSplitBy);

                    String licensePlate = vehicleInformation[0].trim();

                    removed = vc.removeVehicle(licensePlate);

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
        return removed;
    }

    public boolean addPark(String s) {
        boolean added = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        PharmacyController pc = new PharmacyController(new PharmacyDataHandler(), new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {

                    String[] parkInformation = line.split(cvsSplitBy);

                    int maxCapacity = Integer.parseInt(parkInformation[0]);
                    int maxChargingPlaces = Integer.parseInt(parkInformation[1]);
                    double power = Double.parseDouble(parkInformation[2]);
                    int pharmacyID = Integer.parseInt(parkInformation[3]);
                    int idParktype = Integer.parseInt(parkInformation[4]);


                    int parkID = pc.addPark(maxCapacity, maxChargingPlaces, power, pharmacyID, idParktype);
                    if (parkID != 0) {
                        added = true;
                    } else {
                        added = false;
                    }
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

    public boolean addMedicine(String s) {
        boolean added = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler());
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {

                    String[] medicineInformation = line.split(cvsSplitBy);
                    String name = medicineInformation[0].trim();
                    String description = medicineInformation[1].trim();
                    double price = Double.parseDouble(medicineInformation[2]);
                    double weight = Double.parseDouble(medicineInformation[3]);
                    int pharmacyID = Integer.parseInt(medicineInformation[4]);
                    int quantityStock = Integer.parseInt(medicineInformation[5]);

                    added = pc.addProduct(name, description, price, weight, pharmacyID, quantityStock);
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

    public boolean removeMedicine(String s) {
        boolean removed = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler());
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String[] medicineInformation = line.split(cvsSplitBy);
                    int prodID = Integer.parseInt(medicineInformation[0]);
                    removed = pc.removeProduct(prodID);
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
        return removed;
    }

    public boolean addCourier(String s) {
        boolean added = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        UserController uc = new UserController(new UserDataHandler(), new CourierDataHandler(), new ClientDataHandler(), new AddressDataHandler(), new CreditCardDataHandler());
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {

                    String[] courierInformation = line.split(cvsSplitBy);
                    String email = courierInformation[0].trim();
                    String password = courierInformation[1].trim();
                    String name = courierInformation[2].trim();
                    int nif = Integer.parseInt(courierInformation[3]);
                    BigDecimal nss = BigDecimal.valueOf(Long.parseLong(courierInformation[4]));
                    double weight = Double.parseDouble(courierInformation[5]);
                    int pharmacyID = Integer.parseInt(courierInformation[6]);

                    added = uc.addUserAsCourier(name, email, nif, nss, password, weight, pharmacyID);
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

    public boolean removeCourier(String s) {
        boolean removed = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        UserController uc = new UserController(new UserDataHandler(), new CourierDataHandler(), new ClientDataHandler(), new AddressDataHandler(), new CreditCardDataHandler());
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);

            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String[] courierInformation = line.split(cvsSplitBy);
                    int idCourier = Integer.parseInt(courierInformation[0]);

                    removed = uc.removeCourier(idCourier);
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
        return removed;
    }
}
