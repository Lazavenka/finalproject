package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.EDIT_PROFILE_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.USER;
import static by.lozovenko.finalproject.controller.RequestParameter.DESCRIPTION;

public class GoEditUserPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(EDIT_PROFILE_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        Optional<Object> optionalLoggedUser = Optional.ofNullable(session.getAttribute(USER));
        if (optionalLoggedUser.isPresent()){
            User sessionUser = (User) optionalLoggedUser.get();
            UserRole userRole = sessionUser.getRole();
            if (userRole==UserRole.MANAGER){
                Manager manager = (Manager) sessionUser;
                request.setAttribute(DESCRIPTION, manager.getDescription());
            }
        }
        return router;
    }
}
