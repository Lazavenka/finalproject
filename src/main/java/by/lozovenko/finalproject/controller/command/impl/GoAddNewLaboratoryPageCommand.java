package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.ADD_LABORATORY_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.DEPARTMENTS;
import static by.lozovenko.finalproject.controller.RequestAttribute.EXCEPTION;

public class GoAddNewLaboratoryPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        try {
            List<Department> departments = departmentService.findAll();
            request.setAttribute(DEPARTMENTS, departments);
            router.setPage(ADD_LABORATORY_PAGE);
        }catch (ServiceException e){
            logger.log(Level.ERROR, "Error in GoAddNewLaboratoryPageCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
