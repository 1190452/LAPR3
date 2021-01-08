package lapr.project.ui;

import lapr.project.controller.VehicleController;
import lapr.project.model.Courier;
import lapr.project.model.EletricScooter;

import java.util.Scanner;

public class CourierUI {
    private static final Scanner ler = new Scanner(System.in);
    private VehicleController vc;

    public CourierUI(VehicleController vc){
        this.vc = vc;
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
                System.out.println("Enter your ID");
                String courierId = ler.nextLine();
                EletricScooter e = vc.getAvailableScooter(courierId);
                System.out.println("The scooter ID picked is :"+e.getId());
                break;
            case 3:
                System.out.println("Enter the id of the pharmacy to park");
                String pharmacyId = ler.nextLine();
                System.out.println("Enter the id of the pharmacy to park");
                String scooterId = ler.nextLine();
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
