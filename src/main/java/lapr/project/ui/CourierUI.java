package lapr.project.ui;

import lapr.project.controller.VehicleController;
import lapr.project.data.DataHandler;
import lapr.project.data.DeliveryHandler;
import lapr.project.data.ParkHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.model.Courier;
import lapr.project.model.EletricScooter;

import java.util.Scanner;

public class CourierUI {
    private static final Scanner ler = new Scanner(System.in);

    public CourierUI(){
        
    }

    public static void courierMenu(){
        System.out.println("COURIER MENU\n"
                +"\n1-Pick up Order"
                +"\n2-Pick up Scooter"
                +"\n3-Park Scooter"
        );
    }

    public void loop() {
        courierMenu();
        int opt = ler.nextInt();

        switch (opt) {
                case 1:
                    //pick order
                    break;
                case 2:
                    VehicleController vc = new VehicleController(new ScooterHandler(new DataHandler()),new DeliveryHandler(new DataHandler()),new ParkHandler(new DataHandler()));
                    System.out.println("Enter your ID");
                    String courierId = ler.next();
                    EletricScooter e = vc.getAvailableScooter(courierId);
                    System.out.println("The scooter ID picked is :"+e.getId());
                    break;
                case 3:
                    vc = new VehicleController(new ScooterHandler(new DataHandler()),new DeliveryHandler(new DataHandler()),new ParkHandler(new DataHandler()));
                    System.out.println("Enter the id of the pharmacy to park");
                    String pharmacyId = ler.next();
                    System.out.println("Enter the id of the pharmacy to park");
                    String scooterId = ler.next();
                    vc.parkScooter(pharmacyId,scooterId);
                break;
            case 0:
                System.out.println("VOU EMBORA");
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
}
