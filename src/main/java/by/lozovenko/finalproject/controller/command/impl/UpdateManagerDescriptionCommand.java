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

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.EDIT_PROFILE_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class UpdateManagerDescriptionCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(EDIT_PROFILE_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(USER);
        UserService userService = UserServiceImpl.getInstance();
        String description = request.getParameter(DESCRIPTION);
        try {
            boolean result = userService.updateManagerDescriptionByUserId(sessionUser.getId(), description);
            if (result){
                request.setAttribute(SUCCESS_MESSAGE, true);
                request.setAttribute(DESCRIPTION, description);
                Optional<User> optionalUpdatedUser = userService.findUserById(sessionUser.getId());
                optionalUpdatedUser.ifPresent(user -> session.setAttribute(USER, user));
            }else {
                request.setAttribute(INVALID_DESCRIPTION, true);
            }
        }catch (ServiceException e){
            logger.log(Level.ERROR, "Error in UpdateManagerDescriptionCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
