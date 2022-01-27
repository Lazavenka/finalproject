package by.lozovenko.finalproject.util.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Mail{
    private static final String MAIL_PROPERTIES = "config/mail.properties";

    public static void sendMail(String destination, String subject, String body) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Mail.class.getClassLoader()
                .getResourceAsStream(MAIL_PROPERTIES)) {
            properties.load(inputStream);
        }
        MailThread mailOperator = new MailThread(destination, subject, body, properties);
        mailOperator.start();
    }

}
