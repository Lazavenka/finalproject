package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.USER_MANAGEMENT_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.ERROR_USER_MANAGEMENT;
import static by.lozovenko.finalproject.controller.RequestParameter.USER_ID;

public class DeleteUserCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(USER_MANAGEMENT_PAGE, Router.DispatchType.FORWARD);
        String userId = request.getParameter(USER_ID);
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (userService.deleteUserById(userId)){
                request.setAttribute(SUCCESS_USER_MANAGEMENT, true);
            }else {
                request.setAttribute(ERROR_USER_MANAGEMENT, true);
            }
            List<User> userList = userService.findAllUsers();
            logger.log(Level.DEBUG, "UserManagementCommand add to request list of {} users", userList.size());
            request.setAttribute(USERS, userList);
        }catch (ServiceException e){
            logger.log(Level.ERROR, "Error in DeleteUserCommand", e);
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
