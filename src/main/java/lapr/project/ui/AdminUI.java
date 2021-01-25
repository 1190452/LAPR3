package lapr.project.ui;

import lapr.project.controller.*;
import lapr.project.data.*;
import lapr.project.model.*;
import oracle.ucp.util.Pair;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminUI {

    public static final Scanner READ = new Scanner(System.in);
    private static final double MAXCAPACITYCOURIER = 20;
    private static final double MAXCAPACITYDRONE = 10;
    private static final String CONFIRMATION = "Please confirm the provided information for registration: (Yes/No)";
    private static final String PHARMACYID = "\nPharmacy ID:\t";
    private static final String YES = "1-Yes\n";
    private static final String NO = "2-No\n";
    private static final String VALID_OPTION = "Insert a valid option";

    public static void adminMenu() {
        System.out.println("ADMIN MENU\n"
                + "\n1-Create Pharmacy"
                + "\n2-Add Courier"
                + "\n3-Remove Courier"
                + "\n4-Add Vehicle"
                + "\n5-Remove Vehicle"
                + "\n6-Add Medicine"
                + "\n7-Remove Medicine"
                + "\n8-Create Delivery Run"
                + "\n9-RestockOrder Request"
                + "\n0-Exit"
        );
    }

    public void adminLoop() throws SQLException, IOException {
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
                    removeVehicle();
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
                case "9":
                    createDeliveryRestock();
                    break;
                default:
                    break;
            }
        } while (!ch.equals("0"));
    }

    private void createDeliveryRestock() throws IOException {
        OrderController rc = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler());

        List<Pharmacy> pharms = rc.getAllPharmacies();
        Pharmacy phar = choosePharmacy(rc);
        int idPharmReceiver = phar.getId();

        List<RestockOrder> restocklist = rc.getRestockList(idPharmReceiver);
        List<RestockOrder> restocklistToMakeDelivery = new ArrayList<>();
        List<Pharmacy> points = new ArrayList<>();
        for (RestockOrder res : restocklist) {
            System.out.println(res.toString());
        }

        double weightSum = 0;
        boolean decision = true;
        while (decision && weightSum < MAXCAPACITYCOURIER) {
            System.out.println("Chose an id of a restock request you want to get");
            int idR = READ.nextInt();
            RestockOrder r = null;
            for (RestockOrder res : restocklist) {
                if (res.getId() == idR) {
                    r = res;
                }
            }

            Pharmacy aux;
            for (Pharmacy p : pharms) {
                if (r != null && p.getId() == r.getPharmSenderID()) {
                    aux = p;
                    points.add(aux);
                }
            }

            assert r != null;
            Product p = pc.getProductByID(r.getProductID());
            weightSum += p.getWeight();
            if (!restocklistToMakeDelivery.contains(r)) {
                restocklistToMakeDelivery.add(r);
            }

            System.out.println("Do you want to add another restock request to this delivery?\n");
            System.out.println(YES);
            System.out.println(NO);
            switch (READ.nextInt()) {
                case 1:
                    break;
                case 2:
                    decision = false;
                    break;
                default:
                    System.out.println(VALID_OPTION);
            }
        }

        System.out.println("Do you prefer the most efficient energy path or the fastest path?\n");
        System.out.println("1 - Most Efficient Energy Path");
        System.out.println("2 - Fastest Path");
        List<Address> allAddresses = rc.getAllAddresses();
        List<Path> paths = rc.getAllPaths();

        switch (READ.nextInt()) {
            case 1:
                Pair<LinkedList<Address>, Double> energyByDrone = rc.estimateCostPathForRestock(allAddresses, restocklistToMakeDelivery, phar, 2, paths, weightSum);
                rc = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                        new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
                Pair<LinkedList<Address>, Double> energyByEletricScooter = rc.estimateCostPathForRestock(allAddresses, restocklistToMakeDelivery, phar, 1, paths, weightSum);

                chooseBestVehicleForRestockRequest(phar, restocklistToMakeDelivery, weightSum, points, paths, energyByDrone.get2nd(), energyByEletricScooter.get2nd(), energyByDrone.get2nd(), energyByEletricScooter.get2nd());

                break;
            case 2:
                Pair<LinkedList<Address>, Double> distanceByDrone = rc.estimateCostPathForRestock(allAddresses, restocklistToMakeDelivery, phar, 2, paths, 0);
                rc = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                        new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
                Pair<LinkedList<Address>, Double> distanceByEletricScooter = rc.estimateCostPathForRestock(allAddresses, restocklistToMakeDelivery, phar, 1, paths, 0);

                double necessaryEnergyD = rc.getNecessaryEnergy(distanceByDrone.get1st(), weightSum, paths, 2);
                double necessaryEnergyE = rc.getNecessaryEnergy(distanceByEletricScooter.get1st(), weightSum, paths, 3);

                chooseBestVehicleForRestockRequest(phar, restocklistToMakeDelivery, weightSum, points, paths, distanceByEletricScooter.get2nd(), distanceByEletricScooter.get2nd(), necessaryEnergyD, necessaryEnergyE);

                break;
            default:
                System.out.println(VALID_OPTION);
        }
    }

    public void chooseBestVehicleForRestockRequest(Pharmacy phar, List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points,List<Path> paths, double costDR, double costES,double necessaryEnergyDR, double necessaryEnergyES) throws IOException {
        OrderController rc = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
        VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(),
                new PharmacyDataHandler(), new AddressDataHandler());

        if (rc.getDronesAvailable(phar.getId(), necessaryEnergyDR) == null && rc.getAvailableCouriers(phar.getId()) == null) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.INFO, "There are no drones or couriers available to do this restock request");
        } else if (rc.getDronesAvailable(phar.getId(), necessaryEnergyDR) == null) {
            restockDeliveryByEletricScooter(restocklistToMakeDelivery, weightSum, points, costES, paths, rc, vc, necessaryEnergyES);
        } else if (weightSum > MAXCAPACITYDRONE) {
            restockDeliveryByEletricScooter(restocklistToMakeDelivery, weightSum, points, costES, paths, rc, vc, necessaryEnergyES);
        } else if (costDR < costES) {
            restockDeliveryByDrone(restocklistToMakeDelivery, weightSum, points, costDR, paths, rc, vc, necessaryEnergyDR);
        } else if (costDR > costES) {
            restockDeliveryByEletricScooter(restocklistToMakeDelivery, weightSum, points, costES, paths, rc, vc, necessaryEnergyES);
        } else {
            restockDeliveryByEletricScooter(restocklistToMakeDelivery, weightSum, points, costES, paths, rc, vc, necessaryEnergyES);
        }
    }

    private void restockDeliveryByEletricScooter(List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points, double cost, List<Path> paths, OrderController rc, VehicleController v, double necessaryEmergy) throws IOException {

        Pair<Integer, Vehicle> data = rc.createRestockRequestByEletricScooter(restocklistToMakeDelivery, weightSum, points, cost, paths, necessaryEmergy);
        v.parkScooter(data.get1st(), data.get2nd());
    }

    private void restockDeliveryByDrone(List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points, double cost, List<Path> paths, OrderController rc, VehicleController vc, double necessaryEnergy) throws IOException {

        Pair<Integer, Vehicle> data = rc.createRestockRequestByDrone(restocklistToMakeDelivery, weightSum, points, cost, paths, necessaryEnergy);
        vc.parkDrone(data.get1st(), data.get2nd());
    }

    private void createDeliveryRun() throws IOException {
        OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
        Pharmacy phar = choosePharmacy(c);

        Map<Integer, ClientOrder> orderList = c.getUndoneOrders(phar.getId());

        if (!orderList.isEmpty()) {
            for (Map.Entry<Integer, ClientOrder> o : orderList.entrySet()) {
                System.out.println(o.getValue().toString());
            }

            LinkedList<ClientOrder> ordersInThisDelivery = new LinkedList<>();

            boolean decision = true;
            double weightSum = 0;
            int finalWeight = 0;
            while (decision) {
                if (finalWeight > MAXCAPACITYCOURIER) {
                    System.out.println("The orders exceeded the maximum capacity of the courier. The last order added will be removed.");
                    ordersInThisDelivery.removeLast();
                } else {
                    System.out.println("Chose an id of a order you want to deliver. (The courier has " + (MAXCAPACITYCOURIER - finalWeight) + "kg available to deliver\n");
                    int idD = READ.nextInt();
                    weightSum += orderList.get(idD).getFinalWeight();
                    if (!ordersInThisDelivery.contains(orderList.get(idD))) {
                        ordersInThisDelivery.add(orderList.get(idD));
                        finalWeight += orderList.get(idD).getFinalWeight();
                    }
                }


                System.out.println("Do you want to add another order to this delivery?\n");
                System.out.println(YES);
                System.out.println(NO);
                switch (READ.nextInt()) {
                    case 1:
                        break;
                    case 2:
                        decision = false;
                        System.out.println("Processing......\n");
                        break;
                    default:
                        System.out.println(VALID_OPTION);
                }
            }

            System.out.println("Do you prefer the most efficient energy path or the fastest path?\n");
            System.out.println("1 - Most Efficient Energy Path");
            System.out.println("2 - Fastest Path");
            List<Address> allAddresses = c.getAllAddresses();
            List<Path> paths = c.getAllPaths();
            switch (READ.nextInt()) {
                case 1:
                    Pair<LinkedList<Address>, Double> energyByDrone = c.estimateCostPathForDelivery(allAddresses, ordersInThisDelivery, phar, 2, paths, weightSum);
                    c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
                    Pair<LinkedList<Address>, Double> energyByEletricScooter = c.estimateCostPathForDelivery(allAddresses, ordersInThisDelivery, phar, 1, paths, weightSum);

                    chooseBestVehicleForDelivery(phar, ordersInThisDelivery, c, energyByDrone.get2nd(), energyByEletricScooter.get2nd(), weightSum, energyByDrone.get2nd(), energyByEletricScooter.get2nd());
                    break;

                case 2:
                    Pair<LinkedList<Address>, Double> distanceByDrone = c.estimateCostPathForDelivery(allAddresses, ordersInThisDelivery, phar, 2, paths, 0);
                    c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler());
                    Pair<LinkedList<Address>, Double> distanceByEletricScooter = c.estimateCostPathForDelivery(allAddresses, ordersInThisDelivery, phar, 1, paths, 0);
                    double necessaryEnergyD = c.getNecessaryEnergy(distanceByDrone.get1st(), weightSum, paths, 2);
                    double necessaryEnergyE = c.getNecessaryEnergy(distanceByEletricScooter.get1st(), weightSum, paths, 1);

                    chooseBestVehicleForDelivery(phar, ordersInThisDelivery, c, distanceByDrone.get2nd(), distanceByEletricScooter.get2nd(), weightSum, necessaryEnergyD, necessaryEnergyE);
                    break;
                default:
                    System.out.println(VALID_OPTION);
            }
        } else {
            Logger.getLogger(AdminUI.class.getName()).log(Level.INFO, "There are no orders for this pharmacy");
        }
    }

    public void chooseBestVehicleForDelivery(Pharmacy phar, LinkedList<ClientOrder> ordersInThisDelivery, OrderController c, double costDR, double costES, double weightSum, double necessaryEnergyDR, double necessaryEnergyES) throws IOException {
        if (c.getDronesAvailable(phar.getId(), necessaryEnergyDR) == null && c.getAvailableCouriers(phar.getId()) == null) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.INFO, "There are no drones or couriers available to do this delivery");
        } else if (c.getDronesAvailable(phar.getId(), necessaryEnergyDR) == null) {
            deliveryByScooter(phar, ordersInThisDelivery, c, costES, weightSum, necessaryEnergyES);
        } else if (weightSum > MAXCAPACITYDRONE) {
            deliveryByScooter(phar, ordersInThisDelivery, c, costES, weightSum, necessaryEnergyES);
        } else if (costDR < costES) {
            deliveryByDrone(phar, ordersInThisDelivery, c, costDR, weightSum, necessaryEnergyDR);
        } else if (costDR > costES) {
            deliveryByScooter(phar, ordersInThisDelivery, c, costES, weightSum, necessaryEnergyES);
        } else {
            deliveryByScooter(phar, ordersInThisDelivery, c, costES, weightSum, necessaryEnergyES);
        }
    }

    private void deliveryByDrone(Pharmacy phar, LinkedList<ClientOrder> ordersInThisDelivery, OrderController c, double cost, double weight, double necessaryEnergy) throws IOException {

        Vehicle v = c.createDroneDelivery(ordersInThisDelivery, phar, cost, weight, necessaryEnergy);
        if (v != null) {
            parkDrone(phar.getId(), v);
        }
    }

    private void deliveryByScooter(Pharmacy phar, LinkedList<ClientOrder> ordersInThisDelivery, OrderController c, double cost, double weight, double necessaryEnergy) {

        boolean delivery = c.createDeliveryByScooter(ordersInThisDelivery, phar, cost, weight, necessaryEnergy);
        if (delivery) {
            System.out.println("Delivery created with sucess!");
        } else {
            System.out.println("There are no couriers available to make this delivery");
        }

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
        String name = READ.nextLine();

        System.out.println("\nInsert the pharmacy email:");
        String email = READ.nextLine();

        System.out.println("\nInsert the latitude of your address");
        double latitude = READ.nextDouble();

        System.out.println("\nInsert the longitude of your address");
        double longitude = READ.nextDouble();

        System.out.println("\nInsert the altitude of your address. (0 if not known)");
        double altitude = READ.nextDouble();
        READ.nextLine();

        System.out.println("\nInsert your street address");
        String street = READ.nextLine();

        System.out.println("\nInsert your door number");
        int doorNumber = READ.nextInt();
        READ.nextLine();
        System.out.println("\nInsert your zipcode");
        String zipCode = READ.nextLine();

        System.out.println("\nInsert your locality");
        String locality = READ.nextLine();

        int idParkType;
        do {
            System.out.println("\nIs the park for scooters or drones (Insert 1 for scooter, 2 for drone, 3 for both)");
            idParkType = READ.nextInt();
        } while (idParkType != 1 && idParkType != 2 && idParkType != 3);

        int maxCpacityS = 0;
        int maxCpacityD = 0;

        if (idParkType == 1) {
            System.out.println("\nInsert the max capacity of the park of the scooters");
            maxCpacityS = READ.nextInt();
        } else if (idParkType == 2) {
            System.out.println("\nInsert the max capacity of the park of the drones");
            maxCpacityD = READ.nextInt();
        } else {
            System.out.println("\nInsert the max capacity of the park of the scooters");
            maxCpacityS = READ.nextInt();

            System.out.println("\nInsert the max capacity of the park of the drones");
            maxCpacityD = READ.nextInt();
        }

        System.out.println("\nInsert the max number of charging places of the park");
        int maxChargingCapacity = READ.nextInt();


        System.out.println("\nInsert the power of the charging places of the park");
        int power = READ.nextInt();
        READ.nextLine();


        System.out.println("\nPharmacy Name:\t" + name
                + "\nPharmacy Email:\t" + email
                + "\nLatitude:\t" + latitude
                + "\nLongitude:\t" + longitude
                + "\nAltitude:\t" + altitude
                + "\nStreet:\t" + street
                + "\nDoor Number:\t" + doorNumber
                + "\nZipCode:\t" + zipCode
                + "\nLocality:\t" + locality
                + "\nMax Charging Places in the Park:\t" + maxChargingCapacity
                + "\nPower of the Charging Places :\t" + power
        );
        System.out.println(CONFIRMATION);
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {

            PharmacyController pc = new PharmacyController(new PharmacyDataHandler(), new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
            boolean added;
            if (idParkType == 1) {
                added = pc.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, maxCpacityS, 0, maxChargingCapacity, power, idParkType, UserSession.getInstance().getUser().getEmail(), email, altitude);
            } else if (idParkType == 2) {
                added = pc.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, 0, maxCpacityD, maxChargingCapacity, power, idParkType, UserSession.getInstance().getUser().getEmail(), email, altitude);
            } else {
                added = pc.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, maxCpacityS, maxCpacityD, maxChargingCapacity, power, idParkType, UserSession.getInstance().getUser().getEmail(), email, altitude);
            }

            if (added)
                Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, () -> "The pharmacy with the name" + name + " was added!");
            else
                Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, "There was a problem adding the pharmacy. Check your information please.");

        }
    }

    private void addVehicle() {
        int typeVehicle;
        do {
            System.out.println("\nIs the vehicle an electric scooter or a drone? (1 for electric scooter | 2 for drone)");
            typeVehicle = READ.nextInt();
        } while (typeVehicle != 1 && typeVehicle != 2);
        System.out.println("\nInsert the licence plate of the vehicle:");
        String licensePlate = READ.next();

        System.out.println("\nInsert the maximum battery of the vehicle:");
        double maximumBattery = READ.nextDouble();

        System.out.println("\nInsert the ampere per hour for the battery of the vehicle:");
        double ampereHour = READ.nextDouble();

        System.out.println("\nInsert the voltage for the battery of the vehicle:");
        double voltage = READ.nextDouble();

        System.out.println("\nInsert the engine power of the vehicle");
        double enginePower = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy");
        int pharmacyID = READ.nextInt();

        System.out.println("\nMax Battery:\t" + maximumBattery
                + "\nAmper Hour of the Battery:\t" + ampereHour
                + "\nVoltage of the Battery:\t" + voltage
                + "\nEngine Power:\t" + enginePower
                + PHARMACYID + pharmacyID
        );

        System.out.println(CONFIRMATION);
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
            boolean added = vc.addVehicle(licensePlate, maximumBattery, enginePower, ampereHour, voltage, pharmacyID, typeVehicle);
            if (added)
                Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, ("The vehicle was added with success!"));
            else
                Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, ("The vehicle wasn't added. Try again later."));
        }
    }

    private void removeVehicle() {
        VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        List<Vehicle> vehicleList = vc.getVehicles();

        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle.toString());
        }

        System.out.println("\nPlease choose the licence plate of the vehicle you want to remove: ");
        String licencePlate = READ.next();

        if (vc.removeVehicle(licencePlate)) {
            Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, () -> "The vehicle with the license plate " + licencePlate + " was removed!");
        } else {
            Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, "There was a problem removing the pharmacy. Check your information please.");

        }

    }

    private void addMedicine() {
        System.out.println("\nInsert Product Name:");
        String name = READ.nextLine();

        System.out.println("\nInsert description:");
        String description = READ.nextLine();

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
                + PHARMACYID + pharmacyID
                + "\nStock:\t" + stock);
        System.out.println(CONFIRMATION);
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler());
            pc.addProduct(name, description, price, weight, pharmacyID, stock);
            System.out.println("\n\nProduct Added With Sucess ! Thank you.\n\n");

        }
    }

    private void removeMedicine() {
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler());
        List<Pharmacy> phar = pc.getPharmacies();
        for (Pharmacy p : phar) {
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
        READ.nextLine();
        uc.removeCourier(id);


    }

    private void addCourier() throws SQLException, IOException {
        System.out.println("\nInsert courier e-mail:");
        String email = READ.next();

        System.out.println("\nInsert courier name:");
        String name = READ.next();

        System.out.println("\nInsert courier password:");
        String password = READ.next();


        System.out.println("\nInsert courier NIF:");
        int nif = READ.nextInt();

        System.out.println("\nInsert courier NSS:");
        BigDecimal nss = READ.nextBigDecimal();


        System.out.println("\nInsert courier weight:");
        double weight = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy that the courier is going to work for");
        int pharmacyID = READ.nextInt();


        System.out.println("\nUsername:\t" + name
                + "\nE-mail:\t" + email
                + "\nPassword:\t" + password
                + "\nnif:\t" + nif
                + "\nnss:\t" + nss
                + "\nWeight:\t" + weight
                + PHARMACYID + pharmacyID);
        System.out.println(CONFIRMATION);
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {
            UserController uc = new UserController(new UserDataHandler(), new CourierDataHandler(), new ClientDataHandler(), new AddressDataHandler(), new CreditCardDataHandler());

            uc.addUserAsCourier(name, email, nif, nss, password, weight, pharmacyID);
            System.out.println("\n\nThe courier " + name + " was added!\n Thank you.\n\n");
            adminLoop();
        }
    }

    private void parkDrone(int pharmacyId, Vehicle drone) throws IOException {
        VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        if (vc.parkDrone(pharmacyId, drone)) {
            System.out.println("Park Completed");
        } else {
            System.out.println("Park Not completed");
        }
    }

}
