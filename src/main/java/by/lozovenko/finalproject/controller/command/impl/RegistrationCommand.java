package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class RegistrationCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Router router = new Router();
        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN, request.getParameter(LOGIN).trim());
        userData.put(PASSWORD, request.getParameter(PASSWORD).trim());
        userData.put(CONFIRMED_PASSWORD, request.getParameter(CONFIRMED_PASSWORD).trim());
        userData.put(FIRST_NAME, request.getParameter(FIRST_NAME).trim());
        userData.put(LAST_NAME, request.getParameter(LAST_NAME).trim());
        userData.put(EMAIL, request.getParameter(EMAIL).trim());
        userData.put(PHONE, request.getParameter(PHONE).trim());

        try {
            if (userService.registerUser(userData)) {
                router.setPage(CHECK_MAIL_PAGE);
                router.setRedirect();
            } else {
                for (Map.Entry<String, String> entry : userData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case LOGIN_EXISTS -> {
                            request.setAttribute(LOGIN_EXISTS, true);
                            userData.put(LOGIN, EMPTY);
                        }
                        case INVALID_LOGIN -> {
                            request.setAttribute(INVALID_LOGIN, true);
                            userData.put(LOGIN, EMPTY);
                        }
                        case INVALID_PASSWORD -> {
                            request.setAttribute(INVALID_PASSWORD, true);
                            userData.put(PASSWORD, EMPTY);
                            userData.put(CONFIRMED_PASSWORD, EMPTY);
                        }
                        case PASSWORDS_MISMATCH -> {
                            request.setAttribute(PASSWORDS_MISMATCH, true);
                            userData.put(CONFIRMED_PASSWORD, EMPTY);
                        }
                        case INVALID_FIRST_NAME -> {
                            request.setAttribute(INVALID_FIRST_NAME, true);
                            userData.put(FIRST_NAME, EMPTY);
                        }
                        case INVALID_LAST_NAME -> {
                            request.setAttribute(INVALID_LAST_NAME, true);
                            userData.put(LAST_NAME, EMPTY);
                        }
                        case INVALID_PHONE -> {
                            request.setAttribute(INVALID_PHONE, true);
                            userData.put(PHONE, EMPTY);
                        }
                        case INVALID_EMAIL -> {
                            request.setAttribute(INVALID_EMAIL, true);
                            userData.put(EMAIL, EMPTY);
                        }
                        case NOT_UNIQUE_EMAIL -> {
                            request.setAttribute(NOT_UNIQUE_EMAIL, true);
                            userData.put(EMAIL, EMPTY);
                        }
                    }
                }
                request.setAttribute(USER_REGISTRATION_DATA, userData);
                router.setPage(REGISTRATION_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error in RegistrationCommand", e);
        }
        return router;
    }
}
