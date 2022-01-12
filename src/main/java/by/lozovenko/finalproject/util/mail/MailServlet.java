package by.lozovenko.finalproject.util.mail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet(name = "MailServlet", urlPatterns = "/sendMail")
public class MailServlet extends HttpServlet {
    //private static final Logger logger = LogManager.getLogger();
    private static final String MAIL_PROPERTIES = "config/mail.properties";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = MailServlet.class.getClassLoader()
                .getResourceAsStream(MAIL_PROPERTIES)) {
            properties.load(inputStream);
        }
        MailThread mailOperator = new MailThread(request.getParameter("to"), request.getParameter("subject"),
                request.getParameter("body"), properties);
        mailOperator.start();
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}
