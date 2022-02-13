package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.EDIT_PROFILE_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class UpdateUserProfileCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(EDIT_PROFILE_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(USER);
        UserService userService = UserServiceImpl.getInstance();
        Map<String, String> profileData = new HashMap<>();
        profileData.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        profileData.put(LAST_NAME, request.getParameter(LAST_NAME));
        profileData.put(PHONE, request.getParameter(PHONE));
        try {
            boolean result = userService.updateUserProfile(sessionUser.getId(), profileData);
            if (result){
                request.setAttribute(SUCCESS_MESSAGE, true);
                Optional<User> updatedUser = userService.findUserById(sessionUser.getId());
                updatedUser.ifPresent(user -> session.setAttribute(USER, user));
            }else {
                for (Map.Entry<String, String> entry : profileData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case INVALID_FIRST_NAME -> {
                            request.setAttribute(INVALID_FIRST_NAME, true);
                            profileData.put(FIRST_NAME, EMPTY);
                        }
                        case INVALID_LAST_NAME -> {
                            request.setAttribute(INVALID_LAST_NAME, true);
                            profileData.put(LAST_NAME, EMPTY);
                        }
                        case INVALID_PHONE -> {
                            request.setAttribute(INVALID_PHONE, true);
                            profileData.put(PHONE, EMPTY);
                        }
                    }
                }
                request.setAttribute(PROFILE_DATA, profileData);
                request.setAttribute(ERROR_MESSAGE, true);
            }
            if (sessionUser.getRole()== UserRole.MANAGER){
                request.setAttribute(DESCRIPTION, ((Manager)sessionUser).getDescription());
            }
        }catch (ServiceException e){
            throw new CommandException("Error in UpdateUserProfileCommand", e);
        }
        return router;
    }
}
