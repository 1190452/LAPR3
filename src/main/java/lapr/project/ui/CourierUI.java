package lapr.project.ui;

import lapr.project.controller.OrderController;
import lapr.project.controller.VehicleController;
import lapr.project.data.*;
import lapr.project.model.*;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.*;

public class CourierUI {
    private static final Scanner READ = new Scanner(System.in);

    public CourierUI(){
        //Empty constructor
    }

    public static void courierMenu(){
        System.out.println("COURIER MENU\n"
                +"\n1-Pick up Order"
                +"\n2-Pick up Scooter"
                +"\n3-Park Scooter"     //TODO Park done????
        );
    }

    public void courierLoop() throws IOException {
        String ch;
        do {
            courierMenu();
            ch = READ.nextLine();

            switch (ch) {
                case "1":
                    OrderController c = new OrderController(new ClientOrderHandler(), new CourierDataHandler(), new AddressDataHandler(),
                            new ClientDataHandler(), new PharmacyDataHandler(), new DeliveryHandler(), new VehicleHandler());
                    Courier me = c.getCourierByEmail(UserSession.getInstance().getUser().getEmail());
                    List<Delivery> d = c.getDeliverysByCourierId(me.getIdCourier());

                    if(d.isEmpty()){
                        System.out.println("You do not have any available delivery.");
                        break;
                    }

                    Delivery choosen = null;
                    while(choosen == null){
                        for(Delivery deliv: d){
                            System.out.println(deliv.toString());
                        }

                        System.out.println("Insert an ID of a Delivery");

                        int id=READ.nextInt();

                        for(Delivery deliv2: d){
                            if(deliv2.getId()==id){
                                choosen=deliv2;
                            }
                        }

                    }
                    break;
                case "2":
                    VehicleController vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(),new CourierDataHandler(),new PharmacyDataHandler());
                    System.out.println("Enter your ID");
                    int courierId = READ.nextInt();
                    Vehicle vehicle = vc.getAvailableScooter(courierId, UserSession.getInstance().getUser().getEmail());
                    if(vehicle==null){
                        System.out.println("No scooters availables");
                    }else{
                        System.out.println("The scooter license plate picked is: " + vehicle.getLicensePlate());
                    }
                    break;
                case "3":
                    vc = new VehicleController(new VehicleHandler(), new DeliveryHandler(), new ParkHandler(),new CourierDataHandler(),new PharmacyDataHandler());
                    System.out.println("Enter the id of the pharmacy to park");
                    int pharmacyId = READ.nextInt();
                    System.out.println("Enter the licence plate of the scooter to park");
                    String scooterId = READ.next();
                    if(vc.parkScooter(pharmacyId, scooterId)){
                        WatchService watchService = FileSystems.getDefault().newWatchService();
                        Path directory = Paths.get("C_and_Assembly");

                        WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

                        boolean flag = true;
                        while(flag) {
                            for(WatchEvent<?> event : watchKey.pollEvents()) {
                                System.out.println(event.kind());
                                Path file = ((Path) event.context());
                                System.out.println(file);
                                if(FilenameUtils.getExtension(file.toString()).equals("data")) {
                                    String name = "C_and_Assembly\\"+ file.getFileName();
                                    int result = 0;
                                    try(BufferedReader br = new BufferedReader(new FileReader(name))) {
                                         result = Integer.parseInt(br.readLine());
                                    }

                                    EmailAPI.sendLockedVehicleEmail(UserSession.getInstance().getUser().getEmail(), result);
                                    flag = false;
                                    break;
                                }

                            }

                        }
                        System.out.println("Park Completed");
                    }else{
                        System.out.println("Park Not completed");
                    }
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (!ch.equals("0")) ;
    }
}
