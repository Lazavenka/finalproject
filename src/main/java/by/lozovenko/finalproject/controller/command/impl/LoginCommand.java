package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.PagePath;
import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class LoginCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router = new Router(PagePath.LOGIN_PAGE, Router.DispatchType.FORWARD);
        String loginValue = request.getParameter(LOGIN);
        String passValue = request.getParameter(PASSWORD);
        try {
            Optional<User> optionalUser = userService.signIn(loginValue, passValue);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                UserState userState = user.getState();
                switch (userState){
                    case ACTIVE -> {
                        UserRole role = user.getRole();
                        router.setPage(INDEX);
                        switch (role){
                            case ADMIN -> session.setAttribute(USER, user);
                            case MANAGER -> {
                                Manager manager = (Manager) user;
                                session.setAttribute(USER, manager);
                            }
                            case ASSISTANT -> {
                                Assistant assistant = (Assistant) user;
                                session.setAttribute(USER, assistant);
                            }
                            case CLIENT -> {
                                Client client = (Client)user;
                                session.setAttribute(USER, client);
                                BigDecimal balance = ((Client) user).getBalance();
                                session.setAttribute(USER_BALANCE, balance);
                            }
                        }
                    }
                    case BLOCKED -> request.setAttribute(BLOCKED_USER, true);
                    case REGISTRATION -> request.setAttribute(UNCONFIRMED_USER, true);
                }
            } else {
                request.setAttribute(INCORRECT_LOGIN_OR_PASSWORD, true);
            }
        } catch (ServiceException e) {
            logger.error("Error at SignInCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
