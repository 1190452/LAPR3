package lapr.project.ui;

import lapr.project.controller.OrderController;
import lapr.project.controller.VehicleController;
import lapr.project.data.*;
import lapr.project.model.*;

import java.io.IOException;
import java.util.*;

public class CourierUI {
    private static final Scanner READ = new Scanner(System.in);

    public CourierUI() {
        //Empty constructor
    }

    public static void courierMenu() {
        System.out.println("COURIER MENU\n"
                + "\n1-Pick up Order"
                + "\n0-Exit"
        );
    }

    public void loginCourier() throws IOException {
        String ch;
        do {
            courierMenu();
            ch = READ.next();
            if ("1".equals(ch)) {
                pickUpOrder();
            }
        } while (!ch.equals("0"));
    }

    public void pickUpOrder() throws IOException {

            //PICK UP ORDER
            OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                    new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler(), new RefillStockDataHandler(), new RestockDataHandler(), new ParkHandler());
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

            //TIMER
            callTimer("Delivery concluded...");  //SIMULATION OF THE DELIVERY
            c.updateStatusDelivery(choosen.getId());
            callTimer("Starting to park the scooter...");


            //PARK SCOOTER
            vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
            System.out.println("Enter the id of the pharmacy to park");
            int pharmacyId = READ.nextInt();
            if (vc.parkScooter(pharmacyId, vehicle)) {
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
