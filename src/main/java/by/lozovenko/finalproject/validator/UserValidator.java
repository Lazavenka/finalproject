package by.lozovenko.finalproject.validator;

public interface UserValidator {
    boolean isCorrectLogin(String login);
    boolean isCorrectPassword(String password);
}
