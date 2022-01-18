package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.UserValidator;

public class UserValidatorImpl implements UserValidator {
    private static UserValidatorImpl instance;

    private static final String USER_LOGIN_PATTERN = "^[A-Za-zА-Яа-я0-9_]{4,16}$";
    private static final String USER_PASSWORD_PATTERN = "^(?=.*)(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,}$";

    public static UserValidatorImpl getInstance() {
        if (instance == null) {
            instance = new UserValidatorImpl();
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

    private boolean notNullOrEmpty(String stringLine) {
        return stringLine != null && !stringLine.isEmpty();
    }
}
