package de.hwg_lu.bw4s.beansPruefung;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMail {

    private static Message prepareMessage(Session session, String myAccount, String empfaenger) throws Exception{
    		Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(empfaenger));
            message.setSubject("Mail Test");

            // Multipart-Message ("Wrapper") erstellen
            Multipart multipart = new MimeMultipart();
            // Body-Part setzen:
            BodyPart messageBodyPart = new MimeBodyPart();
            // Textteil des Body-Parts
            messageBodyPart.setText("Text-Inhalt der E-Mail zum Testen");
            // Body-Part dem Multipart-Wrapper hinzuf�gen
            multipart.addBodyPart(messageBodyPart);
            // Message fertigstellen, indem sie mit dem Multipart-Content ausgestattet wird
//            message.setContent(multipart);
//            BodyPart anhang = new MimeBodyPart();
//            String file = "beispieldatei.pdf";
//            DataSource source = new FileDataSource(file);
//            anhang.setDataHandler(new DataHandler(source));
//            anhang.setFileName(file);
//
//            multipart.addBodyPart(anhang);
//            message.setContent(multipart);

            return message;
    }

    public static void main(String[] args) {

    	Properties properties = new Properties();
		properties.put("mail.smtp.auth",  "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		String myAccount = "franktside900@gmail.com";
		String myPassword = "ciulLav9.";
		String empfaenger = "franktside@gmail.com";

       Session session = Session.getInstance(properties, new Authenticator() {
           @Override
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(myAccount, myPassword);
           }
       });

        // Message-Objekt erzeugen und senden!
		try {
			Message message = prepareMessage(session, myAccount, empfaenger);
			Transport.send(message); // E-Mail senden!
			System.out.println("E-Mail erfolgreich versendet!");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

}