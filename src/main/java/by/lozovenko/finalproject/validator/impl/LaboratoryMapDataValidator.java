package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.CustomMapDataValidator;

import java.util.Map;

import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.LABORATORY_NAME;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class LaboratoryMapDataValidator extends CustomMapDataValidator {
    private static CustomMapDataValidator instance;

    private LaboratoryMapDataValidator() {
    }

    public static CustomMapDataValidator getInstance() {
        if (instance == null) {
            instance = new LaboratoryMapDataValidator();
        }
        return instance;
    }

    @Override
    public boolean validateMapData(Map<String, String> mapData) {
        boolean result = true;

        String name = mapData.get(LABORATORY_NAME);
        String location = mapData.get(LABORATORY_LOCATION);
        String description = mapData.get(DESCRIPTION);


        if (!customFieldValidator.isCorrectLaboratoryName(name)) {
            mapData.put(LABORATORY_NAME, INVALID_LABORATORY_NAME);
            result = false;
        }
        if (!customFieldValidator.isCorrectLaboratoryLocation(location)) {
            mapData.put(LABORATORY_LOCATION, INVALID_LOCATION);
            result = false;
        }
        if (!customFieldValidator.isCorrectLaboratoryDescription(description)) {
            mapData.put(DESCRIPTION, INVALID_DESCRIPTION);
            result = false;
        }

        return result;
    }
}
