package by.lozovenko.finalproject.validator;

import by.lozovenko.finalproject.validator.impl.CustomFieldValidatorImpl;

import java.util.Map;

public abstract class CustomMapDataValidator {
    protected final CustomFieldValidator customFieldValidator = CustomFieldValidatorImpl.getInstance();

    public abstract boolean validateMapData(Map<String, String> mapData);
}
