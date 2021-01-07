package lapr.project.ui;

import lapr.project.controller.UserController;
import lapr.project.data.UserDataHandler;

import java.util.Scanner;

public class AdminUI {

    public static final Scanner READ = new Scanner(System.in);
    private static final String COURIER_ROLE = "COURIER";

    public static void adminMenu(){
        System.out.println("ADMIN MENU\n"
                +"\n1-Create Pharmacy"
                +"\n2-Add Courier"
                +"\n3-Update Courier"
                +"\n4-Remove Courier"
                +"\n5-Add Eletric Scooter"
                +"\n6-Update Eletric Scooter"
                +"\n7-Remove Eletric Scooter"
                +"\n8-Add Medicine"
                +"\n9-Remove Medicine"
                +"\n0-Exit"
        );
    }

    public void loop() {
        String ch;
        adminMenu();
        ch = READ.nextLine();

        switch(ch){
            case "1":
                //metodo de crição de farmácia
                break;
            case "2":
                addCourier();
                break;
            case "3":
                updateCourier();
                break;
        }

    }

    private void updateCourier() {
        
    }

    private void addCourier() {
        System.out.println("\nInsert your e-mail:");
        String email = READ.nextLine();

        System.out.println("\nInsert your name:");
        String name = READ.nextLine();

        System.out.println("\nInsert your password:");
        String password = READ.nextLine();


        System.out.println("\nInsert your NIF:");
        int nif = READ.nextInt();

        System.out.println("\nInsert your NSS:");
        String nss = READ.nextLine();

        System.out.println("\nInsert the maximum weight capacity of your backpack:");
        double maxWeightCapacity = READ.nextDouble();

        System.out.println("\nInsert your wight:");
        double weight = READ.nextDouble();

        System.out.println("\nInsert the ID of the pharmacy that you are going to work for");
        int pharmacyID = READ.nextInt();
        ;   //acrescentar aqui validacoes mais tarde

        System.out.println("\nUsername:\t" + name
                + "\nE-mail:\t" + email
                + "\nPassword:\t" + password
                + "\nnif:\t" + nif
                + "\nnss:\t" + nss
                + "\nMax Weight Capacity:\t" + maxWeightCapacity
                + "\nWeight:\t" + weight
                + "\nPharmacy ID:\t" + pharmacyID);
        System.out.println("\nPlease confirm the provided information for registration: (Yes/No)");
        String confirmation = READ.nextLine();

        if (confirmation.equalsIgnoreCase("YES")) {
            UserController uc = new UserController(new UserDataHandler());
            uc.addUserAsCourier(name, email, nif, nss, password, maxWeightCapacity, weight, pharmacyID, COURIER_ROLE);
            System.out.println("\n\nWelcome to  Menu " + name + "! Thank you.\n\n");
            adminMenu();
        } else {
            adminMenu();
        }

    }
}
