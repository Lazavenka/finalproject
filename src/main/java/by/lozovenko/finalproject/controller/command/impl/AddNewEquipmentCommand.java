package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.EquipmentTypeService;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.EquipmentTypeServiceImpl;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class AddNewEquipmentCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();

        EquipmentTypeService equipmentTypeService = EquipmentTypeServiceImpl.getInstance();
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();

        Map<String, String> equipmentData = new HashMap<>();
        String laboratoryIdString = request.getParameter(LABORATORY_ID);

        fillMapData(request, equipmentData);
        Optional<Object> optionalCurrentUser = Optional.ofNullable(session.getAttribute(USER));
        if (optionalCurrentUser.isPresent()){
            User currentUser = (User) optionalCurrentUser.get();
            if (currentUser.getRole() == UserRole.MANAGER){
                laboratoryIdString = String.valueOf(((Manager)currentUser).getLaboratoryId());
                equipmentData.put(LABORATORY_ID, laboratoryIdString);
            }
            logger.log(Level.DEBUG, equipmentData);
            try {
                if (equipmentService.addNewEquipment(equipmentData)){

                    router.setRedirect();
                }else {
                    clearInvalidDataAndSetRequestAttributes(request, equipmentData);
                    List<Laboratory> laboratories = laboratoryService.findAll();
                    request.setAttribute(LABORATORIES, laboratories);

                    Optional<Laboratory> optionalLaboratory = laboratoryService.findLaboratoryById(laboratoryIdString);
                    optionalLaboratory.ifPresent(laboratory -> request.setAttribute(SELECTED_LABORATORY, laboratory));

                    List<EquipmentType> equipmentTypes = equipmentTypeService.findAll();
                    request.setAttribute(EQUIPMENT_TYPE_LIST, equipmentTypes);

                    request.setAttribute(EQUIPMENT_DATA, equipmentData);
                    router.setPage(ADD_EQUIPMENT_PAGE);
                }
            }catch (ServiceException e){
                logger.log(Level.ERROR, "Error in AddNewEquipmentCommand", e);
                request.setAttribute(EXCEPTION, e);
                router.setPage(ERROR_404_PAGE);
                router.setRedirect();
            }
        }else {
            router.setPage(LOGIN_PAGE);
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

    private void clearInvalidDataAndSetRequestAttributes(HttpServletRequest request, Map<String, String> mapData){
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
