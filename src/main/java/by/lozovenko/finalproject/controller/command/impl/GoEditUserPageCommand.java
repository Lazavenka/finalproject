package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import jakarta.servlet.http.HttpServletRequest;

import static by.lozovenko.finalproject.controller.PagePath.EDIT_PROFILE_PAGE;

public class GoEditUserPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(EDIT_PROFILE_PAGE, Router.DispatchType.FORWARD);
    }
}
