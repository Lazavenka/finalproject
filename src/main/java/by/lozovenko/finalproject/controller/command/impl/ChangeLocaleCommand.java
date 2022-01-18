package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLocaleCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request){
        HttpSession session = request.getSession();
        String currentPage = (String)session.getAttribute("current_page");
        Router router = new Router(currentPage, Router.DispatchType.FORWARD); //todo доделать
        return router;
    }
}
