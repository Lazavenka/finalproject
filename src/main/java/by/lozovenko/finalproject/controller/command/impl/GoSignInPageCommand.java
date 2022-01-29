package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import jakarta.servlet.http.HttpServletRequest;

import static by.lozovenko.finalproject.controller.PagePath.LOGIN_PAGE;

public class GoSignInPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(LOGIN_PAGE, Router.DispatchType.FORWARD);
    }
}
