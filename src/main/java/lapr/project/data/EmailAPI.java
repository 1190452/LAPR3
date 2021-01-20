package lapr.project.data;

import com.sun.mail.smtp.SMTPTransport;
import lapr.project.model.Invoice;
import lapr.project.model.Product;
import org.apache.commons.io.FilenameUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailAPI {

    private EmailAPI() {
        throw new IllegalArgumentException("Utility class");
    }

    private static final Logger WARNING_LOGGER_EMAIL = Logger.getLogger(EmailAPI.class.getName());

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "lapr3.grupo33@gmail.com";
    private static final String ACCESS = "Galospretos";

    private static final String EMAIL_FROM = "lapr3.grupo33@gmail.com";

    public static boolean sendLockedVehicleEmail(String userEmail, int estimateTime,int pharmacyId,String licensePlate){

        String text = "Your vehicle"+licensePlate+"has been locked on pharmacy" + pharmacyId +".\nThe time estimated to fully charge is: " + estimateTime + " minutes.\nThank you! \n" ;
        String subject = "Locked vehicle notification";

        try {
            sendMail(userEmail, subject, text);
        } catch (Exception e) {
            WARNING_LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean sendEmailToClient(String userEmail, Invoice inv){
        if(userEmail.isEmpty()){
            return false;
        }

        String subject = "Completed order " + inv.getIdOrder() + " with sucess";
        String text = inv.toString();

        try {
            sendMail(userEmail, subject, text);
        } catch (Exception e) {
            WARNING_LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean sendDeliveryEmailToClient(String userEmail){
        if(userEmail.isEmpty()){
            return false;
        }

        String subject = "ORDER IN THE WAY";
        String text = "We inform you that the order from The pharmacy is already leaving. Be aware! Our Courier must be getting there in a little bit.";

        try {
            sendMail(userEmail, subject, text);
        } catch (Exception e) {
            WARNING_LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean sendEmailToClient(String userEmail, Product product){
        if(userEmail.isEmpty()){
            return false;
        }

        String subject = "Stock Products";
        String text = "The product " + product.getName() + " is out of stock";
        try {
            sendMail(userEmail, subject, text);
        } catch (Exception e) {
            WARNING_LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
            return false;
        }
        return true;
    }



    public static boolean sendEmailToSendingProduct(String pharmacyEmail, Product product, int stockMissing) {
        if(pharmacyEmail.isEmpty()){
            return false;
        }

        String subject = "Requesting Product";
        String text = "Hello! We sent you " + stockMissing + " units of " + product;
        try {
            sendMail(pharmacyEmail, subject, text);
        }catch (Exception e) {
            WARNING_LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
            return false;
        }

        return true;
    }


    public static void sendMail(String email, String subject, String text) {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.trust", SMTP_SERVER);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));

            // subject
            msg.setSubject(subject);

            // content
            msg.setText(text);

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, ACCESS);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            WARNING_LOGGER_EMAIL.log(Level.INFO, t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmailNotification(int pharmacyId,String licensePlate) throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path directory = Paths.get("C_and_Assembly");

        WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        boolean flag = true;
        long endTime = System.currentTimeMillis() + 1000; //Repete o código durante 1s (procura o ficheiro durante 1s)

        while (flag && System.currentTimeMillis() < endTime) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                System.out.println(event.kind());
                Path file = ((Path) event.context());
                System.out.println(file);
                if (FilenameUtils.getExtension(file.toString()).equals("data")) {
                    String name = "C_and_Assembly\\" + file.getFileName();  //TODO VERIFICAR O CAMINHO DO FICHEIRO
                    int result = 0;
                    try (BufferedReader br = new BufferedReader(new FileReader(name))) {
                        result = Integer.parseInt(br.readLine());
                    }
                    EmailAPI.sendLockedVehicleEmail(UserSession.getInstance().getUser().getEmail(), result,pharmacyId,licensePlate);
                    flag = false;
                    break;
                }

            }

        }
    }
}

