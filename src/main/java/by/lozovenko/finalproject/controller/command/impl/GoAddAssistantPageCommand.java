package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import jakarta.servlet.http.HttpServletRequest;

import static by.lozovenko.finalproject.controller.PagePath.ADD_ASSISTANT_PAGE;

public class GoAddAssistantPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(ADD_ASSISTANT_PAGE, Router.DispatchType.FORWARD);
    }
}
