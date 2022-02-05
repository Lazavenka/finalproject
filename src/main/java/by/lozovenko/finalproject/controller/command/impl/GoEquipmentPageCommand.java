package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.EquipmentTypeService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.EquipmentTypeServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.EQUIPMENT_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class GoEquipmentPageCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        EquipmentTypeService equipmentTypeService = EquipmentTypeServiceImpl.getInstance();
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        Router router = new Router(EQUIPMENT_PAGE, Router.DispatchType.FORWARD);
        try {
            List<EquipmentType> equipmentTypeList = equipmentTypeService.findAll();
            List<Equipment> equipmentList = equipmentService.findAll();

            request.setAttribute(EQUIPMENT_TYPE_LIST, equipmentTypeList);
            request.setAttribute(EQUIPMENT_LIST, equipmentList);

            if (equipmentList.isEmpty()){
                request.setAttribute(EMPTY_LIST, true);
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error in GoEquipmentPageCommand");
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
