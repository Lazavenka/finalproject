package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.controller.RequestParameter.IS_NEED_ASSISTANT;

public class UpdateEquipmentCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        Map<String, String> equipmentData = new HashMap<>();
        fillMapData(request, equipmentData);
        String equipmentToEditId = request.getParameter(EQUIPMENT_ID);
        try {
            if (equipmentService.updateEquipmentById(equipmentToEditId, equipmentData)){
                router.setPage(SUCCESS_PAGE);
                router.setRedirect();
            }else {
                clearInvalidDataAndSetRequestAttributes(request, equipmentData);
                Optional<Equipment> optionalEquipment = equipmentService.findById(equipmentToEditId);
                optionalEquipment.ifPresent(equipment -> request.setAttribute(SELECTED_EQUIPMENT, equipment));
                request.setAttribute(EQUIPMENT_DATA, equipmentData);
                request.setAttribute(ERROR_MESSAGE, true);
                router.setPage(EDIT_EQUIPMENT_PAGE);
            }
        }catch (ServiceException e){
            throw new CommandException("Error in UpdateEquipmentCommand", e);
        }
        return router;
    }

    private void fillMapData(HttpServletRequest request, Map<String, String> mapData){
        String laboratoryIdString = request.getParameter(LABORATORY_ID);
        String equipmentTypeId = request.getParameter(EQUIPMENT_TYPE_ID);
        String timeHourParam = request.getParameter(RESEARCH_TIME_HOUR);
        String timeMinuteParam = request.getParameter(RESEARCH_TIME_MINUTE);

        String equipmentName = request.getParameter(EQUIPMENT_NAME);
        String equipmentDescription = request.getParameter(DESCRIPTION);
        String pricePerHour = request.getParameter(PRICE_PER_HOUR);
        String state = request.getParameter(EQUIPMENT_STATE);
        String isNeedAssistant = request.getParameter(IS_NEED_ASSISTANT);

        mapData.put(LABORATORY_ID, laboratoryIdString);
        mapData.put(EQUIPMENT_TYPE_ID, equipmentTypeId);
        mapData.put(EQUIPMENT_NAME, equipmentName);
        mapData.put(DESCRIPTION, equipmentDescription);
        mapData.put(RESEARCH_TIME_MINUTE, timeMinuteParam);
        mapData.put(RESEARCH_TIME_HOUR, timeHourParam);
        mapData.put(PRICE_PER_HOUR, pricePerHour);
        mapData.put(EQUIPMENT_STATE, state);
        mapData.put(IS_NEED_ASSISTANT, isNeedAssistant);
    }

    private void clearInvalidDataAndSetRequestAttributes(HttpServletRequest request, Map<String, String> mapData) {
        for (Map.Entry<String, String> entry : mapData.entrySet()) {
            String value = entry.getValue();
            switch (value) {
                case INVALID_EQUIPMENT_NAME -> {
                    request.setAttribute(INVALID_EQUIPMENT_NAME, true);
                    mapData.put(EQUIPMENT_NAME, EMPTY);
                }
                case INVALID_PRICE -> {
                    request.setAttribute(INVALID_PRICE, true);
                    mapData.put(PRICE_PER_HOUR, EMPTY);
                }
                case INVALID_DESCRIPTION -> {
                    request.setAttribute(INVALID_DESCRIPTION, true);
                    mapData.put(DESCRIPTION, EMPTY);
                }
                case INVALID_RESEARCH_TIME -> {
                    request.setAttribute(INVALID_RESEARCH_TIME, true);
                    mapData.put(RESEARCH_TIME_MINUTE, EMPTY);
                    mapData.put(RESEARCH_TIME_HOUR, EMPTY);
                }
                case INVALID_ENUM -> {
                    request.setAttribute(INVALID_ENUM, true);
                    mapData.put(EQUIPMENT_STATE, EMPTY);
                }
            }
        }
    }
}
