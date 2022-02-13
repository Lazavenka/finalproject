package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.CustomMapDataValidator;

import java.util.Map;

import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.INVALID_DESCRIPTION;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class DepartmentMapDataValidator extends CustomMapDataValidator {
    private static CustomMapDataValidator instance;

    private DepartmentMapDataValidator() {
    }

    public static CustomMapDataValidator getInstance() {
        if (instance == null) {
            instance = new DepartmentMapDataValidator();
        }
        return instance;
    }

    @Override
    public boolean validateMapData(Map<String, String> mapData) {
        boolean result = true;

        String name = mapData.get(DEPARTMENT_NAME);
        String address = mapData.get(DEPARTMENT_ADDRESS);
        String description = mapData.get(DESCRIPTION);


        if (!customFieldValidator.isCorrectDepartmentName(name)) {
            mapData.put(DEPARTMENT_NAME, INVALID_DEPARTMENT_NAME);
            result = false;
        }
        if (!customFieldValidator.isCorrectDepartmentAddress(address)) {
            mapData.put(DEPARTMENT_ADDRESS, INVALID_DEPARTMENT_ADDRESS);
            result = false;
        }
        if (!customFieldValidator.isCorrectDepartmentDescription(description)) {
            mapData.put(DESCRIPTION, INVALID_DESCRIPTION);
            result = false;
        }

        return result;
    }
}
