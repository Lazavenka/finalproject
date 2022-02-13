package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.controller.RequestParameter.EMAIL;

public class AddAssistantCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Manager manager = (Manager)session.getAttribute(USER);
        UserService userService = UserServiceImpl.getInstance();
        long laboratoryId = manager.getLaboratoryId();
        Router router = new Router();
        Map<String, String> assistantData = new HashMap<>();
        assistantData.put(LOGIN, request.getParameter(LOGIN).trim());
        assistantData.put(PASSWORD, request.getParameter(PASSWORD).trim());
        assistantData.put(CONFIRMED_PASSWORD, request.getParameter(CONFIRMED_PASSWORD).trim());
        assistantData.put(LABORATORY_ID, String.valueOf(laboratoryId));
        assistantData.put(FIRST_NAME, request.getParameter(FIRST_NAME).trim());
        assistantData.put(LAST_NAME, request.getParameter(LAST_NAME).trim());
        assistantData.put(EMAIL, request.getParameter(EMAIL).trim());
        assistantData.put(PHONE, request.getParameter(PHONE).trim());

        try {
            if (userService.addAssistant(assistantData)) {
                router.setPage(SUCCESS_PAGE);
                router.setRedirect();
            } else {
                for (Map.Entry<String, String> entry : assistantData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case LOGIN_EXISTS -> {
                            request.setAttribute(LOGIN_EXISTS, true);
                            assistantData.put(LOGIN, EMPTY);
                        }
                        case INVALID_LOGIN -> {
                            request.setAttribute(INVALID_LOGIN, true);
                            assistantData.put(LOGIN, EMPTY);
                        }
                        case INVALID_PASSWORD -> {
                            request.setAttribute(INVALID_PASSWORD, true);
                            assistantData.put(PASSWORD, EMPTY);
                            assistantData.put(CONFIRMED_PASSWORD, EMPTY);
                        }
                        case PASSWORDS_MISMATCH -> {
                            request.setAttribute(PASSWORDS_MISMATCH, true);
                            assistantData.put(CONFIRMED_PASSWORD, EMPTY);
                        }
                        case INVALID_FIRST_NAME -> {
                            request.setAttribute(INVALID_FIRST_NAME, true);
                            assistantData.put(FIRST_NAME, EMPTY);
                        }
                        case INVALID_LAST_NAME -> {
                            request.setAttribute(INVALID_LAST_NAME, true);
                            assistantData.put(LAST_NAME, EMPTY);
                        }
                        case INVALID_PHONE -> {
                            request.setAttribute(INVALID_PHONE, true);
                            assistantData.put(PHONE, EMPTY);
                        }
                        case INVALID_EMAIL -> {
                            request.setAttribute(INVALID_EMAIL, true);
                            assistantData.put(EMAIL, EMPTY);
                        }
                        case NOT_UNIQUE_EMAIL -> {
                            request.setAttribute(NOT_UNIQUE_EMAIL, true);
                            assistantData.put(EMAIL, EMPTY);
                        }
                    }
                }
                request.setAttribute(USER_REGISTRATION_DATA, assistantData);
                router.setPage(ADD_ASSISTANT_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error in AddAssistantCommand", e);
        }
        return router;
    }
}
