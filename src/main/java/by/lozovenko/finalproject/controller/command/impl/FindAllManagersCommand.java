package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.ALL_MANAGERS_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.MANAGERS;

public class FindAllManagersCommand implements CustomCommand {
    private final UserService userService;

    public FindAllManagersCommand() {
        this.userService = UserServiceImpl.getInstance();
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        try {
            List<Manager> managers = userService.findAllManagers();
            request.setAttribute(MANAGERS, managers);
            router.setPage(ALL_MANAGERS_PAGE);
        } catch (ServiceException e){
            logger.error("Error at FindAllManagersInCommand", e);
            request.setAttribute("exception", e); //fixme constants and handle on front
            router.setPage(ERROR_404_PAGE); //todo куда пересылается?
        }
        return router;
    }
}
