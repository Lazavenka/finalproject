package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.ALL_MANAGERS_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.MANAGERS;

public class FindAllManagersCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Router router = new Router();
        try {
            List<Manager> managers = userService.findAllManagers();
            request.setAttribute(MANAGERS, managers);
            router.setPage(ALL_MANAGERS_PAGE);
        } catch (ServiceException e){
            throw new CommandException("Error in FindAllManagersCommand", e);
        }
        return router;
    }
}
