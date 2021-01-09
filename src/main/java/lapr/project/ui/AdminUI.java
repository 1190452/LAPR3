package lapr.project.ui;

import lapr.project.controller.PharmacyController;
import lapr.project.controller.ProductController;
import lapr.project.controller.UserController;
import lapr.project.controller.VehicleController;
import lapr.project.data.PharmacyDataHandler;
import lapr.project.data.ProductDataHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.data.UserDataHandler;
import lapr.project.model.Courier;
import lapr.project.model.EletricScooter;
import lapr.project.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminUI {

    public static final Scanner READ = new Scanner(System.in);
    private static final String COURIER_ROLE = "COURIER";

    public static void adminMenu() {
        System.out.println("ADMIN MENU\n"
                + "\n1-Create Pharmacy"
                + "\n2-Add Courier"
                + "\n3-Remove Courier"
                + "\n4-Add Eletric Scooter"
                + "\n5-Remove Eletric Scooter"
                + "\n6-Add Medicine"
                + "\n7-Remove Medicine"
                + "\n0-Exit"
        );
    }

    public void adminLoop() throws SQLException {
        String ch;
        do {
            adminMenu();
            ch = READ.nextLine();
            switch (ch) {
                case "1":
                    addPharmacy();
                    break;
                case "2":
                    addCourier();
                    break;
                case "3":
                    removeCourier();
                    break;
                case "4":
                    addEletricScooter();
                    break;
                case "5":
                    removeEletricScooter();
                    break;
                case "6":
                    addMedicine();
                    break;
                case "7":
                    removeMedicine();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (!ch.equals("0")) ;
    }

    private void addPharmacy() {

        System.out.println("\nInsert the pharmacy name:");
        String name = READ.next();

        System.out.println("\nInsert the latitude of your address");
        double latitude = READ.nextDouble();

        System.out.println("\nInsert the longitude of your address");
        double longitude = READ.nextDouble();

        System.out.println("\nInsert your street address");
        String street = READ.next();

        System.out.println("\nInsert your door number");
        int doorNumber = READ.nextInt();

        System.out.println("\nInsert your zipcode");
        String zipCode = READ.next();

        System.out.println("\nInsert your locality");
        String locality = READ.next();

        System.out.println("\nInsert the max capacity of the park");
        int maxCpacity = READ.nextInt();

        System.out.println("\nInsert the actual number of places available in the park");
        int actualCapacity = READ.nextInt();

        System.out.println("\nInsert the max number of charging places of the park");
        int maxChargingCapacity = READ.nextInt();

        System.out.println("\nInsert the actual number of charging places available in the park");
        int actualChargingCapacity = READ.nextInt();

        System.out.println("\nInsert the power of the charging places of the park");
        int power = READ.nextInt();

        System.out.println("\nPharmacy Name:\t" + name
                + "\nLatitude:\t" + latitude
                + "\nLongitude:\t" + longitude
                + "\nStreet:\t" + street
                + "\nDoor Number:\t" + doorNumber
                + "\nZipCode:\t" + zipCode
                + "\nLocality:\t" + locality
                + "\nMax Capacity of the Park:\t" + maxCpacity
                + "\nActual Capacity of the Park:\t" + actualCapacity
                + "\nMax Charging Places in the Park:\t" + maxChargingCapacity
                + "\nActual Charging Places in the Park:\t" + actualChargingCapacity
                + "\nPower of the Charging Places :\t" + power
        );
        System.out.println("Please confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            PharmacyController pc = new PharmacyController(new PharmacyDataHandler());
            pc.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, maxCpacity, actualCapacity, maxChargingCapacity, actualChargingCapacity, power);
            System.out.println("\n\nPharmacy" + name + " registered with sucess! Thank you.\n\n");
        }
    }

    private void addEletricScooter() throws SQLException {
        System.out.println("\nInsert the licence plate of the eletric scooter:");
        String licencePlate = READ.next();

        System.out.println("\nInsert the maximum capacity for the battery of the eletric scooter:");
        double maxBattery = READ.nextDouble();

        System.out.println("\nInsert the actual battery of the eletric scooter:");
        double actualBattery = READ.nextDouble();

        System.out.println("\nInsert the ampere per hour for the battery of the eletric scooter:");
        double ampereHour = READ.nextDouble();

        System.out.println("\nInsert the voltage for the battery of the eletric scooter:");
        double voltage = READ.nextDouble();

        System.out.println("\nInsert the engine power of the eletric scooter");
        double enginePower = READ.nextDouble();

        System.out.println("\nInsert the weight of the eletric scooter");
        double weight = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy");
        int pharmacyID = READ.nextInt();

        System.out.println("\nMax Battery:\t" + maxBattery
                + "\nActual Battery:\t" + actualBattery
                + "\nAmper Hour of the Battery:\t" + ampereHour
                + "\nVoltage of the Battery:\t" + voltage
                + "\nEngine Power:\t" + enginePower
                + "\nWeight:\t" + weight
                + "\nPharmacy ID:\t" + pharmacyID
        );
        System.out.println("\nPlease confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            VehicleController vc = new VehicleController(new ScooterHandler());
            vc.addScooter(licencePlate,maxBattery, actualBattery, ampereHour, voltage, enginePower, weight, pharmacyID);
            System.out.println("\n\nEletric Scooter Added With Sucess ! Thank you.\n\n");
        }
    }

    private void removeEletricScooter() throws SQLException {
        VehicleController vc = new VehicleController(new ScooterHandler());
        List<EletricScooter> eletricScooters = vc.getEletricScooters();

        for (EletricScooter u : eletricScooters) {
            System.out.println(u.toString());
        }

        System.out.println("\nPlease choose the licence plate of the eletric scooter you want to remove: ");
        String scooterLicencePlate = READ.next();

        vc.removeScooter(scooterLicencePlate);

    }

    private void addMedicine() throws SQLException {
        System.out.println("\nInsert Product Name:");
        String name = READ.next();

        System.out.println("\nInsert description:");
        String description = READ.next();

        System.out.println("\nInsert the price of the product:");
        double price = READ.nextDouble();

        System.out.println("\nInsert the product weight:");
        double weight = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy that you are going to work for");
        int pharmacyID = READ.nextInt();

        System.out.println("\nInsert the stock of the product");
        int stock = READ.nextInt();

        System.out.println("\nUsername:\t" + name
                + "\nDescription:\t" + description
                + "\nPrice:\t" + price
                + "\nWeight:\t" + weight
                + "\nPharmacy ID:\t" + pharmacyID
                + "\nStock:\t" + stock);
        System.out.println("\nPlease confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            ProductController pc = new ProductController(new ProductDataHandler());
            pc.addProduct(name, description, price, weight, pharmacyID, stock);
            System.out.println("\n\nProduct Added With Sucess ! Thank you.\n\n");

        }
    }

    private void removeMedicine() throws SQLException {
        ProductController pc = new ProductController(new ProductDataHandler());
        List<Product>  products = pc.getMedicines();

        for (Product u : products) {
            System.out.println(u.toString());
        }

        System.out.println("\nPlease choose the id of the product you want to remove: ");
        int productID = READ.nextInt();

        pc.removeProduct(productID);
    }

    private void removeCourier() throws SQLException {
        UserController uc = new UserController(new UserDataHandler());
        List<Courier> listCourier = uc.getCourierList();

        for (Courier u : listCourier) {
            System.out.println(u.toString());
        }

        System.out.println("\nIntroduce the id of the courier that needs to be removed\n");
        int id = READ.nextInt();
        uc.removeCourier(id);

    }

    private void addCourier() throws SQLException {
        System.out.println("\nInsert your e-mail:");
        String email = READ.next();

        System.out.println("\nInsert your name:");
        String name = READ.next();

        System.out.println("\nInsert your password:");
        String password = READ.next();


        System.out.println("\nInsert your NIF:");
        int nif = READ.nextInt();

        System.out.println("\nInsert your NSS:");
        double nss = READ.nextDouble();

        System.out.println("\nInsert the maximum weight capacity of your backpack:");
        double maxWeightCapacity = READ.nextDouble();

        System.out.println("\nInsert your weight:");
        double weight = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy that you are going to work for");
        int pharmacyID = READ.nextInt();
        ;   //acrescentar aqui validacoes mais tarde

        System.out.println("\nUsername:\t" + name
                + "\nE-mail:\t" + email
                + "\nPassword:\t" + password
                + "\nnif:\t" + nif
                + "\nnss:\t" + nss
                + "\nMax Weight Capacity:\t" + maxWeightCapacity
                + "\nWeight:\t" + weight
                + "\nPharmacy ID:\t" + pharmacyID);
        System.out.println("\nPlease confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            UserController uc = new UserController(new UserDataHandler());
            uc.addUserAsCourier(name, email, nif, nss, password, maxWeightCapacity, weight, pharmacyID, COURIER_ROLE);
            System.out.println("\n\nWelcome to  Menu " + name + "! Thank you.\n\n");
            adminLoop();
        }
    }
}
