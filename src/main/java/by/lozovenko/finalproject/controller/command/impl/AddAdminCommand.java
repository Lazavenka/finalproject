package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.controller.RequestParameter.EMAIL;

public class AddAdminCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        Router router = new Router();
        Map<String, String> adminData = new HashMap<>();
        adminData.put(LOGIN, request.getParameter(LOGIN).trim());
        adminData.put(PASSWORD, request.getParameter(PASSWORD).trim());
        adminData.put(CONFIRMED_PASSWORD, request.getParameter(CONFIRMED_PASSWORD).trim());
        adminData.put(FIRST_NAME, request.getParameter(FIRST_NAME).trim());
        adminData.put(LAST_NAME, request.getParameter(LAST_NAME).trim());
        adminData.put(EMAIL, request.getParameter(EMAIL).trim());
        adminData.put(PHONE, request.getParameter(PHONE).trim());

        try {
            if (userService.addAdmin(adminData)) {
                List<User> userList = userService.findAllUsers();
                logger.log(Level.DEBUG, "UserManagementCommand add to request list of {} users", userList.size());
                request.setAttribute(USERS, userList);
                request.setAttribute(SUCCESS_USER_MANAGEMENT, true);
                router.setPage(USER_MANAGEMENT_PAGE);
            } else {
                for (Map.Entry<String, String> entry : adminData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case LOGIN_EXISTS -> {
                            request.setAttribute(LOGIN_EXISTS, true);
                            adminData.put(LOGIN, EMPTY);
                        }
                        case INVALID_LOGIN -> {
                            request.setAttribute(INVALID_LOGIN, true);
                            adminData.put(LOGIN, EMPTY);
                        }
                        case INVALID_PASSWORD -> {
                            request.setAttribute(INVALID_PASSWORD, true);
                            adminData.put(PASSWORD, EMPTY);
                            adminData.put(CONFIRMED_PASSWORD, EMPTY);
                        }
                        case PASSWORDS_MISMATCH -> {
                            request.setAttribute(PASSWORDS_MISMATCH, true);
                            adminData.put(CONFIRMED_PASSWORD, EMPTY);
                        }
                        case INVALID_FIRST_NAME -> {
                            request.setAttribute(INVALID_FIRST_NAME, true);
                            adminData.put(FIRST_NAME, EMPTY);
                        }
                        case INVALID_LAST_NAME -> {
                            request.setAttribute(INVALID_LAST_NAME, true);
                            adminData.put(LAST_NAME, EMPTY);
                        }
                        case INVALID_PHONE -> {
                            request.setAttribute(INVALID_PHONE, true);
                            adminData.put(PHONE, EMPTY);
                        }
                        case INVALID_EMAIL -> {
                            request.setAttribute(INVALID_EMAIL, true);
                            adminData.put(EMAIL, EMPTY);
                        }
                        case NOT_UNIQUE_EMAIL -> {
                            request.setAttribute(NOT_UNIQUE_EMAIL, true);
                            adminData.put(EMAIL, EMPTY);
                        }
                    }
                }
                request.setAttribute(USER_REGISTRATION_DATA, adminData);
                router.setPage(ADD_ADMIN_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error in AddAdminCommand", e);
            request.setAttribute(EXCEPTION, e);
            List<User> userList = null;
            try {
                userList = userService.findAllUsers();
            } catch (ServiceException serviceException) {
                logger.log(Level.ERROR, "Error in findAllUsers", serviceException);
            }
            request.setAttribute(USERS, userList);
            request.setAttribute(ERROR_USER_MANAGEMENT, true);
            router.setPage(USER_MANAGEMENT_PAGE);
        }
        return router;
    }
}
