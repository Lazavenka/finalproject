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

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class LoginCommand implements CustomCommand {
    private final UserService userService;

    public LoginCommand() {
        this.userService = UserServiceImpl.getInstance();
    }

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Router router = new Router(PagePath.LOGIN_PAGE, Router.DispatchType.FORWARD);
        String loginValue = request.getParameter(LOGIN);
        String passValue = request.getParameter(PASSWORD);
        try {
            Optional<User> optionalUser = userService.signIn(loginValue, passValue);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                UserRole role = user.getRole();
                session.setAttribute(USER, user);
                switch (role) {
                    case ADMIN -> router.setPage(ADMIN_PAGE);
                    case MANAGER -> router.setPage(MANAGER_PAGE);
                    case ASSISTANT -> router.setPage(ASSISTANT_PAGE);
                    case CLIENT -> {
                        UserState userState = user.getState();
                        switch (userState){
                            case ACTIVE -> router.setPage(CLIENT_PAGE);
                            case BLOCKED -> {
                                router.setPage(LOGIN_PAGE);
                                request.setAttribute(BLOCKED_USER, true);
                            }
                            case REGISTRATION -> {
                                router.setPage(LOGIN_PAGE);
                                request.setAttribute(UNCONFIRMED_USER, true);
                            }
                        }
                    }
                    default -> router.setPage(GUEST_PAGE);
                }
            } else {
                request.setAttribute(INCORRECT_LOGIN_OR_PASSWORD, true);
            }
        } catch (ServiceException e) {
            logger.error("Error at SignInCommand", e);
            request.setAttribute("exception", e); //fixme constants and handle on front
            router.setPage(ERROR_404_PAGE); //todo куда пересылается?
        }

        return router;
    }
}
