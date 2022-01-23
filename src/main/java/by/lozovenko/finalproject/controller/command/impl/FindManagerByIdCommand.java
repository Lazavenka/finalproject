package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.MANAGER_ID;

public class FindManagerByIdCommand implements CustomCommand {
    private final UserServiceImpl userService;

    public FindManagerByIdCommand() {
        this.userService = UserServiceImpl.getInstance();
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        try {
            long managerId = Long.parseLong(request.getParameter(MANAGER_ID));
            Optional<Manager> manager = userService.findManagerById(managerId);
            router.setPage(MANAGER_DETAILS_PAGE);
            if (manager.isPresent()) {
                request.setAttribute(MANAGER, manager.get());
            } else {
                request.setAttribute(NO_MANAGERS_FOUND, true);
            }
        } catch (ServiceException e){
            logger.error("Error at FindAllManagersInCommand", e);
            request.setAttribute("exception", e); //fixme constants and handle on front
            router.setPage(ERROR_404_PAGE); //todo куда пересылается?
        }
        return router;
    }
}
