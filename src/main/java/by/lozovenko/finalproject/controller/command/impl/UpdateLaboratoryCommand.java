package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class UpdateLaboratoryCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(EDIT_LABORATORY_PAGE, Router.DispatchType.FORWARD);
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        Map<String, String> laboratoryData = new HashMap<>();
        String laboratoryToEditId = request.getParameter(LABORATORY_ID);
        String departmentIdString = request.getParameter(DEPARTMENT_ID);
        String laboratoryName = request.getParameter(LABORATORY_NAME);
        String laboratoryDescription = request.getParameter(DESCRIPTION);
        String laboratoryLocation = request.getParameter(LABORATORY_LOCATION);
        laboratoryData.put(DEPARTMENT_ID, departmentIdString);
        laboratoryData.put(LABORATORY_NAME, laboratoryName);
        laboratoryData.put(DESCRIPTION, laboratoryDescription);
        laboratoryData.put(LABORATORY_LOCATION, laboratoryLocation);

        try {
            if (laboratoryService.updateLaboratoryById(laboratoryToEditId, laboratoryData)){
                request.setAttribute(SUCCESS_MESSAGE, true);
                router.setPage(EDIT_LABORATORY_PAGE);
            }else {
                for (Map.Entry<String, String> entry : laboratoryData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case INVALID_LABORATORY_NAME -> {
                            request.setAttribute(INVALID_LABORATORY_NAME, true);
                            laboratoryData.put(LABORATORY_NAME, EMPTY);
                        }
                        case INVALID_LOCATION -> {
                            request.setAttribute(INVALID_LOCATION, true);
                            laboratoryData.put(LABORATORY_LOCATION, EMPTY);
                        }
                        case INVALID_DESCRIPTION -> {
                            request.setAttribute(INVALID_DESCRIPTION, true);
                            laboratoryData.put(DESCRIPTION, EMPTY);
                        }
                    }
                }
                request.setAttribute(LABORATORY_DATA, laboratoryData);
                request.setAttribute(ERROR_MESSAGE, true);
            }
            Optional<Laboratory> optionalLaboratory = laboratoryService.findLaboratoryById(laboratoryToEditId);
            optionalLaboratory.ifPresent(laboratory -> request.setAttribute(SELECTED_LABORATORY, laboratory));
        }catch (ServiceException e){
            throw new CommandException("Error in UpdateLaboratoryCommand", e);
        }
        return router;
    }
}
