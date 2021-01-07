package lapr.project.ui;


import lapr.project.controller.AddCourierController;

import java.util.Scanner;

public class AdminUI {

    public static final Scanner read = new Scanner(System.in);


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
        ch = read.nextLine();

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
        boolean confirmation=false;

        String name=null;
        String email=null;
        int NIF=0;
        String socialSecurityNumber=null;
        double maxWeightCapacity=0;
        String pwd=null;

        while(confirmation==false) {


            System.out.println("Introduce your data:\n");
            System.out.println("Name:\n");

            name = read.nextLine();

            System.out.println("Email:\n");

            email = read.nextLine();

            System.out.println("NIF:\n");

            NIF = read.nextInt();

            System.out.println("Social Security Number:\n");

            socialSecurityNumber = read.nextLine();

            System.out.println("Max Weight Capacity:\n");

            maxWeightCapacity = read.nextDouble();

            System.out.println("Password:\n");

            pwd = read.nextLine();

            System.out.println("DATA CONFIRMATION\n"
                    +"\nName: "+name
                    +"\nEmail: "+email
                    +"\nNIF: "+NIF
                    +"\nSocial Security Number: "+socialSecurityNumber
                    +"\nMax weight capacity: "+maxWeightCapacity
                    +"\nDo you Confirm your data?"
                    +"\n1-Confirm"
                    +"\n2-Decline");
            int num=read.nextInt();

            switch(num){
                case 1:
                    confirmation=true;
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Insert a valid number\n");
            }

        }

        AddCourierController addCourCTRL = new AddCourierController();

        addCourCTRL.setNewCourierData(name, email, NIF, socialSecurityNumber, maxWeightCapacity, pwd);


    }
}
