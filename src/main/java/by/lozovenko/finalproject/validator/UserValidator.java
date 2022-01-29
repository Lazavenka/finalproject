package by.lozovenko.finalproject.validator;

import java.util.Map;

public interface UserValidator {
    boolean isCorrectLogin(String login);
    boolean isCorrectPassword(String password);
    boolean isMatchesPasswords(String password, String confirmedPassword);
    boolean isCorrectName(String name);
    boolean isCorrectEmail(String email);
    boolean isCorrectPhone(String phone);
    boolean checkUserData(Map<String, String> userData);
    boolean isCorrectBalance(String balance);
}
