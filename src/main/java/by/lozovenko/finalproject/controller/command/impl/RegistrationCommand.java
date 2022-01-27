package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.lozovenko.finalproject.controller.PagePath.LOGIN_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.REGISTRATION_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class RegistrationCommand implements CustomCommand {
    private final UserService userService;

    public RegistrationCommand() {
        this.userService = UserServiceImpl.getInstance();
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();

        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(CONFIRMED_PASSWORD, request.getParameter(CONFIRMED_PASSWORD));
        userData.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userData.put(LAST_NAME, request.getParameter(LAST_NAME));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PHONE, request.getParameter(PHONE));

        try {
            if (userService.registerUser(userData)) {
                router.setPage(LOGIN_PAGE);

            } else {
                for (Map.Entry<String, String> entry : userData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case LOGIN_EXISTS -> request.setAttribute(LOGIN_EXISTS, true);
                        case INVALID_LOGIN -> request.setAttribute(INVALID_LOGIN, true);
                        case INVALID_PASSWORD -> request.setAttribute(INVALID_PASSWORD, true);
                        case PASSWORDS_MISMATCH -> request.setAttribute(PASSWORDS_MISMATCH, true);
                        case INVALID_FIRST_NAME -> request.setAttribute(INVALID_FIRST_NAME, true);
                        case INVALID_LAST_NAME -> request.setAttribute(INVALID_LAST_NAME, true);
                        case INVALID_PHONE -> request.setAttribute(INVALID_PHONE, true);
                        case INVALID_EMAIL -> request.setAttribute(INVALID_EMAIL, true);
                        case NOT_UNIQUE_EMAIL -> request.setAttribute(NOT_UNIQUE_EMAIL, true);
                    }
                }
                router.setPage(REGISTRATION_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Error in RegistrationCommand", e);
        }
        return router;
    }
}
