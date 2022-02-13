package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.CustomMapDataValidator;

import java.util.Map;

import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.INVALID_DESCRIPTION;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class EquipmentTypeMapDataValidator extends CustomMapDataValidator {
    private static CustomMapDataValidator instance;

    private EquipmentTypeMapDataValidator() {
    }

    public static CustomMapDataValidator getInstance() {
        if (instance == null) {
            instance = new EquipmentTypeMapDataValidator();
        }
        return instance;
    }

    @Override
    public boolean validateMapData(Map<String, String> mapData) {
        boolean result = true;

        String name = mapData.get(EQUIPMENT_TYPE_NAME);
        String description = mapData.get(EQUIPMENT_TYPE_DESCRIPTION);


        if (!customFieldValidator.isCorrectEquipmentTypeName(name)) {
            mapData.put(EQUIPMENT_TYPE_NAME, INVALID_EQUIPMENT_TYPE_NAME);
            result = false;
        }

        if (!customFieldValidator.isCorrectEquipmentTypeDescription(description)) {
            mapData.put(EQUIPMENT_TYPE_DESCRIPTION, INVALID_DESCRIPTION);
            result = false;
        }

        return result;
    }
}
