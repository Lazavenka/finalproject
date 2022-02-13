package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class GoManagersLabCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Optional<Object> optionalManager = Optional.ofNullable(session.getAttribute(USER));
        if (optionalManager.isPresent()){
            router.setPage(MANAGER_PAGE);
            LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
            EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
            UserService userService = UserServiceImpl.getInstance();
            try {
                Manager loggedManager = (Manager) optionalManager.get();
                long laboratoryId = loggedManager.getLaboratoryId();
                Optional<Laboratory> optionalLaboratory = laboratoryService.findLaboratoryById(laboratoryId);
                List<Equipment> equipmentList = equipmentService.findEquipmentByLaboratoryId(laboratoryId);
                List<Assistant> assistants =  userService.findAssistantsByLaboratoryId(laboratoryId);
                request.setAttribute(ASSISTANT_LIST, assistants);
                if (assistants.isEmpty()){
                    request.setAttribute(EMPTY_ASSISTANT_LIST, true);
                }
                request.setAttribute(EQUIPMENT_LIST, equipmentList);
                if (optionalLaboratory.isPresent()){
                    request.setAttribute(SELECTED_LABORATORY, optionalLaboratory.get());
                }else {
                    request.setAttribute(LABORATORY_NOT_FOUND, true);
                }
                request.setAttribute(MANAGER, loggedManager);
                if (equipmentList.isEmpty()){
                    request.setAttribute(EMPTY_LIST, true);
                }
            }catch (ServiceException e){
                throw new CommandException("Error in GoManagersLabCommand", e);
            }
        }else {
            router.setPage(LOGIN_PAGE);
        }
        return router;
    }
}
