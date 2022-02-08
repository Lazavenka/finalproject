package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lozovenko.finalproject.controller.PagePath.ADD_ADMIN_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.LOGIN_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.USER;

public class GoAddAdminPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = (User)session.getAttribute(USER);
        Router router = new Router();
        if (sessionUser.getRole() == UserRole.ADMIN){
            router.setPage(ADD_ADMIN_PAGE);
        }else {
            router.setPage(LOGIN_PAGE);
        }
        return router;
    }
}
