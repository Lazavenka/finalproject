package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.ADD_MANAGER_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.AVAILABLE_LABORATORIES;

public class GoAddManagerPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ADD_MANAGER_PAGE, Router.DispatchType.FORWARD);
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        try {
            List<Laboratory> laboratoriesWithoutManager = laboratoryService.findLaboratoriesWithoutManager();
            request.setAttribute(AVAILABLE_LABORATORIES,laboratoriesWithoutManager);
        }catch (ServiceException e){
            throw new CommandException("Error in GoAddManagerPageCommand", e);
        }
        return router;
    }
}
