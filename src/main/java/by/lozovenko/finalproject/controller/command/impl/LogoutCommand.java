package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.lozovenko.finalproject.controller.PagePath.GUEST_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.INDEX;

public class LogoutCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return new Router(INDEX, Router.DispatchType.FORWARD);
    }
}
