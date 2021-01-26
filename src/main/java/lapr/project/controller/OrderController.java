package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Physics;
import lapr.project.utils.graph.AdjacencyMatrixGraph;
import lapr.project.utils.graph.EdgeAsDoubleGraphAlgorithms;
import lapr.project.utils.graphbase.Graph;
import lapr.project.utils.graphbase.GraphAlgorithms;
import oracle.ucp.util.Pair;

import java.io.*;
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
    private final ParkHandler parkHandler;
    private final PathDataHandler pathDataHandler;
    private static final double FRONTAL_AREA_ES = 0.65;
    private static final double FRONTAL_AREA_DR = 0.05;


    private static final String RESTOCK = "RestockOrder";
    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    /**
     * contructor that iniciatesall the handlers that are needed int his class
     *
     * @param clh
     * @param cdh
     * @param addressDataHandler
     * @param clientDataHandler
     * @param pharmacyDataHandler
     * @param deliveryHandler
     * @param vehicleHandler
     * @param refillStockDataHandler
     * @param restockDataHandler
     * @param parkHandler
     * @param pathDataHandler
     */
    public OrderController(ClientOrderHandler clh, CourierDataHandler cdh, AddressDataHandler addressDataHandler, ClientDataHandler clientDataHandler,
                           PharmacyDataHandler pharmacyDataHandler, DeliveryHandler deliveryHandler, VehicleHandler vehicleHandler,
                           RefillStockDataHandler refillStockDataHandler, RestockDataHandler restockDataHandler, ParkHandler parkHandler, PathDataHandler pathDataHandler) {
        this.clientOrderHandler = clh;
        this.courierDataHandler = cdh;
        this.addressDataHandler = addressDataHandler;
        this.clientDataHandler = clientDataHandler;
        this.pharmacyDataHandler = pharmacyDataHandler;
        this.deliveryHandler = deliveryHandler;
        this.vehicleHandler = vehicleHandler;
        this.refillStockDataHandler = refillStockDataHandler;
        this.restockDataHandler = restockDataHandler;
        this.parkHandler = parkHandler;
        this.pathDataHandler = pathDataHandler;
        citygraph = new Graph<>(true);
    }

    /**
     * method to create drone delivery
     *
     * @param ordersInThisDelivery
     * @param pharmacy
     * @param distance
     * @param weight
     * @param necessaryEnergy
     * @return pair that has the vehicle and the delivery id
     */
    public Pair<Vehicle, Integer> createDroneDelivery(List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, double distance, double weight, double necessaryEnergy) {

        if (ordersInThisDelivery.isEmpty()) {
            return null;
        }
        Vehicle droneDelivery = getDronesAvailable(pharmacy.getId(), necessaryEnergy);

        //ver peso
        Delivery d = new Delivery(necessaryEnergy, distance, weight, 0, droneDelivery.getLicensePlate());
        int idDelivery = deliveryHandler.addDelivery(d);

        associateVehicleToDelivery(idDelivery, droneDelivery.getLicensePlate());

        for (ClientOrder c : ordersInThisDelivery) {
            updateStatusOrder(idDelivery, c.getOrderId());
        }

        Logger.getLogger(OrderController.class.getName()).log(Level.INFO, "Delivery created with sucess!");

        sendMailToAllClients(deliveryHandler.getDeliveryByDroneId(droneDelivery.getId()).getId());
        LOGGER.log(Level.INFO, "Delivery created with sucess!");
        //TIMER
        //callTimer("Delivery Created...");  //SIMULATION OF THE DELIVERY
        //callTimer("Waiting...");
        //callTimer("Delivery concluded...");
        LOGGER.log(Level.INFO, "Delivery Created...");
        LOGGER.log(Level.INFO, "Waiting...");
        LOGGER.log(Level.INFO, "Delivery concluded...");
        updateStatusDelivery(idDelivery);

        return new Pair<>(droneDelivery, idDelivery);
    }

    /**
     * method to create delivery by scooter
     *
     * @param ordersInThisDelivery
     * @param pharmacy
     * @param distance
     * @param weight
     * @param necessaryEnergy
     * @return id of the delivery
     */
    public int createDeliveryByScooter(List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, double distance, double weight, double necessaryEnergy) {
        List<Courier> couriersAvailable = getAvailableCouriers(pharmacy.getId());

        if (couriersAvailable.isEmpty()) {
            return 0;
        }
        Courier deliveryCourier = couriersAvailable.get(0);


        Delivery d = new Delivery(necessaryEnergy, distance, weight, deliveryCourier.getIdCourier(), "");

        int idDelivery = deliveryHandler.addDelivery(d);

        for (ClientOrder c : ordersInThisDelivery) {
            updateStatusOrder(idDelivery, c.getOrderId());
        }

        return idDelivery;
    }


    /**
     * method that estimates the cost of the path for restock
     *
     * @param allAddresses
     * @param restocklistToMakeDelivery
     * @param pharmacy
     * @param typeVehicle
     * @param paths
     * @param weight
     * @return pair with all the addresses and distance
     */
    public Pair<LinkedList<Address>, Double> estimateCostPathForRestock(List<Address> allAddresses, List<RestockOrder> restocklistToMakeDelivery, Pharmacy pharmacy, int typeVehicle, List<Path> paths, double weight) {
        Graph<Address, Double> graph;
        if (weight == 0) {
            graph = buildDistanceGraph(allAddresses, typeVehicle, paths);
        } else {
            graph = buildEnergyGraph(allAddresses, typeVehicle, paths, weight);
        }
        ArrayList<Address> addressesToMakeDelivery = new ArrayList<>();
        AdjacencyMatrixGraph<Address, Double> matrix = generateAdjacencyMatrixGraph(graph);

        Address startPoint = getAddressesToMakeRestock(restocklistToMakeDelivery, allAddresses, addressesToMakeDelivery, pharmacy);

        for(Address add : addressesToMakeDelivery){
            if(graph.inDegree(add) == 0 || graph.outDegree(add) == 0){
                return null;
            }
        }

        return shortestPathForDelivery(addressesToMakeDelivery, matrix, startPoint, graph);
    }

    /**
     * method that return address to make restock
     *
     * @param restocklistToMakeDelivery
     * @param allAddresses
     * @param addressesToMakeDelivery
     * @param pharmacy
     * @return address
     */
    public Address getAddressesToMakeRestock(List<RestockOrder> restocklistToMakeDelivery, List<Address> allAddresses, List<Address> addressesToMakeDelivery, Pharmacy pharmacy) {
        Address startPoint = null;

        for (RestockOrder co : restocklistToMakeDelivery) {
            Pharmacy phar = getPharmByID(co.getPharmSenderID());
            for (Address add : allAddresses) {
                if (add.getLatitude() == phar.getLatitudePharmacy() && add.getLongitude() == phar.getLongitudePharmacy()) {
                    addressesToMakeDelivery.add(add);
                }
                if (pharmacy.getLatitudePharmacy() == add.getLatitude() && add.getLongitude() == add.getLongitude()) {
                    startPoint = add;
                }
            }
        }
        addressesToMakeDelivery.add(startPoint);
        return startPoint;
    }


    /**
     * method to estimate the cost of the path for the delivery
     *
     * @param allAddresses
     * @param ordersInThisDelivery
     * @param pharmacy
     * @param typeVehicle
     * @param paths
     * @param weight
     * @return pair with addresses and distance
     */
    public Pair<LinkedList<Address>, Double> estimateCostPathForDelivery(List<Address> allAddresses, List<ClientOrder> ordersInThisDelivery, Pharmacy pharmacy, int typeVehicle, List<Path> paths, double weight) {
        Graph<Address, Double> graph;
        if (weight == 0) {
            graph = buildDistanceGraph(allAddresses, typeVehicle, paths);
        } else {
            graph = buildEnergyGraph(allAddresses, typeVehicle, paths, weight);
        }
        ArrayList<Address> addressesToMakeDelivery = new ArrayList<>();

        AdjacencyMatrixGraph<Address, Double> matrix = generateAdjacencyMatrixGraph(graph);

        Address startPoint = getAddressesToMakeDelivery(ordersInThisDelivery, allAddresses, addressesToMakeDelivery, pharmacy);

        for(Address add : addressesToMakeDelivery){
            if(graph.inDegree(add) == 0 || graph.outDegree(add) == 0){
                return null;
            }
        }

        return shortestPathForDelivery(addressesToMakeDelivery, matrix, startPoint, graph);
    }

    /**
     * method the address to make the delivery
     *
     * @param ordersInThisDelivery
     * @param allAddresses
     * @param addressesToMakeDelivery
     * @param pharmacy
     * @return address
     */
    public Address getAddressesToMakeDelivery(List<ClientOrder> ordersInThisDelivery, List<Address> allAddresses, List<Address> addressesToMakeDelivery, Pharmacy pharmacy) {
        Address startPoint = null;
        for (ClientOrder co : ordersInThisDelivery) {
            Client client = clientDataHandler.getClientByID(co.getClientId());
            for (Address add : allAddresses) {
                if (add.getLatitude() == client.getLatitude() && add.getLongitude() == client.getLongitude()) {
                    addressesToMakeDelivery.add(add);
                }
                if (add.getLatitude() == pharmacy.getLatitudePharmacy() && add.getLongitude() == pharmacy.getLongitudePharmacy()) {
                    startPoint = add;
                }
            }
        }
        addressesToMakeDelivery.add(startPoint);
        return startPoint;
    }

    /**
     * method that calculates the shortest path for a given address list
     *
     * @param addressList
     * @param matrix
     * @param startingPoint
     * @param graph
     * @return pair with addresses and distance
     */
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
                if (!path.isEmpty() && auxpath.getFirst().equals(path.getLast())) {
                    path.removeLast();
                }
                path.addAll(auxpath);
                auxpath.clear();
                permutations.removeAll(permutationsToRemove);
                permutationsToRemove.clear();

            }
            LinkedList<Address> auxiliarPath = new LinkedList<>();
            sum += GraphAlgorithms.shortestPath(graph, path.getLast(), startingPoint, auxiliarPath);
            if (!auxiliarPath.isEmpty()) {
                auxiliarPath.removeFirst();
                path.addAll(auxiliarPath);
            }
            return new Pair<>(path, sum);
        }
        return null;
    }

    /**
     * method that return permutations
     *
     * @param addressList
     * @param matrix
     * @return list of pairs of the addresseses and distance
     */
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

    /**
     * method that creates a restock request by eletric scooter
     *
     * @param restocklistToMakeDelivery
     * @param weightSum
     * @param points
     * @param distance
     * @param necessaryEnergy
     * @param idRestock
     * @return pair with id of pharmacy and vehicle
     */
    public Pair<Integer, Vehicle> createRestockRequestByEletricScooter(List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points, double distance, double necessaryEnergy, int idRestock) {

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
                Park park = parkHandler.getParkByPharmacyId(phar.getId(), 1);
                int parkId = park.getId();
                vehicleHandler.updateStatusToBusy(vehicle.getLicensePlate());
                int isCharging = vehicle.getIsCharging();
                if (isCharging == 1) {
                    parkHandler.updateActualChargingPlacesA(parkId);
                    vehicleHandler.updateIsChargingN(vehicle.getLicensePlate());
                } else {
                    new ParkHandler().updateActualCapacityA(parkId);
                }


                RefillStock r = new RefillStock(necessaryEnergy, distance, weightSum, deliveryCourier.getIdCourier(), licensePlate);
                return getIntegerVehiclePair(restocklistToMakeDelivery, points, phar, vehicle, r, idRestock);
            }
        }
        return null;
    }

    /**
     * method that creates a restock request by drone
     *
     * @param restocklistToMakeDelivery
     * @param weightSum
     * @param points
     * @param distance
     * @param necessaryEnergy
     * @param idRestock
     * @return pair with id of pharmacy and vehicle
     */
    public Pair<Integer, Vehicle> createRestockRequestByDrone(List<RestockOrder> restocklistToMakeDelivery, double weightSum, List<Pharmacy> points, double distance, double necessaryEnergy, int idRestock) {
        Pharmacy phar = getPharmByID(restocklistToMakeDelivery.get(0).getPharmReceiverID());

        List<Vehicle> dronesAvailable = vehicleHandler.getDronesAvailable(phar.getId(), necessaryEnergy);

        if (dronesAvailable.isEmpty()) {
            return null;
        } else {

            Vehicle vehicle = dronesAvailable.get(0);

            String licensePlate = vehicle.getLicensePlate();
            Park park = parkHandler.getParkByPharmacyId(phar.getId(), 2);
            int parkId = park.getId();
            vehicleHandler.updateStatusToParked(licensePlate);
            int isCharging = vehicle.getIsCharging();
            if (isCharging == 1) {
                parkHandler.updateActualChargingPlacesA(parkId);
                vehicleHandler.updateIsChargingN(licensePlate);
            } else {
                parkHandler.updateActualCapacityA(parkId);

            }
            vehicleHandler.updateStatusToBusy(licensePlate);

            RefillStock r = new RefillStock(necessaryEnergy, distance, weightSum, 0, licensePlate);
            return getIntegerVehiclePair(restocklistToMakeDelivery, points, phar, vehicle, r, idRestock);
        }
    }

    /**
     * method that return a asociation of a vehicle and a pharmacy
     *
     * @param restocklistToMakeDelivery
     * @param points
     * @param phar
     * @param vehicle
     * @param r
     * @param idRestock
     * @return pair with id of the pharmacy and the vehicle
     */
    private Pair<Integer, Vehicle> getIntegerVehiclePair(List<RestockOrder> restocklistToMakeDelivery, List<Pharmacy> points, Pharmacy phar, Vehicle vehicle, RefillStock r, int idRestock) {
        int idRS = refillStockDataHandler.addRefillStock(r);
        idRestock = idRS;
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

    /**
     * method that updates the status of the courier
     *
     * @param idCourier
     */
    public void updateSatusCourier(int idCourier) {
        courierDataHandler.updateSatusCourier(idCourier);
    }

    /**
     * method to call timer
     *
     * @param message
     */
    private void callTimer(String message) {

        TimerTask task = new TimerTask() {
            public void run() {
                Logger.getLogger(OrderController.class.getName()).log(Level.INFO, message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 10000);
    }

    /**
     * method that updates the status of the vehicle
     *
     * @param v
     * @return boolean that informs the success of the operation
     */
    public boolean updateStatusVehicle(Vehicle v) {
        return vehicleHandler.updateStatusToParked(v.getLicensePlate());
    }

    /**
     * method to update status of the order
     *
     * @param idDelivery
     * @param orderId
     * @return boolean that informs the success of the operation
     */
    public boolean updateStatusOrder(int idDelivery, int orderId) {
        return clientOrderHandler.updateStatusOrder(idDelivery, orderId);

    }

    /**
     * method to send email to all clients
     *
     * @param id
     */
    public void sendMailToAllClients(int id) {
        List<String> mails = clientOrderHandler.getClientEmailByDelivery(id);
        for (String mail : mails) {
            EmailAPI.sendDeliveryEmailToClient(mail);
        }
    }

    /**
     * ~method to get all restocks
     *
     * @param pharmID
     * @return list restocks
     */
    public List<RestockOrder> getRestockList(int pharmID) {
        return restockDataHandler.getRestockList(pharmID);
    }

    /**
     * method to get available couriers
     *
     * @param idPhar
     * @return list of couriers
     */
    public List<Courier> getAvailableCouriers(int idPhar) {
        return courierDataHandler.getAvailableCouriers(idPhar);
    }

    /**
     * get all deliveries of this courier
     *
     * @param idCourier
     * @return list of deliveries
     */
    public List<Delivery> getDeliverysByCourierId(int idCourier) {
        return deliveryHandler.getDeliverysByCourierId(idCourier);
    }

    /**
     * method that returns all the pharmacies in the system
     *
     * @return list of pharmacies
     */
    public List<Pharmacy> getAllPharmacies() {

        return pharmacyDataHandler.getAllPharmacies();

    }

    public List<Vehicle> getDronesFree(int idPhar, double necessaryEnergy){
        return vehicleHandler.getDronesAvailable(idPhar, necessaryEnergy);
    }
    /**
     * get all drones available
     *
     * @param idPhar
     * @param necessaryEnergy
     * @return vehicle
     */
    public Vehicle getDronesAvailable(int idPhar, double necessaryEnergy) {
        List<Vehicle> vehicleList = getDronesFree(idPhar,necessaryEnergy);
        for (Vehicle vehicle : vehicleList) {
            double actualBattery = vehicle.getActualBattery();
            if (necessaryEnergy < actualBattery) {
                String licensePlate = vehicle.getLicensePlate();
                Park park = parkHandler.getParkByPharmacyId(idPhar, 2);
                int parkId = park.getId();
                vehicleHandler.updateStatusToBusy(licensePlate);
                int isCharging = vehicle.getIsCharging();
                if (isCharging == 1) {
                    parkHandler.updateActualChargingPlacesA(parkId);
                    vehicleHandler.updateIsChargingN(licensePlate);
                } else {
                    parkHandler.updateActualCapacityA(parkId);
                }
                return vehicle;
            }
        }
        return null;
    }

    /**
     * method that updates the status of the delivery
     *
     * @param delId
     * @return boolean that informs the success of the operation
     */
    public boolean updateStatusDelivery(int delId) {
        return deliveryHandler.updateStatusDelivery(delId);
    }

    /**
     * method that return the total energy
     *
     * @param totalWeight
     * @param typeVehicle
     * @param frontalArea
     * @param elevationInitial
     * @param elevationFinal
     * @param latitude1
     * @param latitude2
     * @param longitude1
     * @param longitude2
     * @return boolean that informs the success of the operation
     */
    public double getTotalEnergy(double totalWeight, int typeVehicle, double frontalArea, double elevationInitial, double elevationFinal, double latitude1, double latitude2, double longitude1, double longitude2) {
        double linearDistance = Physics.linearDistanceTo(latitude1, latitude2, longitude1, longitude2);
        return Physics.getNecessaryEnergy(Physics.calculateDistanceWithElevation(latitude1, latitude2, longitude1, longitude2, elevationInitial, elevationFinal), totalWeight, typeVehicle, frontalArea, (elevationFinal - elevationInitial), 3, 80, 0.002, linearDistance);
    }

    /**
     * method to get all oders weight
     *
     * @param ordersInThisDelivery
     * @return weight
     */
    public double getOrdersWeight(List<ClientOrder> ordersInThisDelivery) {
        double weightSum = 0;

        for (ClientOrder c : ordersInThisDelivery) {
            weightSum += c.getFinalWeight();
        }

        return weightSum;
    }

    /**
     * method to return courier by email
     *
     * @param email
     * @return courier
     */
    public Courier getCourierByEmail(String email) {
        return courierDataHandler.getCourierByEmail(email);
    }

    /**
     * method that return courier by nif
     *
     * @param nif
     * @return courier
     */
    public Courier getCourierByNIF(double nif) {
        return courierDataHandler.getCourier(nif);
    }

    /**
     * method that returns all undone orders
     *
     * @param pharID
     * @return map where key is order id and value is client order
     */
    public Map<Integer, ClientOrder> getUndoneOrders(int pharID) {
        return clientOrderHandler.getUndoneOrders(pharID);
    }

    /**
     * method that returns pharmacy by its id
     *
     * @param pharmacyID
     * @return pharmacy
     */
    public Pharmacy getPharmByID(int pharmacyID) {
        return pharmacyDataHandler.getPharmacyByID(pharmacyID);
    }

    /**
     * method to get couriers email
     *
     * @return email
     */
    public String getCourierEmail() {
        return UserSession.getInstance().getUser().getEmail();
    }

    /**
     * method to return all addresses
     *
     * @return list of address
     */
    public List<Address> getAllAddresses() {
        return addressDataHandler.getAllAddresses();
    }

    /**
     * method to build the graph where the edge value is the energy
     *
     * @param addresses
     * @param typeVehicle
     * @param paths
     * @param weight
     * @return graph
     */
    public Graph<Address, Double> buildEnergyGraph(List<Address> addresses, int typeVehicle, List<Path> paths, double weight) {
        for (Address add : addresses) {
            citygraph.insertVertex(add);
        }

        if (typeVehicle == 1) {
            for (Path path : paths) {
                double distanceWithElevation = Physics.calculateDistanceWithElevation(path.getLatitudeFrom(), path.getLatitudeTo(), path.getLongitudeFrom(), path.getLongitudeTo(), path.getAltitudeFrom(), path.getAltitudeTo());
                double elevationDifference = path.getAltitudeTo() - path.getAltitudeFrom();
                double linearDistance = Physics.linearDistanceTo(path.getLatitudeFrom(), path.getLatitudeTo(), path.getLongitudeFrom(), path.getLongitudeTo());
                elevationDifference = Math.abs(elevationDifference);
                double energy = Physics.getNecessaryEnergy(distanceWithElevation, weight, 1, FRONTAL_AREA_ES, elevationDifference, path.getWindspeed(), path.getWindDirection(), path.getRoadRollingResistance(), linearDistance);
                Address add1 = null;
                Address add2 = null;
                for (Address add : addresses) {
                    if (add.getAltitude() == path.getAltitudeFrom() && add.getLongitude() == path.getLongitudeFrom() && add.getLatitude() == path.getLatitudeFrom()) {
                        add1 = add;
                    }
                    if (add.getAltitude() == path.getAltitudeTo() && add.getLongitude() == path.getLongitudeTo() && add.getLatitude() == path.getLatitudeTo()) {
                        add2 = add;
                    }
                }
                citygraph.insertEdge(add1, add2, 1.0, energy);
            }
        } else if (typeVehicle == 2) {
            for (Path path : paths) {

                double linearDistance = Physics.linearDistanceTo(path.getLatitudeFrom(), path.getLatitudeTo(), path.getLongitudeFrom(), path.getLongitudeTo());
                double distanceWithElevation = Physics.calculateDistanceWithElevation(path.getLatitudeFrom(), path.getLatitudeTo(), path.getLongitudeFrom(), path.getLongitudeTo(), 0, 0);
                double elevationDifference = 0;
                double energy = Physics.getNecessaryEnergy(distanceWithElevation, weight, 2, FRONTAL_AREA_DR, elevationDifference, path.getWindspeed(), path.getWindDirection(), path.getRoadRollingResistance(), linearDistance);
                Address add1 = null;
                Address add2 = null;
                for (Address add : addresses) {
                    if (add.getAltitude() == path.getAltitudeFrom() && add.getLongitude() == path.getLongitudeFrom() && add.getLatitude() == path.getLatitudeFrom()) {
                        add1 = add;
                    }
                    if (add.getAltitude() == path.getAltitudeTo() && add.getLongitude() == path.getLongitudeTo() && add.getLatitude() == path.getLatitudeTo()) {
                        add2 = add;
                    }
                }
                citygraph.insertEdge(add1, add2, 1.0, energy);
            }

        }

        return citygraph;
    }

    /**
     * method to build the graph where the edge value is the distance
     *
     * @param addresses
     * @param typeVehicle
     * @param paths
     * @return graph
     */
    public Graph<Address, Double> buildDistanceGraph(List<Address> addresses, int typeVehicle, List<Path> paths) {

        for (Address add : addresses) {
            citygraph.insertVertex(add);
        }
            for (Path path : paths) {
                Address add1 = null;
                Address add2 = null;
                for (Address add : addresses) {
                    if (add.getAltitude() == path.getAltitudeFrom() && add.getLongitude() == path.getLongitudeFrom() && add.getLatitude() == path.getLatitudeFrom()) {
                        add1 = add;
                    }
                    if (add.getAltitude() == path.getAltitudeTo() && add.getLongitude() == path.getLongitudeTo() && add.getLatitude() == path.getLatitudeTo()) {
                        add2 = add;
                    }
                }
                assert add1 != null;
                assert add2 != null;
                double distance = 0;
                if(typeVehicle == 1) {
                    distance = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), add1.getAltitude(), add2.getAltitude());
                }else if(typeVehicle == 2){
                    distance = Physics.linearDistanceTo(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude());
                }
                citygraph.insertEdge(add1, add2, 1.0, distance);
            }

        return citygraph;
    }

    /**
     * method that generates the adjacency matrix
     *
     * @param graph
     * @return adjacency matrix
     */
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

    /**
     * method that returns necessary energy to make a certain path
     *
     * @param pathToMakeDelivery
     * @param weight
     * @param pathPairs
     * @param typeVehicle
     * @return energy
     */
    public double getNecessaryEnergy(List<Address> pathToMakeDelivery, double weight, List<Path> pathPairs, int typeVehicle) {
        double necessaryEnergy = 0;
        for (int i = 0; i < pathToMakeDelivery.size() - 1; i++) {
            Address add1 = pathToMakeDelivery.get(i);
            Address add2 = pathToMakeDelivery.get(i + 1);
            for (Path paths : pathPairs) {
                if (add1.getAltitude() == paths.getAltitudeFrom() && add1.getLongitude() == paths.getLongitudeFrom() && add1.getLatitude() == paths.getLatitudeFrom()
                        && add2.getAltitude() == paths.getAltitudeTo() && add2.getLongitude() == paths.getLongitudeTo() && add2.getLatitude() == paths.getLatitudeTo()) {
                    if (typeVehicle == 2) {
                        double linearDistance = Physics.linearDistanceTo(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude());
                        double distanceWithElevation = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), 0, 0);
                        double elevationDifference = 0;
                        necessaryEnergy += Physics.getNecessaryEnergy(distanceWithElevation, weight, 2, FRONTAL_AREA_DR, elevationDifference, paths.getWindspeed(), paths.getWindDirection(), paths.getRoadRollingResistance(), linearDistance);
                    } else {
                        double linearDistance = Physics.linearDistanceTo(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude());
                        double distanceWithElevation = Physics.calculateDistanceWithElevation(add1.getLatitude(), add2.getLatitude(), add1.getLongitude(), add2.getLongitude(), add1.getAltitude(), add2.getAltitude());
                        double elevationDifference = add2.getAltitude() - add1.getAltitude();
                        necessaryEnergy += Physics.getNecessaryEnergy(distanceWithElevation, weight, 1, FRONTAL_AREA_DR, elevationDifference, paths.getWindspeed(), paths.getWindDirection(), paths.getRoadRollingResistance(), linearDistance);
                    }
                }
            }

        }
        return necessaryEnergy;
    }

    /**
     * method that associates vehicle to delivery
     *
     * @param deliveryId
     * @param licensePlate
     * @return boolean that informs of the success of the operation
     */
    public boolean associateVehicleToDelivery(int deliveryId, String licensePlate) {
        return vehicleHandler.associateVehicleToDelivery(deliveryId, licensePlate);
    }

    /**
     * method to get all paths in db
     *
     * @return list of paths
     */
    public List<Path> getAllPaths(int typePath) {
        return pathDataHandler.getAllPaths(typePath);
    }

    /**
     * method to add new path to db
     *
     * @param latitude1
     * @param longitude1
     * @param altitude1
     * @param latitude2
     * @param longitude2
     * @param altitude2
     * @param roadrollingresistance
     * @param windDirection
     * @param windSpeed
     * @return boolean that informs of the success of the operation
     */
    public boolean addPath(double latitude1, double longitude1, double altitude1, double latitude2, double longitude2, double altitude2, double roadrollingresistance, double windDirection, double windSpeed, int pathType) {
        Path p = new Path(latitude1, longitude1, altitude1, latitude2, longitude2, altitude2, roadrollingresistance, windDirection, windSpeed, pathType);
        return pathDataHandler.addPath(p);
    }

    public List<Address> importPathFromFile(int id, int idTypeDeliveryOrRestock) {
        String fileName = id + "-" + idTypeDeliveryOrRestock + ".txt";
        List<Address> path = new ArrayList<>();
        String line = "";
        String txtSplit = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {


            while ((line = br.readLine()) != null) {

                String[] pathInformation = line.split(txtSplit);
                double latitude = Double.parseDouble(pathInformation[0]);
                double longitude = Double.parseDouble(pathInformation[1]);
                String street = pathInformation[2].trim();
                int doorNum = Integer.parseInt(pathInformation[3]);
                String zipCode = pathInformation[4].trim();
                String locality = pathInformation[5].trim();
                double altitude = Double.parseDouble(pathInformation[6]);


                path.add(new Address(latitude, longitude, street, doorNum, zipCode, locality, altitude));
            }
            return path;


        } catch (IOException e) {
            Logger.getLogger(OrderController.class.getName()).log(Level.WARNING, e.getMessage());
        }
        return new ArrayList<>();
    }

    public boolean removeFile(int id, int idTypeDeliveryOrRestock) {
        String currentDir = System.getProperty("user.dir") + id + "-" + idTypeDeliveryOrRestock + ".txt";
        File dirFile = new File(currentDir);
        File fileToRemove = new File(dirFile.getAbsolutePath());
        if (fileToRemove.delete()) {
            LOGGER.log(Level.INFO, "File Removed : " + dirFile.getName());
            return true;
        }
        return false;
    }
}




