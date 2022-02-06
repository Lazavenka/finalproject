package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import jakarta.servlet.http.HttpServletRequest;

import static by.lozovenko.finalproject.controller.PagePath.ADD_DEPARTMENT_PAGE;

public class GoAddNewDepartmentPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(ADD_DEPARTMENT_PAGE, Router.DispatchType.FORWARD);
    }
}
