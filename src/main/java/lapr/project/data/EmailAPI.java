package lapr.project.data;

import com.sun.mail.smtp.SMTPTransport;
import lapr.project.model.Invoice;
import lapr.project.model.Product;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

    private static final String EMAIL_SUBJECT = "Locked vehicle notification";
    private static final String EMAIL_TEXT = "Your vehicle has been locked. Thank you! \n";

    public static void sendLockedVehicleEmail(String userEmail, int estimateTime){

        String EMAIL_TEXT = "Your vehicle has been locked.\nThe time estimated to fully charge is: " + estimateTime + " minutes.\nThank you! \n" ;
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
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail, false));

            // subject
            msg.setSubject(EMAIL_SUBJECT);

            // content
            msg.setText(EMAIL_TEXT);

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

    public static boolean sendEmailToClient(String userEmail, Invoice inv){
        if(userEmail.isEmpty()){
            return false;
        }

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
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail, false));

            // subject
            msg.setSubject("Completed order" + inv.getIdOrder() + "with sucess (PHARMACY)");

            // content
            msg.setText(inv.toString());

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
            return false;
        }
        return true;
    }

    public static boolean sendEmailToClient(String userEmail, Product product){
        if(userEmail.isEmpty()){
            return false;
        }

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
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail, false));

            // subject
            msg.setSubject("Stock Products");

            // content
            msg.setText("The product " + product.getName() + " is out of stock");

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
            return false;
        }
        return true;
    }

    public static boolean sendEmailToRequestingStock(String pharmacyEmail, Product product, int stockMissing){
        if(pharmacyEmail.isEmpty()){
            return false;
        }

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
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pharmacyEmail, false));

            // subject
            msg.setSubject("Requesting Product");

            // content
            msg.setText("Hello! We need " + stockMissing + " units of " + product);

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
            return false;
        }
        return true;
    }

    public static boolean sendEmailToSendingProduct(String pharmacyEmail, Product product, int stockMissing) {
        if(pharmacyEmail.isEmpty()){
            return false;
        }

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
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pharmacyEmail, false));

            // subject
            msg.setSubject("Requesting Product");

            // content
            msg.setText("Hello! We sent you " + stockMissing + " units of " + product);

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
            return false;
        }
        return true;
    }
}

