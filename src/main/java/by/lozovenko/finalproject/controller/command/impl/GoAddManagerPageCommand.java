package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.ADD_MANAGER_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.AVAILABLE_LABORATORIES;
import static by.lozovenko.finalproject.controller.RequestAttribute.EXCEPTION;

public class GoAddManagerPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(ADD_MANAGER_PAGE, Router.DispatchType.FORWARD);
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        try {
            List<Laboratory> laboratoriesWithoutManager = laboratoryService.findLaboratoriesWithoutManager();
            request.setAttribute(AVAILABLE_LABORATORIES,laboratoriesWithoutManager);
        }catch (ServiceException e){
            logger.log(Level.ERROR, "Error in UpdateManagerDescriptionCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
