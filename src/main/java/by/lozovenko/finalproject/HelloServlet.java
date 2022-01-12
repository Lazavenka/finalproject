package by.lozovenko.finalproject;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "helloServlet", urlPatterns = "/controller")
public class HelloServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger();
    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        logger.log(Level.INFO, "Log from {}; method - {}", this.getServletName(), request.getMethod());
        logger.log(Level.INFO,"contextPath : {}", request.getContextPath());
        String message = "Hello World!";
        // Hello
            Integer.parseInt(message); //добиться выкидывания на страницу 500 еррор
        PrintWriter out = response.getWriter();
        out.print("<html><body>");
        out.print("<h1>" + message + "</h1>");
        out.print("</body></html>");

    }

    public void destroy() {
    }
}