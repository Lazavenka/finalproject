package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class GoEditLaboratoryPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(EDIT_LABORATORY_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        Manager manager = (Manager) session.getAttribute(USER);
        try {
            Optional<Laboratory> optionalLaboratory  = laboratoryService.findLaboratoryById(manager.getLaboratoryId());
            if (optionalLaboratory.isPresent()) {
                Laboratory laboratory = optionalLaboratory.get();
                request.setAttribute(SELECTED_LABORATORY, laboratory);
                Optional<Department> optionalDepartment = departmentService.findDepartmentById(laboratory.getDepartmentId());
                optionalDepartment.ifPresent(department -> request.setAttribute(SELECTED_DEPARTMENT, department));
            } else {
                request.setAttribute(LABORATORY_NOT_FOUND, true);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error in GoEditLaboratoryPageCommand", e);
        }
        return router;
    }
}
