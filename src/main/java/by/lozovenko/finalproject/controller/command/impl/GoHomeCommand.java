package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.USER;
import static by.lozovenko.finalproject.controller.RequestAttribute.USER_ROLE;

public class GoHomeCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(USER);
        UserRole role = user != null ? user.getRole() : UserRole.GUEST;
        switch (role){
            case ADMIN -> router.setPage(ADMIN_PAGE);
            case MANAGER -> router.setPage(MANAGER_PAGE);
            case ASSISTANT -> router.setPage(ASSISTANT_PAGE);
            case CLIENT -> router.setPage(CLIENT_PAGE);
            default -> router.setPage(GUEST_PAGE);
        }
        return router;
    }
}
