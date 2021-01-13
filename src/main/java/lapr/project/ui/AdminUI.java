package lapr.project.ui;

import lapr.project.controller.*;
import lapr.project.data.*;
import lapr.project.model.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                + "\n8-Create Delivery Run"
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
                    addVehicle();
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
                case "8":
                    createDeliveryRun();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (!ch.equals("0"));
    }

    private void createDeliveryRun() throws SQLException {
        System.out.println("Chose the delivery method:");
        System.out.println("1-Eletric Scooter");
        System.out.println("2-Drone");

        String option = READ.next();

        switch (option) {
            case "1":
                deliveryRunByScooter();
                break;
            case "2":
                deliveryByDrone();
                break;
            default:
                break;
        }
    }

    private void deliveryByDrone() throws SQLException {
        OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler());

        Pharmacy p = choosePharmacy(c);

        List<Vehicle> dronesAvailable = c.getDronesAvailable(p.getId());

        Vehicle selectedVehicle = null;

        if (dronesAvailable.isEmpty()) {
            System.out.println("No available drones in this pharmacy");
            createDeliveryRun();
        }
        while (selectedVehicle == null) {
            for (Vehicle v : dronesAvailable) {
                System.out.println(v.toString() + "\n");
            }

            System.out.println("Choose a id of a Drone");
            int id = READ.nextInt();
            for (Vehicle dA : dronesAvailable) {
                if (dA.getId() == id) {
                    selectedVehicle = dA;
                    break;
                }
            }
        }

        LinkedHashMap<Integer, ClientOrder> orderList = c.getUndoneOrders();

        for (Map.Entry<Integer, ClientOrder> o : orderList.entrySet()) {
            System.out.println(o.getValue().toString());
        }

        List<ClientOrder> ordersInThisDelivery = new ArrayList<>();

        double weightSum = 0;
        boolean decision = true;
        while (decision && weightSum < selectedVehicle.getMaxWeightCapacity()) {
            System.out.println("Chose an id of a order you want to deliver\n");
            int idD = READ.nextInt();

            weightSum += orderList.get(idD).getFinalWeight();
            if (!ordersInThisDelivery.contains(orderList.get(idD))) {
                ordersInThisDelivery.add(orderList.get(idD));
            }

            System.out.printf("Drone can still carry %.1f kilograms\n", selectedVehicle.getMaxWeightCapacity() - weightSum);
            System.out.println("Do you want to add another order to this delivery?\n");
            System.out.println("1-Yes\n");
            System.out.println("2-No\n");
            switch (READ.nextInt()) {
                case 1:
                    break;
                case 2:
                    decision = false;
                    System.out.println("Processing......\n");
                    break;
                default:
                    System.out.println("Insert a valid option\n");
            }
        }

        c.createDroneDelivery(ordersInThisDelivery, p, weightSum, selectedVehicle.getId());


    }

    private void deliveryRunByScooter() throws SQLException {
        OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler());

        Pharmacy p = choosePharmacy(c);

        LinkedHashMap<Integer, ClientOrder> orderList = c.getUndoneOrders();

        List<Courier> availableCouriers = c.getAvailableCouriers();

        for (Courier courier : availableCouriers) {
            System.out.println(courier.toString());
        }

        Courier selectedCourier = null;
        double weightSum = 0;
        while (selectedCourier == null) {
            System.out.println("Choose a id of a courier:");
            int id = READ.nextInt();
            for (Courier cour : availableCouriers) {
                if (cour.getIdCourier() == id) {
                    selectedCourier = cour;
                    break;
                }
            }
        }


        for (Map.Entry<Integer, ClientOrder> o : orderList.entrySet()) {
            System.out.println(o.getValue().toString());
        }

        List<ClientOrder> ordersInThisDelivery = new ArrayList<>();

        boolean decision = true;
        while (decision && weightSum < selectedCourier.getMaxWeightCapacity()) {
            System.out.println("Chose an id of a order you want to deliver");
            int idD = READ.nextInt();

            weightSum += orderList.get(idD).getFinalWeight();
            if (!ordersInThisDelivery.contains(orderList.get(idD))) {
                ordersInThisDelivery.add(orderList.get(idD));
            }

            System.out.printf("Courier can still carry %.1f kilograms\n", selectedCourier.getMaxWeightCapacity() - weightSum);
            System.out.println("Do you want to add another order to this delivery?\n");
            System.out.println("1-Yes\n");
            System.out.println("2-No\n");
            switch (READ.nextInt()) {
                case 1:
                    break;
                case 2:
                    decision = false;
                    break;
                default:
                    System.out.println("Insert a valid option");
            }
        }

        c.createDelivery(ordersInThisDelivery, p, selectedCourier.getIdCourier());

    }

    private Pharmacy choosePharmacy(OrderController c) {
        List<Pharmacy> allPharmacies = c.getAllPharmacies();

        Pharmacy selectedPharmacy = null;
        while (selectedPharmacy == null) {
            for (Pharmacy p : allPharmacies) {
                System.out.println(p.toString() + "\n");
            }

            System.out.println("Choose a id of a Pharmacy");
            int id = READ.nextInt();
            for (Pharmacy ph : allPharmacies) {
                if (ph.getId() == id) {
                    selectedPharmacy = ph;
                    break;
                }
            }
        }
        return selectedPharmacy;
    }

    private void addPharmacy() {

        System.out.println("\nInsert the pharmacy name:");
        String name = READ.next();

        System.out.println("\nInsert the pharmacy email:");
        String email = READ.next();

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

        System.out.println("\nInsert the max number of charging places of the park");
        int maxChargingCapacity = READ.nextInt();

        System.out.println("\nInsert the power of the charging places of the park");
        int power = READ.nextInt();
        int idParkType;
        do {
            System.out.println("\nIs the park for scooters or drones (Insert 1 for scooter, 2 for drone, 3 for both)");
            idParkType = READ.nextInt();
        } while (idParkType != 1 && idParkType != 2 && idParkType != 3);


        System.out.println("\nPharmacy Name:\t" + name
                +"\nPharmacy Email:\t" + email
                + "\nLatitude:\t" + latitude
                + "\nLongitude:\t" + longitude
                + "\nStreet:\t" + street
                + "\nDoor Number:\t" + doorNumber
                + "\nZipCode:\t" + zipCode
                + "\nLocality:\t" + locality
                + "\nMax Capacity of the Park:\t" + maxCpacity
                + "\nMax Charging Places in the Park:\t" + maxChargingCapacity
                + "\nPower of the Charging Places :\t" + power
        );
        System.out.println("Please confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            PharmacyController pc = new PharmacyController(new PharmacyDataHandler(),new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
            boolean added = pc.registerPharmacyandPark(name,latitude, longitude, street, doorNumber, zipCode, locality, maxCpacity, maxChargingCapacity, power, idParkType, UserSession.getInstance().getUser().getEmail(), email);
            if (added)
                Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, "The pharmacy with the name " + name + " was added!");
            else
                Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, "There was a problem adding the pharmacy. Check your information please.");

        }
    }

    private void addVehicle() throws SQLException {
        int typeVehicle;
        do{
            System.out.println("\nIs the vehicle an electric scooter or a drone? (1 for electric scooter | 2 for drone)");
            typeVehicle = READ.nextInt();
        }while(typeVehicle != 1 && typeVehicle != 2);
        System.out.println("\nInsert the licence plate of the vehicle:");
        String licencePlate = READ.next();

        double maximumCapacity = 0;
        if(typeVehicle == 2) {
            System.out.println("\nInsert the maximum capacity of the vehicle:");
            maximumCapacity = READ.nextDouble();
        }

        System.out.println("\nInsert the maximum battery of the vehicle:");
        double maximumBattery = READ.nextDouble();

        System.out.println("\nInsert the actual battery of the vehicle:");
        double actualBattery = READ.nextDouble();

        System.out.println("\nInsert the ampere per hour for the battery of the vehicle:");
        double ampereHour = READ.nextDouble();

        System.out.println("\nInsert the voltage for the battery of the vehicle:");
        double voltage = READ.nextDouble();

        System.out.println("\nInsert the engine power of the vehicle");
        double enginePower = READ.nextDouble();

        System.out.println("\nInsert the weight of the vehicle");
        double weight = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy");
        int pharmacyID = READ.nextInt();

        System.out.println("\nInsert the type of vehicle");
        int type = READ.nextInt();

        if(typeVehicle == 2) {
            System.out.println("\nMax capacity:\t" + maximumCapacity
                    + "\nMax Battery:\t" + maximumBattery
                    + "\nActual Battery:\t" + actualBattery
                    + "\nAmper Hour of the Battery:\t" + ampereHour
                    + "\nVoltage of the Battery:\t" + voltage
                    + "\nEngine Power:\t" + enginePower
                    + "\nWeight:\t" + weight
                    + "\nPharmacy ID:\t" + pharmacyID
            );
        }else {
            System.out.println("\nMax Battery:\t" + maximumBattery
                    + "\nActual Battery:\t" + actualBattery
                    + "\nAmper Hour of the Battery:\t" + ampereHour
                    + "\nVoltage of the Battery:\t" + voltage
                    + "\nEngine Power:\t" + enginePower
                    + "\nWeight:\t" + weight
                    + "\nPharmacy ID:\t" + pharmacyID
            );
        }

        System.out.println("\nPlease confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            VehicleController vc = new VehicleController(new VehicleHandler());
            if(typeVehicle == 1) {
                vc.addScooter(licencePlate, maximumBattery,actualBattery, enginePower, ampereHour, voltage, weight, pharmacyID, typeVehicle);
                System.out.println("\n\nEletric Scooter Added With Sucess ! Thank you.\n\n");

            }else {
                vc.addDrone(maximumCapacity, licencePlate, maximumBattery,actualBattery, ampereHour, voltage, enginePower, weight, pharmacyID, type);
                System.out.println("\n\nDrone Added With Sucess ! Thank you.\n\n");

            }
        }
    }

    private void removeEletricScooter() {
        VehicleController vc = new VehicleController(new VehicleHandler());
        ArrayList<Vehicle> eletricScooters = vc.getVehicles();

        for (Vehicle vehicle : eletricScooters) {
            System.out.println(vehicle.toString());
        }

        System.out.println("\nPlease choose the licence plate of the vehicle you want to remove: ");
        String licencePlate = READ.next();

        vc.removeVehicle(licencePlate);

    }

    private void addMedicine() {
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

    private void removeMedicine() {
        ProductController pc = new ProductController(new ProductDataHandler());
        List<Pharmacy> phar = pc.getPharmacies();
        for (Pharmacy p : phar){
            System.out.println(p.toString());
        }
        System.out.println("Introduce the id of the pharmacy");
        int id = READ.nextInt();
        List<Product> products = pc.getMedicines(id);

        for (Product u : products) {
            System.out.println(u.toString());
        }

        System.out.println("\nPlease choose the id of the product you want to remove: ");
        int productID = READ.nextInt();

        pc.removeProduct(productID);
    }

    private void removeCourier() {
        UserController uc = new UserController(new UserDataHandler(), new CourierDataHandler(), new ClientDataHandler(), new AddressDataHandler(), new CreditCardDataHandler());
        List<Courier> listCourier = uc.getCourierList();

        for (Courier u : listCourier) {
            System.out.println(u.toString());
        }

        System.out.println("\nIntroduce the id of the courier that needs to be removed\n");
        int id = READ.nextInt();
        uc.removeCourier(id);

    }

    private void addCourier() throws SQLException {
        System.out.println("\nInsert courier e-mail:");
        String email = READ.next();

        System.out.println("\nInsert your name:");
        String name = READ.next();

        System.out.println("\nInsert your password:");
        String password = READ.next();


        System.out.println("\nInsert your NIF:");
        int nif = READ.nextInt();

        System.out.println("\nInsert your NSS:");
        BigDecimal nss = READ.nextBigDecimal();

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
            UserController uc = new UserController(new UserDataHandler(), new CourierDataHandler(), new ClientDataHandler(), new AddressDataHandler(), new CreditCardDataHandler());

            uc.addUserAsCourier(name, email, nif, nss, password, maxWeightCapacity, weight, pharmacyID, COURIER_ROLE);
            System.out.println("\n\nThe courier " + name + " was added!\n Thank you.\n\n");
            adminLoop();
        }
    }
}
