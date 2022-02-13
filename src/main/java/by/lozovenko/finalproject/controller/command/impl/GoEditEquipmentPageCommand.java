package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.EDIT_EQUIPMENT_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.SELECTED_EQUIPMENT;
import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_ID;

public class GoEditEquipmentPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        String equipmentIdString = request.getParameter(EQUIPMENT_ID);
        Router router = new Router();
        try {
            Optional<Equipment> optionalEquipment = equipmentService.findById(equipmentIdString);
            if (optionalEquipment.isPresent()){
                logger.log(Level.DEBUG, "FOUND EQUIPMENT - {}", optionalEquipment.get());
                request.setAttribute(SELECTED_EQUIPMENT, optionalEquipment.get());
                router.setPage(EDIT_EQUIPMENT_PAGE);
            }else {
                logger.log(Level.DEBUG, "EQUIPMENT NOT FOUND");
                router.setPage(ERROR_404_PAGE);
            }
        }catch (ServiceException e){
            throw new CommandException("Error in GoEquipmentPageCommand", e);
        }
        logger.log(Level.DEBUG, "SEND PAGE = {}", router.getPage());
        return router;
    }
}
