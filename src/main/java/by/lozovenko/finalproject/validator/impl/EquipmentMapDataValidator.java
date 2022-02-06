package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.CustomMapDataValidator;

import java.util.Map;

import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class EquipmentMapDataValidator extends CustomMapDataValidator {
    private static CustomMapDataValidator instance;

    private EquipmentMapDataValidator(){
    }

    public static CustomMapDataValidator getInstance() {
        if (instance == null) {
            instance = new EquipmentMapDataValidator();
        }
        return instance;
    }
    @Override
    public boolean validateMapData(Map<String, String> mapData) {
        boolean result = true;

        String name = mapData.get(EQUIPMENT_NAME);
        String description = mapData.get(DESCRIPTION);
        String pricePerHour = mapData.get(PRICE_PER_HOUR);
        String researchTimeHour = mapData.get(RESEARCH_TIME_HOUR);
        String researchTimeMinute = mapData.get(RESEARCH_TIME_MINUTE);
        String state = mapData.get(EQUIPMENT_STATE);


        if (!customFieldValidator.isCorrectEquipmentName(name)){
            mapData.put(EQUIPMENT_NAME, INVALID_EQUIPMENT_NAME);
            result = false;
        }

        if (!customFieldValidator.isCorrectEquipmentDescription(description)){
            mapData.put(DESCRIPTION, INVALID_DESCRIPTION);
            result = false;
        }

        if (!customFieldValidator.isCorrectEquipmentPricePerHour(pricePerHour)){
            mapData.put(PRICE_PER_HOUR, INVALID_PRICE);
            result = false;
        }
        if (!customFieldValidator.isCorrectHours(researchTimeHour)){
            mapData.put(RESEARCH_TIME_HOUR, INVALID_RESEARCH_TIME);
            result = false;
        }
        if (!customFieldValidator.isCorrectMinutes(researchTimeMinute)){
            mapData.put(RESEARCH_TIME_MINUTE, INVALID_RESEARCH_TIME);
            result = false;
        }
        if (!customFieldValidator.isCorrectEquipmentState(state)){
            mapData.put(EQUIPMENT_STATE, INVALID_ENUM);
            result = false;
        }


        return result;
    }
}
