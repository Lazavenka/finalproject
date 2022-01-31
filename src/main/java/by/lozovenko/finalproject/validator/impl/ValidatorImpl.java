package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.Validator;

import java.util.Map;

import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class ValidatorImpl implements Validator {
    private static Validator instance;

    private static final String USER_LOGIN_PATTERN = "^[A-Za-zА-Яа-я0-9_.]{4,20}$";
    private static final String USER_PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!#%*?&_])[A-Za-z\\d@$!#%*?&_]{8,20}$";
    private static final String USER_NAME_PATTERN = "[A-Za-zА-Яа-я]{2,20}";
    private static final String EMAIL_PATTERN = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,6}$";
    private static final String EMAIL_SYMBOL_PATTERN = ".{8,55}";
    private static final String PHONE_PATTERN = "(25|29|33|44)\\d{7}";
    private static final String ID_PATTERN = "^\\d+$";
    private static final String BALANCE_PATTERN = "^\\d{1,3}\\.?\\d{0,2}$";

    public static Validator getInstance() {
        if (instance == null) {
            instance = new ValidatorImpl();
        }
        return instance;
    }


    @Override
    public boolean isCorrectLogin(String login) {
        return notNullOrEmpty(login) && login.matches(USER_LOGIN_PATTERN);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        return notNullOrEmpty(password) && password.matches(USER_PASSWORD_PATTERN);
    }

    @Override
    public boolean isCorrectName(String name) {
        return notNullOrEmpty(name) && name.matches(USER_NAME_PATTERN);
    }

    @Override
    public boolean isMatchesPasswords(String password, String confirmedPassword) {
        return notNullOrEmpty(password) && notNullOrEmpty(confirmedPassword) && password.equals(confirmedPassword);
    }

    @Override
    public boolean isCorrectEmail(String email) {
        return notNullOrEmpty(email) && email.matches(EMAIL_PATTERN) && email.matches(EMAIL_SYMBOL_PATTERN);
    }

    @Override
    public boolean isCorrectPhone(String phone) {
        return notNullOrEmpty(phone) && phone.matches(PHONE_PATTERN);
    }

    @Override
    public boolean checkUserData(Map<String, String> userData) {
        boolean result = true;

        String login = userData.get(LOGIN);
        String password = userData.get(PASSWORD);
        String confirmedPassword = userData.get(CONFIRMED_PASSWORD);
        String firstName = userData.get(FIRST_NAME);
        String lastName = userData.get(LAST_NAME);
        String phone = userData.get(PHONE);
        String email = userData.get(EMAIL);

        if (!isCorrectLogin(login)){
            userData.put(LOGIN, INVALID_LOGIN);
            result = false;
        }
        if (!isCorrectPassword(password)){
            userData.put(PASSWORD, INVALID_PASSWORD);
            result = false;
        }
        if (!isCorrectName(firstName)){
            userData.put(FIRST_NAME, INVALID_FIRST_NAME);
            result = false;
        }
        if (!isCorrectName(lastName)){
            userData.put(LAST_NAME, INVALID_LAST_NAME);
            result = false;
        }
        if (!isCorrectPhone(phone)){
            userData.put(PHONE, INVALID_PHONE);
            result = false;
        }
        if (!isCorrectEmail(email)){
            userData.put(EMAIL, INVALID_EMAIL);
            result = false;
        }
        if (!isMatchesPasswords(password, confirmedPassword)){
            userData.put(CONFIRMED_PASSWORD, PASSWORDS_MISMATCH);
            result = false;
        }
        return result;
    }

    @Override
    public boolean isCorrectBalance(String balance) {
        return notNullOrEmpty(balance) && balance.matches(BALANCE_PATTERN);
    }

    @Override
    public boolean isCorrectEquipmentTypeId(String equipmentTypeId) {
        return notNullOrEmpty(equipmentTypeId) && equipmentTypeId.matches(ID_PATTERN);
    }


    private boolean notNullOrEmpty(String stringLine) {
        return stringLine != null && !stringLine.isEmpty();
    }
}
