package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.EquipmentTypeService;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.impl.EquipmentTypeServiceImpl;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class GoAddNewEquipmentPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        EquipmentTypeService equipmentTypeService = EquipmentTypeServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Optional<Object> optionalCurrentUser = Optional.ofNullable(session.getAttribute(USER));
        if (optionalCurrentUser.isPresent()){
            router.setPage(ADD_EQUIPMENT_PAGE);
            try {
                List<EquipmentType> equipmentTypes = equipmentTypeService.findAll();
                request.setAttribute(EQUIPMENT_TYPE_LIST, equipmentTypes);
                User currentUser = (User) optionalCurrentUser.get();
                UserRole currentUserRole = currentUser.getRole();
                switch (currentUserRole){
                    case ADMIN -> {
                        List<Laboratory> laboratoryList = laboratoryService.findAll();
                        request.setAttribute(LABORATORIES, laboratoryList);
                        if (laboratoryList.isEmpty()){
                            request.setAttribute(EMPTY_LIST, true);
                        }
                    }
                    case MANAGER -> {
                        Manager manager = (Manager) currentUser;
                        long managersLaboratoryId = manager.getLaboratoryId();
                        Optional<Laboratory> selectedLaboratory = laboratoryService.findLaboratoryById(managersLaboratoryId);
                        selectedLaboratory.ifPresent(laboratory -> request.setAttribute(SELECTED_LABORATORY, laboratory));
                    }
                }
            }catch (ServiceException e){
                logger.log(Level.ERROR, "Error in GoAddNewEquipmentPageCommand", e);
                request.setAttribute(EXCEPTION, e);
                router.setPage(ERROR_404_PAGE);
                router.setRedirect();
            }

        }
        return router;
    }
}
