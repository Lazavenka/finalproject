package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.EDIT_EQUIPMENT_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_ID;

public class GoEditEquipmentPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        String equipmentIdString = request.getParameter(EQUIPMENT_ID);
        Router router = new Router(EDIT_EQUIPMENT_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {
            Optional<Equipment> optionalEquipment = equipmentService.findById(equipmentIdString);
            if (optionalEquipment.isPresent()) {
                Equipment selectedEquipment = optionalEquipment.get();
                if (user.getRole() == UserRole.ADMIN ||
                        (user.getRole() == UserRole.MANAGER && selectedEquipment.getLaboratoryId()
                                == ((Manager) user).getLaboratoryId())) {
                    logger.log(Level.DEBUG, "FOUND EQUIPMENT - {}", selectedEquipment);
                    request.setAttribute(SELECTED_EQUIPMENT, selectedEquipment);
                } else {
                    logger.log(Level.DEBUG, "Not allowed to edit with this authorization");
                    request.setAttribute(EQUIPMENT_NOT_FOUND, true);
                }
            } else {
                logger.log(Level.DEBUG, "EQUIPMENT NOT FOUND");
                request.setAttribute(EQUIPMENT_NOT_FOUND, true);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error in GoEquipmentPageCommand", e);
        }
        logger.log(Level.DEBUG, "SEND PAGE = {}", router.getPage());
        return router;
    }
}
