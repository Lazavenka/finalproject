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
import static by.lozovenko.finalproject.controller.RequestAttribute.USERS;

public class GoUserManagementPageCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Router router = new Router(USER_MANAGEMENT_PAGE, Router.DispatchType.FORWARD);
        try {
            List<User> userList = userService.findAllUsers();
            logger.log(Level.DEBUG, "UserManagementCommand add to request list of {} users", userList.size());
            request.setAttribute(USERS, userList);
        }catch (ServiceException e){
            throw new CommandException("Error in UserManagementCommandPageCommand", e);
        }
        return router;
    }
}
