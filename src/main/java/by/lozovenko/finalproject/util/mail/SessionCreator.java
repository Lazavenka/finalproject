package by.lozovenko.finalproject.util.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SessionCreator {
    private final String userName;
    private final String userPassword;
    private final Properties sessionProperties;

    public SessionCreator(Properties properties){
        userName = properties.getProperty("mail.user.name");
        userPassword = properties.getProperty("mail.user.password");
        sessionProperties = properties;
    }
    public Session createSession(){
        return Session.getDefaultInstance(sessionProperties, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(userName, userPassword);
            }
        });
    }
}
