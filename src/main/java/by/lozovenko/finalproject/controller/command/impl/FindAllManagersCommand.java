package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.ALL_MANAGERS_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.MANAGERS;

public class FindAllManagersCommand implements CustomCommand {
    private final UserServiceImpl userService;

    public FindAllManagersCommand() {
        this.userService = UserServiceImpl.getInstance();
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        try {
            List<User> managers = userService.findAllManagers();
            request.setAttribute(MANAGERS, managers);
            router.setPage(ALL_MANAGERS_PAGE);
        }catch (ServiceException e){

        }
        return router;
    }
}
