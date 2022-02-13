package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.CustomMapDataValidator;

import java.util.Map;

import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.controller.RequestParameter.CONFIRMED_PASSWORD;

public class AssistantMapDataValidator extends CustomMapDataValidator {
    private static CustomMapDataValidator instance;

    private AssistantMapDataValidator() {
    }

    public static CustomMapDataValidator getInstance() {
        if (instance == null) {
            instance = new AssistantMapDataValidator();
        }
        return instance;
    }

    @Override
    public boolean validateMapData(Map<String, String> mapData) {
        boolean result = true;

        String login = mapData.get(LOGIN);
        String password = mapData.get(PASSWORD);
        String confirmedPassword = mapData.get(CONFIRMED_PASSWORD);
        String firstName = mapData.get(FIRST_NAME);
        String lastName = mapData.get(LAST_NAME);
        String phone = mapData.get(PHONE);
        String email = mapData.get(EMAIL);

        if (!customFieldValidator.isCorrectLogin(login)) {
            mapData.put(LOGIN, INVALID_LOGIN);
            result = false;
        }
        if (!customFieldValidator.isCorrectPassword(password)) {
            mapData.put(PASSWORD, INVALID_PASSWORD);
            result = false;
        }
        if (!customFieldValidator.isCorrectName(firstName)) {
            mapData.put(FIRST_NAME, INVALID_FIRST_NAME);
            result = false;
        }
        if (!customFieldValidator.isCorrectName(lastName)) {
            mapData.put(LAST_NAME, INVALID_LAST_NAME);
            result = false;
        }
        if (!customFieldValidator.isCorrectPhone(phone)) {
            mapData.put(PHONE, INVALID_PHONE);
            result = false;
        }
        if (!customFieldValidator.isCorrectEmail(email)) {
            mapData.put(EMAIL, INVALID_EMAIL);
            result = false;
        }
        if (!customFieldValidator.isMatchesPasswords(password, confirmedPassword)) {
            mapData.put(CONFIRMED_PASSWORD, PASSWORDS_MISMATCH);
            result = false;
        }
        return result;
    }
}
