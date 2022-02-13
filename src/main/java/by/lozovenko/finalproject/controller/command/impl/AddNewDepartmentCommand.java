package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class AddNewDepartmentCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        String departmentName = request.getParameter(DEPARTMENT_NAME);
        String departmentAddress = request.getParameter(DEPARTMENT_ADDRESS);
        String departmentDescription = request.getParameter(DESCRIPTION);
        Map<String, String> departmentData = new HashMap<>();
        departmentData.put(DEPARTMENT_NAME, departmentName);
        departmentData.put(DEPARTMENT_ADDRESS, departmentAddress);
        departmentData.put(DESCRIPTION, departmentDescription);
        try {
            if (departmentService.addNewDepartment(departmentData)){
                router.setPage(SUCCESS_PAGE);
                router.setRedirect();
            }    else {
                for (Map.Entry<String, String> entry : departmentData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case INVALID_DEPARTMENT_NAME -> {
                            request.setAttribute(INVALID_DEPARTMENT_NAME, true);
                            departmentData.put(DEPARTMENT_NAME, EMPTY);
                        }
                        case INVALID_DEPARTMENT_ADDRESS -> {
                            request.setAttribute(INVALID_DEPARTMENT_ADDRESS, true);
                            departmentData.put(DEPARTMENT_ADDRESS, EMPTY);
                        }
                        case INVALID_DESCRIPTION -> {
                            request.setAttribute(INVALID_DESCRIPTION, true);
                            departmentData.put(DESCRIPTION, EMPTY);
                        }
                    }

                }
                request.setAttribute(DEPARTMENT_DATA, departmentData);
                router.setPage(ADD_DEPARTMENT_PAGE);
            }
        }catch (ServiceException e){
            throw new CommandException("Error in AddNewDepartmentCommand", e);

        }
        return router;
    }
}
