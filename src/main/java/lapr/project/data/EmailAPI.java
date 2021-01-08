package lapr.project.data;

import com.sun.mail.smtp.SMTPTransport;

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

    private static final Logger WARNING_LOGGER_EMAIL = Logger.getLogger(EmailAPI.class.getName());

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "lapr3.grupo33@gmail.com";
    private static final String PASSWORD = "Galospretos";

    private static final String EMAIL_FROM = "lapr3.grupo33@gmail.com";
    //private static final String EMAIL_TO = "email_1@yahoo.com, email_2@gmail.com";

    private static final String EMAIL_SUBJECT = "Locked vehicle notification";
    private static final String EMAIL_TEXT = "Your vehicle has been locked. Thank you! \n";

    public static void sendLockedVehicleEmail(String userEmail){

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
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            WARNING_LOGGER_EMAIL.log(Level.INFO, "Response: %s", t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
