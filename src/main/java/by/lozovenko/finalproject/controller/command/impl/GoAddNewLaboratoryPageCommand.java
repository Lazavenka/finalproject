package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.ADD_LABORATORY_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.DEPARTMENTS;

public class GoAddNewLaboratoryPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        try {
            List<Department> departments = departmentService.findAll();
            request.setAttribute(DEPARTMENTS, departments);
            router.setPage(ADD_LABORATORY_PAGE);
        }catch (ServiceException e){
            throw new CommandException("Error in GoAddNewLaboratoryPageCommand", e);
        }
        return router;
    }
}
