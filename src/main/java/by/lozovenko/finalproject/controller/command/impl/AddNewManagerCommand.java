package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.controller.RequestParameter.EMAIL;

public class AddNewManagerCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        Router router = new Router();
        Map<String, String> managerData = new HashMap<>();

        UserService userService = UserServiceImpl.getInstance();
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();

        try {
            String laboratoryId = request.getParameter(LABORATORY_ID);
            Optional<Laboratory> optionalLaboratory = laboratoryService.findLaboratoryById(laboratoryId);
            if (optionalLaboratory.isPresent()){
                Laboratory laboratory = optionalLaboratory.get();
                managerData.put(LOGIN, request.getParameter(LOGIN).trim());
                managerData.put(PASSWORD, request.getParameter(PASSWORD).trim());
                managerData.put(CONFIRMED_PASSWORD, request.getParameter(CONFIRMED_PASSWORD).trim());
                managerData.put(FIRST_NAME, request.getParameter(FIRST_NAME).trim());
                managerData.put(LAST_NAME, request.getParameter(LAST_NAME).trim());
                managerData.put(EMAIL, request.getParameter(EMAIL).trim());
                managerData.put(PHONE, request.getParameter(PHONE).trim());
                managerData.put(LABORATORY_ID, laboratoryId);
                managerData.put(DEPARTMENT_ID, String.valueOf(laboratory.getDepartmentId()));
                managerData.put(DESCRIPTION, request.getParameter(DESCRIPTION));
                managerData.put(MANAGER_DEGREE, request.getParameter(MANAGER_DEGREE));
                if (userService.addManager(managerData)) {
                    router.setPage(SUCCESS_PAGE);
                    router.setRedirect();
                } else {
                    for (Map.Entry<String, String> entry : managerData.entrySet()) {
                        String value = entry.getValue();
                        switch (value) {
                            case LOGIN_EXISTS -> {
                                request.setAttribute(LOGIN_EXISTS, true);
                                managerData.put(LOGIN, EMPTY);
                            }
                            case INVALID_LOGIN -> {
                                request.setAttribute(INVALID_LOGIN, true);
                                managerData.put(LOGIN, EMPTY);
                            }
                            case INVALID_PASSWORD -> {
                                request.setAttribute(INVALID_PASSWORD, true);
                                managerData.put(PASSWORD, EMPTY);
                                managerData.put(CONFIRMED_PASSWORD, EMPTY);
                            }
                            case PASSWORDS_MISMATCH -> {
                                request.setAttribute(PASSWORDS_MISMATCH, true);
                                managerData.put(CONFIRMED_PASSWORD, EMPTY);
                            }
                            case INVALID_FIRST_NAME -> {
                                request.setAttribute(INVALID_FIRST_NAME, true);
                                managerData.put(FIRST_NAME, EMPTY);
                            }
                            case INVALID_LAST_NAME -> {
                                request.setAttribute(INVALID_LAST_NAME, true);
                                managerData.put(LAST_NAME, EMPTY);
                            }
                            case INVALID_PHONE -> {
                                request.setAttribute(INVALID_PHONE, true);
                                managerData.put(PHONE, EMPTY);
                            }
                            case INVALID_EMAIL -> {
                                request.setAttribute(INVALID_EMAIL, true);
                                managerData.put(EMAIL, EMPTY);
                            }
                            case NOT_UNIQUE_EMAIL -> {
                                request.setAttribute(NOT_UNIQUE_EMAIL, true);
                                managerData.put(EMAIL, EMPTY);
                            }
                            case INVALID_ENUM -> {
                                request.setAttribute(INVALID_ENUM, true);
                                managerData.put(MANAGER_DEGREE, EMPTY);
                            }
                            case INVALID_DESCRIPTION -> {
                                request.setAttribute(INVALID_DESCRIPTION, true);
                                managerData.put(DESCRIPTION, EMPTY);
                            }
                        }
                    }
                    List<Laboratory> laboratoriesWithoutManager = laboratoryService.findLaboratoriesWithoutManager();
                    request.setAttribute(AVAILABLE_LABORATORIES,laboratoriesWithoutManager);
                    request.setAttribute(USER_REGISTRATION_DATA, managerData);
                    router.setPage(ADD_MANAGER_PAGE);
                }
            }else {
                List<User> userList = userService.findAllUsers();
                request.setAttribute(USERS, userList);
                request.setAttribute(ERROR_USER_MANAGEMENT, true);
                router.setPage(USER_MANAGEMENT_PAGE);
                request.setAttribute(EXCEPTION, new ServiceException("Error due to selection of a non-existent lab. Or a manager has already been added to the selected laboratory"));
            }
        } catch (ServiceException e) {
            throw new CommandException("Error in AddManagerCommand", e);
        }
        return router;
    }
}
