package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import jakarta.servlet.http.HttpServletRequest;

import static by.lozovenko.finalproject.controller.PagePath.CLIENT_BALANCE_PAGE;

public class GoBalancePageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(CLIENT_BALANCE_PAGE, Router.DispatchType.FORWARD);
    }
}
