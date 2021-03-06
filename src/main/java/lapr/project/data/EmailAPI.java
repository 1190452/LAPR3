package lapr.project.data;

import com.sun.mail.smtp.SMTPTransport;
import lapr.project.model.Invoice;
import lapr.project.model.Product;
import lapr.project.model.UserSession;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailAPI {

    private EmailAPI() {
        throw new IllegalArgumentException("Utility class");
    }

    private static final Logger LOGGER_EMAIL = Logger.getLogger(EmailAPI.class.getName());

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "lapr3.grupo33@gmail.com";
    private static final String ACCESS = "Galospretos";

    private static final String EMAIL_FROM = "lapr3.grupo33@gmail.com";
    private static final String ESTIMATE = "estimate";
    private static final String DATA = "data";
    private static final String FLAG = "flag";

    public static boolean sendLockedVehicleEmail(String userEmail, String estimateTime, int pharmacyId, String licensePlate) {

        String text = "Your vehicle " + licensePlate + " has been locked on pharmacy " + pharmacyId + ".\nThe time estimated to fully charge is: " + estimateTime + " hours.\nThank you! \n";
        String subject = "Locked vehicle notification";

        try {
            sendMail(userEmail, subject, text);
        } catch (Exception e) {
            LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean sendEmailToClient(String userEmail, Invoice inv) {
        if (userEmail.isEmpty()) {
            return false;
        }

        String subject = "Completed order " + inv.getIdOrder() + " with sucess";
        String text = inv.toString();

        try {
            sendMail(userEmail, subject, text);
        } catch (Exception e) {
            LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean sendEmailToClient(String userEmail, Product product) {
        if (userEmail.isEmpty()) {
            return false;
        }

        String subject = "Stock Products";
        String text = "The product " + product.getName() + " is out of stock";

        try {
            sendMail(userEmail, subject, text);
        } catch (Exception e) {
            LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean sendNoticeEmail(String userEmail, String subject, String text) {
        if (userEmail.isEmpty()) {
            return false;
        }
        try {
            sendMail(userEmail, subject, text);
        } catch (Exception e) {
            LOGGER_EMAIL.log(Level.WARNING, e.getMessage());
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

            LOGGER_EMAIL.log(Level.INFO, t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmailNotification(List<String> listFiles, int pharmacyId, String licensePlate) throws InterruptedException {
        String currentDir = System.getProperty("user.dir") + "//C_and_Assembly//";
        File dir = new File(currentDir);
        //Thread.sleep(5000);
        File[] dirFiles = dir.listFiles();

        String name = null;
        for (File dirFile : dirFiles) {
            String nameDir = dirFile.getName();
            if (nameDir.contains(ESTIMATE) && nameDir.contains(DATA) && !nameDir.contains(FLAG)) {
                name = dirFile.getName();
            }
            //Thread.sleep(2000);
            if ((nameDir.contains(ESTIMATE) && nameDir.contains(DATA)) || (nameDir.contains(ESTIMATE) && nameDir.contains(DATA) && nameDir.contains(FLAG))) {
                listFiles.add(dirFile.getName());
            }

        }
        String content = null;
        if (name != null) {
            File file = new File(currentDir + name);
            try(FileReader reader =  new FileReader(file)) {
                char[] chars = new char[(int) file.length()];
                reader.read(chars);
                content = new String(chars);
            }catch (Exception e) {
                LOGGER_EMAIL.log(Level.WARNING,"Error");
            }
        } else {
            LOGGER_EMAIL.log(Level.INFO, "Missing estimate file");
        }

        if(content != null) {   //N??o envia o mail caso o conteudo seja null
            EmailAPI.sendLockedVehicleEmail(UserSession.getInstance().getUser().getEmail(), content, pharmacyId, licensePlate);
        }
        //Thread.sleep(12000);
        for (File dirFile : dirFiles) {
            if (listFiles.contains(dirFile.getName())) {
                File fileToRemove = new File(dirFile.getAbsolutePath());
                if (fileToRemove.delete()) {
                    LOGGER_EMAIL.log(Level.INFO, "File Removed : " + dirFile.getName());
                }
            }
        }
    }
    
}


