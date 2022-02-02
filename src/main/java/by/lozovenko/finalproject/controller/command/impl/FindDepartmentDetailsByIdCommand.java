package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
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
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.DEPARTMENT_ID;

public class FindDepartmentDetailsByIdCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(DEPARTMENT_DETAILS_PAGE, Router.DispatchType.FORWARD);
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        String departmentId = request.getParameter(DEPARTMENT_ID);
        try {
            Optional<Department> optionalDepartment = departmentService.findDepartmentById(departmentId);
            if (optionalDepartment.isPresent()){
                Department selectedDepartment = optionalDepartment.get();
                long selectedDepartmentId = selectedDepartment.getId();
                request.setAttribute(SELECTED_DEPARTMENT, selectedDepartment);
                List<Laboratory> laboratoryList = laboratoryService.findLaboratoriesByDepartmentId(selectedDepartmentId);
                List<Department> departmentList = departmentService.findAll();
                request.setAttribute(LABORATORIES, laboratoryList);
                request.setAttribute(DEPARTMENTS, departmentList);
                if (laboratoryList.isEmpty()) {
                    request.setAttribute(EMPTY_LIST, true);
                }
            }
        }catch (ServiceException e){
            logger.log(Level.ERROR, "Error in FindDepartmentDetailsByIdCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }

        return router;
    }
}
