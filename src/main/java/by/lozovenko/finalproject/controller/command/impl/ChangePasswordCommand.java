package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.EQUIPMENT_TYPE_DATA;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class ChangePasswordCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(EDIT_PROFILE_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(USER);
        UserService userService = UserServiceImpl.getInstance();
        Map<String, String> passwordData = new HashMap<>();
        passwordData.put(OLD_PASSWORD, request.getParameter(OLD_PASSWORD));
        passwordData.put(NEW_PASSWORD, request.getParameter(NEW_PASSWORD));
        passwordData.put(CONFIRMED_PASSWORD, request.getParameter(CONFIRMED_PASSWORD));
        try {
            boolean result = userService.updatePassword(sessionUser.getLogin(), passwordData);
            if (result){
                request.setAttribute(SUCCESS_MESSAGE, true);
            }else {
                for (Map.Entry<String, String> entry : passwordData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case INCORRECT_OLD_PASSWORD -> {
                            request.setAttribute(INCORRECT_OLD_PASSWORD, true);
                            passwordData.put(OLD_PASSWORD, EMPTY);
                        }
                        case INVALID_PASSWORD -> {
                            request.setAttribute(INVALID_PASSWORD, true);
                            passwordData.put(NEW_PASSWORD, EMPTY);
                        }
                        case PASSWORDS_MISMATCH -> {
                            request.setAttribute(PASSWORDS_MISMATCH, true);
                            passwordData.put(NEW_PASSWORD, EMPTY);
                            passwordData.put(CONFIRMED_PASSWORD, EMPTY);
                        }
                    }

                }
                request.setAttribute(PASSWORD_DATA, passwordData);
                request.setAttribute(ERROR_MESSAGE, true);
                if (sessionUser.getRole() == UserRole.MANAGER){
                    request.setAttribute(DESCRIPTION, ((Manager)sessionUser).getDescription());
                }
            }
        }catch (ServiceException e){
            logger.log(Level.ERROR, "Error in ChangePasswordCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
