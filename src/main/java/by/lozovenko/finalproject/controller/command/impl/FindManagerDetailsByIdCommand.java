package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.MANAGER_ID;

public class FindManagerDetailsByIdCommand implements CustomCommand {
    private final UserService userService;
    private final DepartmentService departmentService;
    private final LaboratoryService laboratoryService;

    public FindManagerDetailsByIdCommand() {
        this.userService = UserServiceImpl.getInstance();
        this.departmentService = DepartmentServiceImpl.getInstance();
        this.laboratoryService = LaboratoryServiceImpl.getInstance();
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        try {
            long managerId = Long.parseLong(request.getParameter(MANAGER_ID));
            Optional<Manager> optionalManager = userService.findManagerById(managerId);
            router.setPage(MANAGER_DETAILS_PAGE);
            if (optionalManager.isPresent()) {
                Manager manager = optionalManager.get();
                request.setAttribute(MANAGER, manager);

                long departmentId = manager.getDepartmentId();
                long laboratoryId = manager.getLaboratoryId();
                Optional<String> optionalDepartmentName = departmentService.findDepartmentNameById(departmentId);
                Optional<String> optionalLaboratoryName = laboratoryService.findLaboratoryNameById(laboratoryId);
                if (optionalLaboratoryName.isPresent()){
                    request.setAttribute(LABORATORY_NAME, optionalLaboratoryName.get());
                }else {
                    request.setAttribute(LABORATORY_NOT_FOUND, true);
                }
                if (optionalDepartmentName.isPresent()){
                    request.setAttribute(DEPARTMENT_NAME, optionalDepartmentName.get());
                }else {
                    request.setAttribute(DEPARTMENT_NOT_FOUND, true);
                }
            } else {
                request.setAttribute(NO_MANAGERS_FOUND, true);
            }
        } catch (ServiceException e){
            logger.log(Level.ERROR, "Error in FindAllManagersInCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
