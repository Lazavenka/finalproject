package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.EQUIPMENT_DETAILS_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.SELECTED_EQUIPMENT;
import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_ID;

public class GoEquipmentDetailsPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(EQUIPMENT_DETAILS_PAGE, Router.DispatchType.FORWARD);
        String equipmentIdString = request.getParameter(EQUIPMENT_ID);
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        try {
            Optional<Equipment> optionalEquipment = equipmentService.findById(equipmentIdString);
            optionalEquipment.ifPresent(equipment -> request.setAttribute(SELECTED_EQUIPMENT, equipment));
        }catch (ServiceException e){
            throw new CommandException("Error in GoEquipmentDetailsPageCommand", e);
        }
        return router;
    }
}
