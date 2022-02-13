package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.DEPARTMENT_DETAILS_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.CURRENT_DEPARTMENT_ID;
import static by.lozovenko.finalproject.controller.RequestParameter.DEPARTMENT_ID;

public class FindDepartmentDetailsByIdCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(DEPARTMENT_DETAILS_PAGE, Router.DispatchType.FORWARD);
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        String departmentId = request.getParameter(DEPARTMENT_ID);
        if (departmentId == null){
            departmentId = request.getParameter(CURRENT_DEPARTMENT_ID);
        }
        try {
            Optional<Department> optionalDepartment = departmentService.findDepartmentById(departmentId);
            if (optionalDepartment.isPresent()){
                Department selectedDepartment = optionalDepartment.get();
                long selectedDepartmentId = selectedDepartment.getId();
                request.setAttribute(SELECTED_DEPARTMENT, selectedDepartment);
                List<Laboratory> laboratoryList = laboratoryService.findLaboratoriesByDepartmentId(selectedDepartmentId);
                request.setAttribute(LABORATORIES, laboratoryList);
                if (laboratoryList.isEmpty()) {
                    request.setAttribute(EMPTY_LIST, true);
                }
            }
            List<Department> departmentList = departmentService.findAll();
            request.setAttribute(DEPARTMENTS, departmentList);
        }catch (ServiceException e){
            throw new CommandException("Error in FindDepartmentDetailsByIdCommand", e);
        }
        return router;
    }
}
