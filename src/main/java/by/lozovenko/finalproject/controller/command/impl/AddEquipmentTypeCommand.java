package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.EquipmentTypeService;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.EquipmentTypeServiceImpl;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class AddEquipmentTypeCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        EquipmentTypeService equipmentTypeService = EquipmentTypeServiceImpl.getInstance();
        String equipmentTypeName = request.getParameter(EQUIPMENT_TYPE_NAME);
        String equipmentTypeDescription = request.getParameter(EQUIPMENT_TYPE_DESCRIPTION);
        Map<String, String> equipmentTypeData = new HashMap<>();
        equipmentTypeData.put(EQUIPMENT_TYPE_NAME, equipmentTypeName);
        equipmentTypeData.put(EQUIPMENT_TYPE_DESCRIPTION, equipmentTypeDescription);
        try {
            if (equipmentTypeService.addNewEquipmentType(equipmentTypeData)){
                router.setPage(SUCCESS_PAGE);
                router.setRedirect();
            }    else {
                for (Map.Entry<String, String> entry : equipmentTypeData.entrySet()) {
                    String value = entry.getValue();
                    switch (value) {
                        case INVALID_EQUIPMENT_TYPE_NAME -> {
                            request.setAttribute(INVALID_EQUIPMENT_TYPE_NAME, true);
                            equipmentTypeData.put(EQUIPMENT_TYPE_NAME, EMPTY);
                        }
                        case INVALID_DESCRIPTION -> {
                            request.setAttribute(INVALID_DESCRIPTION, true);
                            equipmentTypeData.put(EQUIPMENT_TYPE_DESCRIPTION, EMPTY);
                        }
                    }

                }
                request.setAttribute(EQUIPMENT_TYPE_DATA, equipmentTypeData);
                router.setPage(ADD_EQUIPMENT_TYPE_PAGE);
            }
            }catch (ServiceException e){
            logger.log(Level.ERROR, "Error in AddEquipmentTypeCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
