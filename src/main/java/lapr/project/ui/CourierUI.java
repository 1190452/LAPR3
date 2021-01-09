package lapr.project.ui;

import lapr.project.controller.VehicleController;
import lapr.project.data.DataHandler;
import lapr.project.data.DeliveryHandler;
import lapr.project.data.ParkHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.model.EletricScooter;

import java.util.Scanner;

public class CourierUI {
    private static final Scanner READ = new Scanner(System.in);

    public CourierUI(){
        
    }

    public static void courierMenu(){
        System.out.println("COURIER MENU\n"
                +"\n1-Pick up Order"
                +"\n2-Pick up Scooter"
                +"\n3-Park Scooter"
        );
    }

    public void courierLoop() {
        String ch;
        do {
            courierMenu();
            ch = READ.nextLine();

            switch (ch) {
                case "1":
                    //pick order
                    break;
                case "2":
                    VehicleController vc = new VehicleController(new ScooterHandler(), new DeliveryHandler(new DataHandler()), new ParkHandler(new DataHandler()));
                    System.out.println("Enter your ID");
                    String courierId = READ.next();
                    EletricScooter e = vc.getAvailableScooter(courierId);
                    System.out.println("The scooter ID picked is :" + e.getId());
                    courierLoop();
                    break;
                case "3":
                    vc = new VehicleController(new ScooterHandler(), new DeliveryHandler(new DataHandler()), new ParkHandler(new DataHandler()));
                    System.out.println("Enter the id of the pharmacy to park");
                    String pharmacyId = READ.next();
                    System.out.println("Enter the id of the pharmacy to park");
                    String scooterId = READ.next();
                    vc.parkScooter(pharmacyId, scooterId);
                    courierLoop();
                    break;
                default:
                    System.out.println("Invalid option");
                    courierLoop();
                    break;
            }
        } while (!ch.equals("0")) ;
    }
}
