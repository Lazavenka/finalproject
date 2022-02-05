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
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.EQUIPMENT_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.CURRENT_EQUIPMENT_TYPE_ID;
import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_TYPE_ID;

public class FindEquipmentByTypeCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(EQUIPMENT_PAGE, Router.DispatchType.FORWARD);
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        EquipmentTypeService equipmentTypeService = EquipmentTypeServiceImpl.getInstance();
        String equipmentTypeId = request.getParameter(EQUIPMENT_TYPE_ID);
        if (equipmentTypeId == null) {
            equipmentTypeId = request.getParameter(CURRENT_EQUIPMENT_TYPE_ID);
        }
        try {
            Optional<EquipmentType> equipmentTypeOptional = equipmentTypeService.findById(equipmentTypeId);
            List<Equipment> equipmentList;
            if (equipmentTypeOptional.isPresent()) {
                EquipmentType equipmentType = equipmentTypeOptional.get();
                equipmentList = equipmentService.findAllByType(equipmentType);
                request.setAttribute(SELECTED_EQUIPMENT_TYPE, equipmentType);
            } else {
                equipmentList = equipmentService.findAll();
            }
            List<EquipmentType> equipmentTypeList = equipmentTypeService.findAll();
            request.setAttribute(EQUIPMENT_TYPE_LIST, equipmentTypeList);
            request.setAttribute(EQUIPMENT_LIST, equipmentList);
            if (equipmentList.isEmpty()) {
                request.setAttribute(EMPTY_LIST, true);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error in FindEquipmentByTypeCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        logger.log(Level.DEBUG, "FingEqyByType QUERY = {}",request.getQueryString() );
        logger.log(Level.DEBUG, "send page = {}",router.getPage());

        return router;
    }
}
