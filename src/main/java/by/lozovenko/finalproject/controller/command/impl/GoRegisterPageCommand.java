package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lozovenko.finalproject.controller.PagePath.REGISTRATION_PAGE;

public class GoRegisterPageCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(REGISTRATION_PAGE, Router.DispatchType.FORWARD);
    }
}
