package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
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

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.MANAGER_ID;

public class FindManagerDetailsByIdCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        Router router = new Router();
        try {
            String managerId = request.getParameter(MANAGER_ID);
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
            throw new CommandException("Error in FindManagerDetailsByIdCommand", e);
        }
        return router;
    }
}
