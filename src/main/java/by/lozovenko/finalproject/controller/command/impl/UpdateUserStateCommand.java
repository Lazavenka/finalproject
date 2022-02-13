package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.USER_MANAGEMENT_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.USER_ID;
import static by.lozovenko.finalproject.controller.RequestParameter.USER_STATE;

public class UpdateUserStateCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(USER_MANAGEMENT_PAGE, Router.DispatchType.FORWARD);
        UserService userService = UserServiceImpl.getInstance();
        String userState = request.getParameter(USER_STATE);
        String userId = request.getParameter(USER_ID);
        try {
            if (userService.updateUserStateById(userState, userId)) {
                request.setAttribute(SUCCESS_USER_MANAGEMENT, true);

            } else {
                request.setAttribute(ERROR_USER_MANAGEMENT, true);

            }
            List<User> userList = userService.findAllUsers();
            logger.log(Level.DEBUG, "UserManagementCommand add to request list of {} users", userList.size());
            request.setAttribute(USERS, userList);
        } catch (ServiceException e) {
            throw new CommandException("Error in UpdateUserStateCommand", e);
        }
        return router;
    }
}
