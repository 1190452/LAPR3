package lapr.project.ui;

import lapr.project.controller.OrderController;
import lapr.project.controller.VehicleController;
import lapr.project.data.*;
import lapr.project.model.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourierUI {

    private static final Scanner READ = new Scanner(System.in);

    public CourierUI() {
        //Dummy constructor to be called and have acess to the instance methods of CourierUI
    }

    /**
     * Main menu for the Courier
     */
    public static void courierMenu() {
        System.out.println("COURIER MENU\n"
                + "\n1-Pick up Order"
                + "\n0-Exit"
        );
    }

    /**
     * Menu loop that allows navigation through the different menu options
     *
     * @throws IOException when an I/O exception of some sort has occurred
     */
    public void loginCourier() throws IOException {
        String ch;
        courierMenu();
        ch = READ.nextLine();

        while (!ch.equals("0")) {
            switch (ch) {
                case "1":
                    pickUpOrder();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    break;
            }
            courierMenu();
            ch = READ.nextLine();
        }
    }

    /**
     * @throws IOException when an I/O exception of some sort has occurred
     */
    public void pickUpOrder() throws IOException {

        //PICK UP ORDER
        OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler(), new PathDataHandler(), new ProductDataHandler());
        Courier me = c.getCourierByEmail(UserSession.getInstance().getUser().getEmail());
        List<Delivery> d = c.getDeliverysByCourierId(me.getIdCourier());

        if (d.isEmpty()) {
            System.out.println("You do not have any available delivery.");
            loginCourier();
        }

        Delivery choosen = null;
        while (choosen == null) {
            for (Delivery deliv : d) {
                System.out.println(deliv.toString());
            }
            System.out.println("Insert an ID of a Delivery");
            int id = READ.nextInt();
            READ.nextLine();

            for (Delivery deliv2 : d) {
                if (deliv2.getId() == id) {
                    choosen = deliv2;
                    c.updateSatusCourier(me.getIdCourier());
                }
            }
        }


        //PICK UP SCOOTER
        VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
        Vehicle vehicle = vc.getAvailableScooter(me.getIdCourier(), UserSession.getInstance().getUser().getEmail());
        if (vehicle == null) {
            System.out.println("No scooters availables");
            loginCourier();
        } else {
            System.out.println("The scooter license plate picked is: " + vehicle.getLicensePlate());
        }

        c.sendMailToAllClients(choosen.getId());

        Logger.getLogger(Courier.class.getName()).log(Level.INFO, "Delivery Concluded!");
        c.updateStatusDelivery(choosen.getId());

        List<Address> path = c.importPathFromFile(choosen.getId(), 1);

        if (path.get(0).equals(path.get(path.size() - 1))) {
            Logger.getLogger(Courier.class.getName()).log(Level.INFO, "Starting to park the scooter...");
            //PARK SCOOTER
            vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
            System.out.println("Enter the id of the pharmacy to park");
            int pharmacyId = READ.nextInt();
            READ.nextLine();

            if (pharmacyId != 0 && vehicle != null) {
                if (vc.parkScooter(pharmacyId, vehicle)) {
                    System.out.println("Park Completed");
                } else {
                    System.out.println("Park Not completed");
                }
            } else {
                Logger.getLogger(CourierUI.class.getName()).log(Level.INFO, "You cannot park the scooter because you cannot reach any park");
            }
        }
    }

}
