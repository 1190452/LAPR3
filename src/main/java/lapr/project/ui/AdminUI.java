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
                    System.out.println("Invalid option");
                    break;
            }
        } while (!ch.equals("0"));
    }

    private void createDeliveryRestock() throws IOException {
        System.out.println("Chose the delivery method:");
        System.out.println("1-Eletric Scooter");
        System.out.println("2-Drone");

        String option = READ.next();

        switch (option) {
            case "1":
                restockDeliveryByEletricScooter();
                break;
            case "2":
                restockDeliveryByDrone();
                break;
            default:
                break;
        }
    }

    private void restockDeliveryByEletricScooter() throws IOException {
        OrderController rc = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler());
        VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(),new CourierDataHandler() ,
                new PharmacyDataHandler(), new AddressDataHandler());
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler(), new RestockDataHandler());
        List<Pharmacy> pharms = rc.getAllPharmacies();
        for (Pharmacy p : pharms) {
            System.out.println(p.toString());
        }

        System.out.println("Chose an id of pahrmacy to get Stock");
        int idPharmReceiver = READ.nextInt();

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

            Pharmacy aux = null;
            for (Pharmacy p : pharms) {
                if (p.getId() == r.getPharmSenderID()) {
                    aux = p;
                    points.add(aux);
                }
            }

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
        List<Path> pathPairs = new ArrayList<>();
        Pair<LinkedList<Address>, Double> path = rc.getPath(restocklistToMakeDelivery, pathPairs);
        for(Path pair : pathPairs){
            System.out.println(pair.toString());
            System.out.println("Introduce the Road Rolling Resistance of this Path");
            double roadRR = READ.nextDouble();
            pair.setRoad_rolling_resistance(roadRR);
            System.out.println("Introduce the Wind Speed");
            double windSpeed = READ.nextDouble();
            pair.setWindspeed(windSpeed);
            System.out.println("Introduce the Wind direction of this Path");
            double directionWind = READ.nextDouble();
            pair.setWindDirection(directionWind);
        }
        Pair<Integer, Vehicle> data = rc.createRestockRequestByEletricScooter(restocklistToMakeDelivery, weightSum, points, path.get2nd(), pathPairs);
        vc.parkScooter(data.get1st(), data.get2nd());
    }

    private void restockDeliveryByDrone() throws IOException {
        OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(),new RefillStockDataHandler(), new RestockDataHandler());
        VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(),new CourierDataHandler() ,
                new PharmacyDataHandler(), new AddressDataHandler());
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler(), new RestockDataHandler());
        List<Pharmacy> pharms = c.getAllPharmacies();
        for (Pharmacy p : pharms) {
            System.out.println(p.toString());
        }

        System.out.println("Chose an id of pahrmacy to get Stock");
        int idPharmReceiver = READ.nextInt();

        List<RestockOrder> restocklist = c.getRestockList(idPharmReceiver);
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

            Pharmacy aux = null;
            for (Pharmacy p : pharms) {
                if (p.getId() == r.getPharmSenderID()) {
                    aux = p;
                    points.add(aux);
                }
            }

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
        List<Path> pathPairs = new ArrayList<>();
        Pair<LinkedList<Address>, Double> path = c.getPath(restocklistToMakeDelivery, pathPairs);
        for(Path pair : pathPairs){
            System.out.println(pair.toString());
            System.out.println("Introduce the Road Rolling Resistance of this Path");
            double roadRR = READ.nextDouble();
            pair.setRoad_rolling_resistance(roadRR);
            System.out.println("Introduce the Wind Speed");
            double windSpeed = READ.nextDouble();
            pair.setWindspeed(windSpeed);
            System.out.println("Introduce the Wind direction of this Path");
            double directionWind = READ.nextDouble();
            pair.setWindDirection(directionWind);
        }
        Pair<Integer, Vehicle> data = c.createRestockRequestByDrone(restocklistToMakeDelivery, weightSum, points, path.get2nd(), pathPairs);
        vc.parkDrone(data.get1st(), data.get2nd());

    }

    private void createDeliveryRun() throws SQLException, IOException {
        OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler());
        Pharmacy phar = choosePharmacy(c);

        Map<Integer, ClientOrder> orderList = c.getUndoneOrders(phar.getId());

        if(!orderList.isEmpty()){
            for (Map.Entry<Integer, ClientOrder> o : orderList.entrySet()) {
                System.out.println(o.getValue().toString());
            }
            System.out.println("Chose the delivery method:");
            System.out.println("1-Eletric Scooter");
            System.out.println("2-Drone");

            String option = READ.next();

            switch (option) {
                case "1":
                    deliveryRunByScooter(phar, orderList, c);
                    break;
                case "2":
                    deliveryByDrone(phar, orderList, c);
                    break;
                default:
                    break;
            }
        }else {
            Logger.getLogger(AdminUI.class.getName()).log(Level.INFO, "There are no orders for this pharmacy");
        }



    }

    private void deliveryByDrone(Pharmacy phar,  Map<Integer, ClientOrder> orderList, OrderController c) throws SQLException, IOException {

        LinkedList<ClientOrder> ordersInThisDelivery = new LinkedList<>();

        boolean decision = true;
        double weightSum = 0;
        while (decision && MAXCAPACITYDRONE > weightSum) {
            System.out.println("Chose an id of a order you want to deliver\n");
            int idD = READ.nextInt();
            weightSum += orderList.get(idD).getFinalWeight();
            if (!ordersInThisDelivery.contains(orderList.get(idD))) {
                ordersInThisDelivery.add(orderList.get(idD));
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

        List<Path> paths = new ArrayList<>();
        double distance = c.createPaths(ordersInThisDelivery, phar, paths);
        for(Path pair : paths){
            System.out.println(pair.toString());
            System.out.println("Introduce the Road Rolling Resistance of this Path");
            double roadRR = READ.nextDouble();
            pair.setRoad_rolling_resistance(roadRR);
            System.out.println("Introduce the Wind Speed");
            double windSpeed = READ.nextDouble();
            pair.setWindspeed(windSpeed);
            System.out.println("Introduce the Wind direction of this Path");
            double directionWind = READ.nextDouble();
            pair.setWindDirection(directionWind);
        }
        Vehicle v = c.createDroneDelivery(ordersInThisDelivery, phar, weightSum, distance, paths);

        if (v!=null) {
            System.out.println("Delivery created with sucess!");
            //TIMER
            callTimer("Delivery Created...");  //SIMULATION OF THE DELIVERY
            c.updateStatusDelivery(ordersInThisDelivery.get(0).getDeliveryId());
            c.updateStatusVehicle(v);
            callTimer("Waiting...");

            parkDrone(phar.getId(),v);
        }else{
            System.out.println("There are no drones with capacity to make this delivery");
        }

        parkDrone(phar.getId(), v);

    }

    private void deliveryRunByScooter(Pharmacy phar, Map<Integer, ClientOrder> orderList,   OrderController c) throws SQLException {

        if(orderList != null) {
            for (Map.Entry<Integer, ClientOrder> o : orderList.entrySet()) {
                System.out.println(o.getValue().toString());
            }

            LinkedList<ClientOrder> ordersInThisDelivery = new LinkedList<>();

            double weightSum = 0;
            boolean decision = true;
            while (decision && weightSum < MAXCAPACITYCOURIER) {
                System.out.println("Chose an id of a order you want to deliver");
                int idD = READ.nextInt();

                weightSum += orderList.get(idD).getFinalWeight();
                if (!ordersInThisDelivery.contains(orderList.get(idD))) {
                    ordersInThisDelivery.add(orderList.get(idD));
                }

                System.out.println("Do you want to add another order to this delivery?\n");
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

            List<Path> paths = new ArrayList<>();
            double distance = c.createPaths(ordersInThisDelivery, phar, paths);
            for(Path pair : paths){
                System.out.println(pair.toString());
                System.out.println("Introduce the Road Rolling Resistance of this Path");
                double roadRR = READ.nextDouble();
                pair.setRoad_rolling_resistance(roadRR);
                System.out.println("Introduce the Wind Speed");
                double windSpeed = READ.nextDouble();
                pair.setWindspeed(windSpeed);
                System.out.println("Introduce the Wind direction of this Path");
                double directionWind = READ.nextDouble();
                pair.setWindDirection(directionWind);
            }
            boolean delivery = c.createDeliveryByScooter(ordersInThisDelivery, phar, weightSum, distance, paths);
            if (delivery) {
                System.out.println("Delivery created with sucess!");
            }else{
                System.out.println("There are no couriers available to make this delivery");
            }
        }else {
            System.out.println("There are no orders available.");
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

        System.out.println("\nInsert your street address");
        String street = READ.nextLine();

        System.out.println("\nInsert your door number");
        int doorNumber = READ.nextInt();

        System.out.println("\nInsert your zipcode");
        String zipCode = READ.next();

        System.out.println("\nInsert your locality");
        String locality = READ.nextLine();

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
                + "\nPharmacy Email:\t" + email
                + "\nLatitude:\t" + latitude
                + "\nLongitude:\t" + longitude
                + "\nAltitude:\t" + altitude
                + "\nStreet:\t" + street
                + "\nDoor Number:\t" + doorNumber
                + "\nZipCode:\t" + zipCode
                + "\nLocality:\t" + locality
                + "\nMax Capacity of the Park:\t" + maxCpacity
                + "\nMax Charging Places in the Park:\t" + maxChargingCapacity
                + "\nPower of the Charging Places :\t" + power
        );
        System.out.println(CONFIRMATION);
        String confirmation = READ.next();

        if (confirmation.equalsIgnoreCase("YES")) {

            PharmacyController pc = new PharmacyController(new PharmacyDataHandler(), new ParkHandler(), new AddressDataHandler(), new ClientDataHandler());
            boolean added = pc.registerPharmacyandPark(name, latitude, longitude, street, doorNumber, zipCode, locality, maxCpacity, maxChargingCapacity, power, idParkType, UserSession.getInstance().getUser().getEmail(), email, altitude);

            if (added)
                Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, "The pharmacy with the name " + name + " was added!");
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

        System.out.println("\nInsert the actual battery of the vehicle:");
        double actualBattery = READ.nextDouble();

        System.out.println("\nInsert the ampere per hour for the battery of the vehicle:");
        double ampereHour = READ.nextDouble();

        System.out.println("\nInsert the voltage for the battery of the vehicle:");
        double voltage = READ.nextDouble();

        System.out.println("\nInsert the engine power of the vehicle");
        double enginePower = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy");
        int pharmacyID = READ.nextInt();

        System.out.println("\nMax Battery:\t" + maximumBattery
                + "\nActual Battery:\t" + actualBattery
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
            Logger.getLogger(AdminUI.class.toString()).log(Level.INFO, "The vehicle with the license plate " + licencePlate + " was removed!");
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
            ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler(), new RestockDataHandler());
            pc.addProduct(name, description, price, weight, pharmacyID, stock);
            System.out.println("\n\nProduct Added With Sucess ! Thank you.\n\n");

        }
    }

    private void removeMedicine() {
        ProductController pc = new ProductController(new ProductDataHandler(), new PharmacyDataHandler(), new RestockDataHandler());
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
        uc.removeCourier(id);



    }

    private void addCourier() throws SQLException, IOException {
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


        System.out.println("\nInsert your weight:");
        double weight = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy that you are going to work for");
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

    private void parkDrone (int pharmacyId,Vehicle drone)throws IOException{
       VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());

        if (vc.parkDrone(pharmacyId, drone)) {
            vc.sendEmailNotification(pharmacyId,drone);
            System.out.println("Park Completed");
        } else {
            System.out.println("Park Not completed");
        }
    }

    private void callTimer(String message) {

        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println(message);
            }
        };

        Timer timer = new Timer("Timer");

        timer.schedule(task, 10000);
    }
}
