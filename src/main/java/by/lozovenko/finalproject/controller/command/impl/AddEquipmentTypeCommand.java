package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.service.EquipmentTypeService;
import by.lozovenko.finalproject.model.service.impl.EquipmentTypeServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class AddEquipmentTypeCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
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
            throw new CommandException("Error in AddEquipmentTypeCommand", e);
        }
        return router;
    }
}
