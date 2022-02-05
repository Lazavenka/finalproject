package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.USER;
import static by.lozovenko.finalproject.controller.RequestAttribute.USER_ROLE;
@Deprecated
public class GoHomeCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(INDEX, Router.DispatchType.FORWARD);
    }
}
