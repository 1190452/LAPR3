package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Physics;
import lapr.project.utils.graph.AdjacencyMatrixGraph;
import lapr.project.utils.graph.EdgeAsDoubleGraphAlgorithms;
import lapr.project.utils.graphbase.Graph;
import lapr.project.utils.graphbase.GraphAlgorithmsB;
import oracle.ucp.util.Pair;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController {

    private final ClientOrderHandler clientOrderHandler;
    private final CourierDataHandler courierDataHandler;
    private final AddressDataHandler addressDataHandler;
    private final ClientDataHandler clientDataHandler;
    private final PharmacyDataHandler pharmacyDataHandler;
    private final DeliveryHandler deliveryHandler;
    private final VehicleHandler vehicleHandler;
    private final RefillStockDataHandler refillStockDataHandler;
    private final RestockDataHandler restockDataHandler;
    private final Graph<Address, Double> citygraph;
    private static final double FRONTAL_AREA_ES = 0.65;
    private static final double FRONTAL_AREA_DR = 0.05;
    private static final double WIND_SPEED = 5.0;
    private static final double MIN_WIND_DIRECTION = 0.0;
    private static final double MAX_WIND_DIRECTION = 360.0;
    private static final double MIN_ROAD_RESISTANCE = 0.006;
    private static final double MAX_ROAD_RESISTANCE = 0.02;

    private static final String RESTOCK = "RestockOrder";
    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    public OrderController(ClientOrderHandler clh, CourierDataHandler cdh, AddressDataHandler addressDataHandler, ClientDataHandler clientDataHandler, PharmacyDataHandler pharmacyDataHandler, DeliveryHandler deliveryHandler, VehicleHandler vehicleHandler, RefillStockDataHandler refillStockDataHandler, RestockDataHandler restockDataHandler) {
        this.clientOrderHandler = clh;
        this.courierDataHandler = cdh;
        this.addressDataHandler = addressDataHandler;
        this.clientDataHandler = clientDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
        this.deliveryHandler = deliveryHandler;
        this.vehicleHandler = vehicleHandler;
        this.refillStockDataHandler = refillStockDataHandler;
        this.restockDataHandler = restockDataHandler;
        citygraph = new Graph<>(true);
    }

    public Vehicle createDroneDelivery(List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, double distance, List<Path> pathPairs, double weight, double necessaryEnergy) {

        if (ordersInThisDelivery.isEmpty()) {
            return null;
        }
        List<Vehicle> dronesAvailable = getDronesAvailable(pharmacy.getId(), necessaryEnergy);

        Vehicle droneDelivery;
        if (dronesAvailable.isEmpty()) {
            return null;
        }

        droneDelivery = dronesAvailable.get(0);
        //ver peso
        Delivery d = new Delivery(necessaryEnergy, distance, weight, droneDelivery.getId(), droneDelivery.getLicensePlate());
        int id = deliveryHandler.addDelivery(d);

        for (ClientOrder c : ordersInThisDelivery) {
            updateStatusOrder(id, c.getOrderId());
        }

        Logger.getLogger(OrderController.class.getName()).log(Level.INFO, "Delivery created with sucess!");

        sendMailToAllClients(deliveryHandler.getDeliveryByDroneId(droneDelivery.getId()).getId());
        LOGGER.log(Level.INFO, "Delivery created with sucess!");
        //TIMER
        callTimer("Delivery Created...");  //SIMULATION OF THE DELIVERY
        updateStatusDelivery(id);
        updateStatusVehicle(droneDelivery);
        callTimer("Waiting...");

        return droneDelivery;
    }

    public boolean createDeliveryByScooter(List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, double distance, List<Path> pathPairs, double weight, double necessaryEnergy) {
        List<Courier> couriersAvailable = getAvailableCouriers(pharmacy.getId());

        if (couriersAvailable.isEmpty()) {
            return false;
        }
        Courier deliveryCourier = couriersAvailable.get(0);


        Delivery d = new Delivery(necessaryEnergy, distance, weight, deliveryCourier.getIdCourier(), "");

        int idDelivery = deliveryHandler.addDelivery(d);

        for (ClientOrder c : ordersInThisDelivery) {
            updateStatusOrder(idDelivery, c.getOrderId());
        }

        return true;
    }

    public Pair<LinkedList<Address>, Double> estimateEnergyPathForRestock(List<Address> allAddresses, List<RestockOrder> restocklistToMakeDelivery, List<Path> paths, Pharmacy pharmacy, int typeVehicle, double weight) {
        Graph<Address, Double> graph = buildEnergyGraph(allAddresses, typeVehicle, paths, weight);
        List<Address> addressesToMakeDelivery = new ArrayList<>();
        AdjacencyMatrixGraph<Address, Double> matrix = generateAdjacencyMatrixGraph(graph);

        Address startPoint = getAddressesToMakeDelivery(restocklistToMakeDelivery, allAddresses, addressesToMakeDelivery, pharmacy);

        return shortestPathForDelivery(addressesToMakeDelivery, matrix, startPoint, graph);
    }

    public Pair<LinkedList<Address>, Double> estimateDistancePathForRestock(List<Address> allAddresses, List<RestockOrder> restocklistToMakeDelivery, Pharmacy pharmacy, int typeVehicle) {
        Graph<Address, Double> graph = buildDistanceGraph(allAddresses, typeVehicle);
        ArrayList<Address> addressesToMakeDelivery = new ArrayList<>();
        AdjacencyMatrixGraph<Address, Double> matrix = generateAdjacencyMatrixGraph(graph);

        Address startPoint = getAddressesToMakeDelivery(restocklistToMakeDelivery, allAddresses, addressesToMakeDelivery, pharmacy);

        return shortestPathForDelivery(addressesToMakeDelivery, matrix, startPoint, graph);
    }

    public Address getAddressesToMakeDelivery(List<RestockOrder> restocklistToMakeDelivery, List<Address> allAddresses, List<Address> addressesToMakeDelivery, Pharmacy pharmacy) {
        Address startPoint = null;
        for (RestockOrder co : restocklistToMakeDelivery) {
            Client client = clientDataHandler.getClientByClientOrderID(co.getClientOrderID());
            for (Address add : allAddresses) {
                if (add.getLatitude() == client.getLatitude() && add.getLongitude() == client.getLongitude()) {
                    addressesToMakeDelivery.add(add);
                }
                if (pharmacy.getLatitude() == add.getLatitude() && add.getLongitude() == add.getLongitude()) {
                    startPoint = add;
                }
            }
        }
        addressesToMakeDelivery.add(startPoint);
        return startPoint;
    }

    public Pair<LinkedList<Address>, Double> estimateEnergyPath(List<Address> allAddresses, List<ClientOrder> ordersInThisDelivery, List<Path> paths, Pharmacy pharmacy, int typeVehicle, double weight) {
        Graph<Address, Double> graph = buildEnergyGraph(allAddresses, typeVehicle, paths, weight);
        ArrayList<Address> addressesToMakeDelivery = new ArrayList<>();
        AdjacencyMatrixGraph<Address, Double> matrix = generateAdjacencyMatrixGraph(graph);

        Address startPoint = getAddressesToMakeDelivery2(ordersInThisDelivery, allAddresses, addressesToMakeDelivery, pharmacy);
        return shortestPathForDelivery(addressesToMakeDelivery, matrix, startPoint, graph);
    }

    public Pair<LinkedList<Address>, Double> estimateDistancePath(List<Address> allAddresses, List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, int typeVehicle) {
        Graph<Address, Double> graph = buildDistanceGraph(allAddresses, typeVehicle);
        ArrayList<Address> addressesToMakeDelivery = new ArrayList<>();
        AdjacencyMatrixGraph<Address, Double> matrix = generateAdjacencyMatrixGraph(graph);

        Address startPoint = getAddressesToMakeDelivery2(ordersInThisDelivery, allAddresses, addressesToMakeDelivery, pharmacy);

        return shortestPathForDelivery(addressesToMakeDelivery, matrix, startPoint, graph);
    }

    public Address getAddressesToMakeDelivery2(List<ClientOrder> ordersInThisDelivery, List<Address> allAddresses, List<Address> addressesToMakeDelivery, Pharmacy pharmacy) {
        Address startPoint = null;
        for (ClientOrder co : ordersInThisDelivery) {
            Client client = clientDataHandler.getClientByID(co.getClientId());
            for (Address add : allAddresses) {
                if (add.getLatitude() == client.getLatitude() && add.getLongitude() == client.getLongitude()) {
                    addressesToMakeDelivery.add(add);
                }
                if (add.getLatitude() == pharmacy.getLatitude() && add.getLongitude() == pharmacy.getLongitude()) {
                    startPoint = add;
                }
            }
        }
        addressesToMakeDelivery.add(startPoint);
        return startPoint;
    }

    public Pair<LinkedList<Address>, Double> shortestPathForDelivery(List<Address> addressList, AdjacencyMatrixGraph<Address, Double> matrix, Address startingPoint, Graph<Address, Double> graph) {
        List<Pair<LinkedList<Address>, Double>> permutations = getPermutations(addressList, matrix);
        if (!permutations.isEmpty()) {
            double sum = 0;

            List<Pair<LinkedList<Address>, Double>> permutationsToRemove = new ArrayList<>();
            LinkedList<Address> path = new LinkedList<>();
            LinkedList<Address> auxpath = new LinkedList<>();
            Address next = startingPoint;

            while (!addressList.isEmpty()) {

                double smallestDistance = Double.MAX_VALUE;

                for (Pair<LinkedList<Address>, Double> p : permutations) {
                    if (p.get1st().getFirst().equals(next) && p.get2nd().compareTo(smallestDistance) < 0) {
                        auxpath.clear();
                        auxpath.addAll(p.get1st());
                        smallestDistance = p.get2nd();
                    }
                }

                for (Pair<LinkedList<Address>, Double> p : permutations) {
                    if (p.get1st().getFirst().equals(next) || p.get1st().getLast().equals(next)) {
                        permutationsToRemove.add(p);
                    }
                }

                sum += smallestDistance;

                next = auxpath.getLast();

                addressList.removeAll(auxpath);
                path.addAll(auxpath);
                auxpath.clear();
                permutations.removeAll(permutationsToRemove);
                permutationsToRemove.clear();

            }

            LinkedList<Address> shortPath = new LinkedList<>();
            Address a1 = path.get(path.size() - 1);
            sum += GraphAlgorithmsB.shortestPath(graph, a1, startingPoint, shortPath);
            shortPath.remove(0);
            path.addAll(shortPath);
            return new Pair<>(path, sum);
        }
        return null;
    }

    public List<Pair<LinkedList<Address>, Double>> getPermutations(List<Address> addressList, AdjacencyMatrixGraph<Address, Double> matrix) {
        List<Pair<LinkedList<Address>, Double>> permutations = new ArrayList<>();
        for (Address a1 : addressList) {
            for (Address a2 : addressList) {
                if (!a1.equals(a2)) {
                    LinkedList<Address> path = new LinkedList<>();
                    double distance = EdgeAsDoubleGraphAlgorithms.shortestPath(matrix, a1, a2, path);
                    permutations.add(new Pair<>(path, distance));
                }
            }
        }
        return permutations;
    }

    public Pair<Integer, Vehicle> createRestockRequestByEletricScooter(List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points, double distance, List<Path> pathPairs, double necessaryEnergy) {

        Pharmacy phar = getPharmByID(restocklistToMakeDelivery.get(0).getPharmReceiverID());
        List<Courier> couriersAvailable = getAvailableCouriers(phar.getId());

        if (couriersAvailable.isEmpty()) {
            return null;
        }

        Courier deliveryCourier = couriersAvailable.get(0);

        List<Vehicle> vehicleList = vehicleHandler.getAllScooterAvailables(phar.getId(), necessaryEnergy);
        for (Vehicle vehicle : vehicleList) {
            double actualBattery = vehicle.getActualBattery();
            if (necessaryEnergy < actualBattery) {
                String licensePlate = vehicle.getLicensePlate();
                Park park = vehicleHandler.getParkByPharmacyId(phar.getId(), 1);
                int parkId = park.getId();
                vehicleHandler.updateStatusToBusy(vehicle.getLicensePlate());
                int isCharging = vehicle.getIsCharging();
                if (isCharging == 1) {
                    new ParkHandler().updateActualChargingPlacesA(parkId);
                    vehicleHandler.updateIsChargingN(vehicle.getLicensePlate());
                } else {
                    new ParkHandler().updateActualCapacityA(parkId);
                }


                RefillStock r = new RefillStock(necessaryEnergy, distance, weightSum, deliveryCourier.getIdCourier(), licensePlate);
                return getIntegerVehiclePair(restocklistToMakeDelivery, points, phar, vehicle, r);
            }
        }
        return null;
    }

    private Pair<Integer, Vehicle> getIntegerVehiclePair(List<RestockOrder> restocklistToMakeDelivery, List<Pharmacy> points, Pharmacy phar, Vehicle vehicle, RefillStock r) {
        int idRS = refillStockDataHandler.addRefillStock(r);
        callTimer("Delivery RestockOrder Created...");
        for (Pharmacy p : points) {
            try (FileWriter myWriter = new FileWriter("restockPharmacy.txt")) {
                myWriter.write("Transfer note from pharmacy " + p.getName() + " to pharmacy " + phar.getName());
                myWriter.write("\nThe products you required are ready to be picked up");
                Logger.getLogger(OrderController.class.getName()).log(Level.INFO, "Transfer note issued.");
            } catch (IOException e) {
                Logger.getLogger(OrderController.class.getName()).log(Level.WARNING, "There was an error issuing the transfer note." + e.getMessage());
            }
        }

        callTimer("Delivery Restock Created...");
        for (RestockOrder co : restocklistToMakeDelivery) {
            restockDataHandler.updateStatusRestock(co.getId(), idRS);
            Client c = clientDataHandler.getClientByClientOrderID(co.getClientOrderID());
            EmailAPI.sendMail(c.getEmail(), RESTOCK, "The product(s) that you are waiting for is/are already available. Your products will be delivered soon");
        }

        refillStockDataHandler.updateStatusToDone(idRS);
        int id = phar.getId();
        return new Pair<>(id, vehicle);
    }

    public void updateSatusCourier(int idCourier) {
        courierDataHandler.updateSatusCourier(idCourier);
    }

    public Pair<Integer, Vehicle> createRestockRequestByDrone(List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points, double distance, List<Path> pathPairs, double necessaryEnergy) {
        Pharmacy phar = getPharmByID(restocklistToMakeDelivery.get(0).getPharmReceiverID());

        List<Vehicle> dronesAvailable = vehicleHandler.getDronesAvailable(phar.getId(), necessaryEnergy);

        if (dronesAvailable.isEmpty()) {
            return null;
        } else {

            Vehicle vehicle = dronesAvailable.get(0);

            String licensePlate = vehicle.getLicensePlate();
            Park park = vehicleHandler.getParkByPharmacyId(phar.getId(), 2);
            int parkId = park.getId();
            vehicleHandler.updateStatusToParked(licensePlate);
            int isCharging = vehicle.getIsCharging();
            if (isCharging == 1) {
                new ParkHandler().updateActualChargingPlacesA(parkId);
                vehicleHandler.updateIsChargingN(licensePlate);
            } else {
                new ParkHandler().updateActualCapacityA(parkId);
            }
            vehicleHandler.updateStatusToBusy(licensePlate);

            RefillStock r = new RefillStock(necessaryEnergy, distance, weightSum, 0, licensePlate);
            return getIntegerVehiclePair(restocklistToMakeDelivery, points, phar, vehicle, r);
        }
    }

    private void callTimer(String message) {

        TimerTask task = new TimerTask() {
            public void run() {
                Logger.getLogger(OrderController.class.getName()).log(Level.INFO, message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 10000);
    }

    public boolean updateStatusVehicle(Vehicle v) {
        return vehicleHandler.updateStatusToParked(v.getLicensePlate());
    }

    public boolean updateStatusOrder(int idDelivery, int orderId) {
        return clientOrderHandler.updateStatusOrder(idDelivery, orderId);

    }

    public void sendMailToAllClients(int id) {
        List<String> mails = clientOrderHandler.getClientEmailByDelivery(id);
        for (String mail : mails) {
            EmailAPI.sendDeliveryEmailToClient(mail);
        }
    }

    public List<RestockOrder> getRestockList(int pharmID) {
        return restockDataHandler.getRestockList(pharmID);
    }

    public List<Courier> getAvailableCouriers(int idPhar) {
        return courierDataHandler.getAvailableCouriers(idPhar);

    }

    public List<Delivery> getDeliverysByCourierId(int idCourier) {
        return deliveryHandler.getDeliverysByCourierId(idCourier);
    }

    public List<Pharmacy> getAllPharmacies() {

        return pharmacyDataHandler.getAllPharmacies();

    }

    public List<Vehicle> getDronesAvailable(int idPhar, double necessaryEnergy) {
        return vehicleHandler.getDronesAvailable(idPhar, necessaryEnergy);
    }

    public boolean updateStatusDelivery(int delId) {
        return deliveryHandler.updateStatusDelivery(delId);
    }

    public double getTotalEnergy(double totalWeight, int typeVehicle, double frontalArea, double elevationInitial, double elevationFinal, double latitude1, double latitude2, double longitude1, double longitude2) {
        double linearDistance = Physics.linearDistanceTo(latitude1, latitude2, longitude1, longitude2);
        return Physics.getNecessaryEnergy(Physics.calculateDistanceWithElevation(latitude1, latitude2, longitude1, longitude2, elevationInitial, elevationFinal), totalWeight, typeVehicle, frontalArea, (elevationFinal - elevationInitial), 3, 80, 0.002, linearDistance);
    }

    public double getOrdersWeight(List<ClientOrder> ordersInThisDelivery) {
        double weightSum = 0;

        for (ClientOrder c : ordersInThisDelivery) {
            weightSum += c.getFinalWeight();
        }

        return weightSum;
    }

    public Courier getCourierByEmail(String email) {
        return courierDataHandler.getCourierByEmail(email);
    }

    public Courier getCourierByNIF(double nif) {
        return courierDataHandler.getCourier(nif);
    }

    public Map<Integer, ClientOrder> getUndoneOrders(int pharID) {
        return clientOrderHandler.getUndoneOrders(pharID);
    }

    public Pharmacy getPharmByID(int pharmacyID) {
        return pharmacyDataHandler.getPharmacyByID(pharmacyID);
    }

    public String getCourierEmail() {
        return UserSession.getInstance().getUser().getEmail();
    }

    public List<Address> getAllAddresses() {
        return addressDataHandler.getAllAddresses();
    }

    public List<Path> getAllPathsPairs(List<Address> addresses, List<Path> paths) {
        for (int i = 0; i < addresses.size(); i++) {
            for (int p = 0; p < addresses.size(); p++) {
                if (addresses.get(i) != addresses.get(p)) {
                    Address a1 = addresses.get(i);
                    Address a2 = addresses.get(p);
                    // Path pth = new Path(a2, a1, 0, 0, 0);
                    //if (!paths.contains(pth)) {
                    double windDirection = (Math.random() * (MAX_WIND_DIRECTION - MIN_WIND_DIRECTION) + MIN_WIND_DIRECTION);
                    double roadRRes = (Math.random() * (MAX_ROAD_RESISTANCE - MIN_ROAD_RESISTANCE) + MIN_ROAD_RESISTANCE);
                    paths.add(new Path(a1, a2, roadRRes, WIND_SPEED, windDirection));
                    //}
                }
            }
        }
        return paths;
    }

    public Graph<Address, Double> buildEnergyGraph(List<Address> addresses, int typeVehicle, List<Path> paths, double weight) {
        for (Address add : addresses) {
            citygraph.insertVertex(add);
        }

        if (typeVehicle == 1) {
            for (int i = 0; i < paths.size(); i++) {

                Address add1 = paths.get(i).getA1();
                Address add2 = paths.get(i).getA2();
                double distanceWithElevation = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), add1.getAltitude(), add2.getAltitude());
                double elevationDifference = add2.getAltitude() - add1.getAltitude();
                double linearDistance = Physics.linearDistanceTo(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude());
                elevationDifference = Math.abs(elevationDifference);
                double energy = Physics.getNecessaryEnergy(distanceWithElevation, weight, 1, FRONTAL_AREA_ES, elevationDifference, paths.get(i).getWindspeed(), paths.get(i).getWindDirection(), paths.get(i).getRoadRollingResistance(), linearDistance);
                citygraph.insertEdge(add1, add2, 1.0, energy);
            }
        } else if (typeVehicle == 2) {
            for (int i = 0; i < paths.size(); i++) {
                Address add1 = paths.get(i).getA1();
                Address add2 = paths.get(i).getA2();
                double linearDistance = Physics.linearDistanceTo(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude());
                double distanceWithElevation = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), 0, 0);
                double elevationDifference = 0;
                double energy = Physics.getNecessaryEnergy(distanceWithElevation, weight, 2, FRONTAL_AREA_DR, elevationDifference, paths.get(i).getWindspeed(), paths.get(i).getWindDirection(), paths.get(i).getRoadRollingResistance(), linearDistance);
                citygraph.insertEdge(add1, add2, 1.0, energy);
            }

        }

        return citygraph;
    }

    public Graph<Address, Double> buildDistanceGraph(List<Address> addresses, int typeVehicle) {

        for (Address add : addresses) {
            citygraph.insertVertex(add);
        }

        if (typeVehicle == 1) {
            for (int i = 0; i < addresses.size(); i++) {
                for (int p = 0; p < addresses.size(); p++) {
                    if (!addresses.get(i).equals(addresses.get(p))) {

                        Address add1 = addresses.get(i);
                        Address add2 = addresses.get(p);
                        double distance = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), add1.getAltitude(), add2.getAltitude());
                        citygraph.insertEdge(add1, add2, 1.0, distance);
                    }
                }
            }
        } else if (typeVehicle == 2) {
            for (int i = 0; i < addresses.size(); i++) {
                for (int p = 0; p < addresses.size(); p++) {
                    if (!addresses.get(i).equals(addresses.get(p))) {
                        Address add1 = addresses.get(i);
                        Address add2 = addresses.get(p);
                        double distance = Physics.linearDistanceTo(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude());
                        citygraph.insertEdge(add1, add2, 1.0, distance);
                    }
                }
            }
        }

        return citygraph;
    }

    public AdjacencyMatrixGraph generateAdjacencyMatrixGraph(Graph<Address, Double> graph) {

        AdjacencyMatrixGraph matrizAdjacencias = new AdjacencyMatrixGraph(graph.numVertices());

        for (Address p1 : graph.vertices()) {
            matrizAdjacencias.insertVertex(p1);
        }

        for (Address p1 : graph.vertices()) {
            for (Address p2 : graph.vertices()) {
                if (graph.getEdge(p1, p2) != null && !p1.equals(p2)) {
                    matrizAdjacencias.insertEdge(p1, p2, graph.getEdge(p1, p2).getWeight());
                }
            }
        }

        return matrizAdjacencias;
    }

    public double getNecessaryEnergy(LinkedList<Address> path, double weight, List<Path> pathPairs, int typeVehicle) {
        double necessaryEnergy = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Address add1 = path.get(i);
            Address add2 = path.get(i + 1);
            for (Path paths : pathPairs) {
                if (add1.equals(paths.getA1()) && add2.equals(paths.getA2())) {
                    if (typeVehicle == 2) {
                        double linearDistance = Physics.linearDistanceTo(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude());
                        double distanceWithElevation = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), 0, 0);
                        double elevationDifference = 0;
                        necessaryEnergy += Physics.getNecessaryEnergy(distanceWithElevation, weight, 2, FRONTAL_AREA_DR, elevationDifference, paths.getWindspeed(), paths.getWindDirection(), paths.getRoadRollingResistance(), linearDistance);
                    } else {
                        double linearDistance = Physics.linearDistanceTo(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude());
                        double distanceWithElevation = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), add1.getAltitude(), add2.getAltitude());
                        double elevationDifference = add2.getAltitude()-add1.getAltitude();
                        necessaryEnergy += Physics.getNecessaryEnergy(distanceWithElevation, weight, 1, FRONTAL_AREA_DR, elevationDifference, paths.getWindspeed(), paths.getWindDirection(), paths.getRoadRollingResistance(), linearDistance);
                    }
                }
            }

        }
        return necessaryEnergy;
    }
}




