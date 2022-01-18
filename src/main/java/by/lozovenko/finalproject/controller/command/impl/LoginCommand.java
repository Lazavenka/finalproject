package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.PagePath;
import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class LoginCommand implements CustomCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserServiceImpl userService;

    public LoginCommand(){
        this.userService = UserServiceImpl.getInstance();
    }
    @Override
    public Router execute(HttpServletRequest request){
        HttpSession session = request.getSession();
        Router router = new Router(PagePath.LOGIN_PAGE, Router.DispatchType.FORWARD);
        String loginValue =  request.getParameter(LOGIN);
        String passValue = request.getParameter(PASSWORD);
        try {
           Optional<User> optionalUser = userService.signIn(loginValue, passValue);
           if (optionalUser.isPresent()){
               User user = optionalUser.get();
               UserRole role = user.getRole();
               session.setAttribute(USER, user);
               session.setAttribute(USER_ROLE, role);
               switch (role){
                   case ADMIN -> router.setPage(ADMIN_PAGE);
                   case MANAGER -> router.setPage(MANAGER_PAGE);
                   case ASSISTANT -> router.setPage(ASSISTANT_PAGE);
                   case CLIENT -> router.setPage(CLIENT_PAGE);
                   default -> router.setPage(GUEST_PAGE);
               }
           }else {
               request.setAttribute(INCORRECT_LOGIN_OR_PASSWORD, true);
           }
        } catch (ServiceException e){
            LOGGER.error("Error at SignInCommand", e);
            request.setAttribute("exception", e); //fixme constants and handle on front
            router.setPage(ERROR_404_PAGE); //todo куда пересылается?
        }

        return router;
    }
}
