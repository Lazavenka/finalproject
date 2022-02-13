package by.lozovenko.finalproject.util.mail;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailThread extends Thread {
    private static final Logger logger = LogManager.getLogger();
    private MimeMessage message;
    private final String sendToEmail;
    private final String mailSubject;
    private final String mailText;
    private final Properties properties;
    private Session mailSession;

    public MailThread(String sendToEmail, String subject, String mailText, Properties properties) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = subject;
        this.mailText = mailText;
        this.properties = properties;
    }

    private void init() {
        mailSession = (new SessionCreator(properties)).createSession();
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        try {
            message.setSubject(mailSubject);
            message.setContent(mailText, "text/html; charset=utf-8");

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        } catch (AddressException e) {
            logger.log(Level.WARN, "Incorrect address \"{}\" - {}", sendToEmail, e);
        } catch (MessagingException e) {
            logger.log(Level.WARN, "Message creation exception", e);
        }
    }

    @Override
    public void run() {
        init();
        try {
            Transport.send(message, message.getRecipients(Message.RecipientType.TO));
        } catch (MessagingException e) {
            logger.log(Level.WARN, "Message sending exception", e);
        }
    }
}
