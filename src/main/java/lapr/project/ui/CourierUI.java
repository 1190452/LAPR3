package lapr.project.ui;

import lapr.project.controller.OrderController;
import lapr.project.controller.VehicleController;
import lapr.project.data.*;
import lapr.project.model.ClientOrder;
import lapr.project.model.Courier;
import lapr.project.model.EletricScooter;
import lapr.project.model.Pharmacy;

import java.util.*;

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
                    OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(), new ClientDataHandler(), new PharmacyDataHandler());
                    LinkedHashMap<Integer, ClientOrder> orderList = c.getUndoneOrders();

                    for (Map.Entry<Integer, ClientOrder> o: orderList.entrySet()){
                        System.out.println(o.getValue().toString());
                    }

                    double weightSum = 0;

                    Courier cour = c.getCourierByEmail(UserSession.getInstance().getUser().getEmail());

                    Pharmacy phar = c.getPharmByID(cour.getPharmacyID());

                    List<ClientOrder> ordersInThisDelivery = new ArrayList<>();

                    boolean decision = true;
                    while(decision && weightSum < cour.getMaxWeightCapacity()){
                        System.out.println("Chose an id of a order you want to deliver");
                        int id=READ.nextInt();

                        weightSum+=orderList.get(id).getFinalWeight();
                        if(!ordersInThisDelivery.contains(orderList.get(id))){
                            ordersInThisDelivery.add(orderList.get(id));
                        }

                        System.out.printf("You still can carry %.1f kilograms\n", cour.getMaxWeightCapacity()-weightSum);
                        System.out.println("Do you want to add another order to this delivery?\n");
                        System.out.println("1-Yes\n");
                        System.out.println("2-No\n");
                        switch (READ.nextInt()){
                            case 1:
                                break;
                            case 2:
                                decision = false;
                                break;
                            default:
                                System.out.println("Insert a valid option");
                        }
                    }

                    c.processDelivery(ordersInThisDelivery, phar);

                    break;
                case "2":
                    VehicleController vc = new VehicleController(new ScooterHandler(), new DeliveryHandler(new DataHandler()), new ParkHandler());
                    System.out.println("Enter your ID");
                    int courierId = READ.nextInt();
                    EletricScooter e = vc.getAvailableScooter(courierId);
                    System.out.println("The scooter license plate picked is: " + e.getLicensePlate());
                    break;
                case "3":
                    vc = new VehicleController(new ScooterHandler(), new DeliveryHandler(new DataHandler()), new ParkHandler());
                    System.out.println("Enter the id of the pharmacy to park");
                    int pharmacyId = READ.nextInt();
                    System.out.println("Enter the id of the scooter to park");
                    String scooterId = READ.next();
                    if(vc.parkScooter(pharmacyId, scooterId)){
                        System.out.println("Parked Completed");
                    }else{
                        System.out.println("Parked Not completed");
                    }
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (!ch.equals("0")) ;
    }
}
