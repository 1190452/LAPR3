package lapr.project.ui;

import lapr.project.controller.OrderController;
import lapr.project.controller.VehicleController;
import lapr.project.data.*;
import lapr.project.model.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class CourierUI {
    private static final Scanner READ = new Scanner(System.in);

    public CourierUI() {
        //Empty constructor
    }

    public static void courierMenu() {
        System.out.println("COURIER MENU\n"
                + "\n1-Pick up Order"
        );
    }

    public void courierLoop() throws IOException {
        String ch;
        do {
            //PICK UO ORDER
            courierMenu();
            ch = READ.nextLine();
            OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                    new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler());
            Courier me = c.getCourierByEmail(UserSession.getInstance().getUser().getEmail());
            List<Delivery> d = c.getDeliverysByCourierId(me.getIdCourier());

            if (d.isEmpty()) {
                System.out.println("You do not have any available delivery.");
                break;
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
                    }
                }
            }

            //PICK UP SCOOTER
            VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
            System.out.println("Enter your ID");
            int courierId = READ.nextInt();
            Vehicle vehicle = vc.getAvailableScooter(courierId, UserSession.getInstance().getUser().getEmail());
            if (vehicle == null) {
                System.out.println("No scooters availables");
            } else {
                System.out.println("The scooter license plate picked is: " + vehicle.getLicensePlate());
            }

            c.sendMailToAllClients(choosen.getId());

            //TIMER
            callTimer("Delivery Created...");  //SIMULATION OF THE DELIVERY
            c.updateStatusDelivery(choosen.getId());
            int dec = 0;
            while (dec != 1) {
                callTimer("Waiting...");
                System.out.println("Do you want to proceed to park the scooter?");
                System.out.println("1-Yes");
                System.out.println("2-No");
                dec = READ.nextInt();
            }

            //PARK SCOOTER
            vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(), new CourierDataHandler(), new PharmacyDataHandler(), new AddressDataHandler());
            System.out.println("Enter the id of the pharmacy to park");
            int pharmacyId = READ.nextInt();
            System.out.println("Enter the licence plate of the scooter to park");
            String scooterLicensePlate = READ.next();
            if (vc.parkDrone(pharmacyId, scooterLicensePlate)) {
                vc.sendEmailNotification(pharmacyId,scooterLicensePlate);
                System.out.println("Park Completed");
            } else {
                System.out.println("Park Not completed");
            }

        } while (!ch.equals("0"));
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
