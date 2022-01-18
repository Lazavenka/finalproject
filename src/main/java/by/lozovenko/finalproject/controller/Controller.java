package by.lozovenko.finalproject.controller;

import by.lozovenko.finalproject.controller.command.CommandProvider;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestParameter.COMMAND;

@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String commandName = request.getParameter(COMMAND);
        Optional<CustomCommand> optionalCustomCommand = CommandProvider.defineCommand(commandName);
        if (optionalCustomCommand.isPresent()){
            CustomCommand command = optionalCustomCommand.get();
            Router router = command.execute(request);
            switch (router.getType()) {
                case REDIRECT -> response.sendRedirect(router.getPage());
                case FORWARD -> {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPage());
                    dispatcher.forward(request, response);
                }
                default -> {
                    LOGGER.error("Invalid router dispath type : {}", router.getType());
                    response.sendRedirect(PagePath.ERROR_404_PAGE);
                }
            }
        }else {

            request.getSession().setAttribute("errorReason", "message.nullpage"); //todo make constants
            response.sendRedirect(PagePath.ERROR_404_PAGE);

        }

    }
}
