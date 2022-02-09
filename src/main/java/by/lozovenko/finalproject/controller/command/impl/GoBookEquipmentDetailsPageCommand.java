package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentTimeTable;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.BOOK_ITEM_DETAILS_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.EXCEPTION;
import static by.lozovenko.finalproject.controller.RequestAttribute.SELECTED_EQUIPMENT;
import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_ID;

public class GoBookEquipmentDetailsPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(BOOK_ITEM_DETAILS_PAGE, Router.DispatchType.FORWARD);
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        String equipmentIdString = request.getParameter(EQUIPMENT_ID);
        try {
            Optional<Equipment> optionalEquipment = equipmentService.findById(equipmentIdString);
            optionalEquipment.ifPresent(equipment -> request.setAttribute(SELECTED_EQUIPMENT, equipment));
        }catch (ServiceException e){
            logger.log(Level.ERROR, "Error in GoBookEquipmentDetailsPageCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
