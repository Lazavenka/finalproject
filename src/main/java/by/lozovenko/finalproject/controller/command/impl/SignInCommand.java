package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class SignInCommand implements CustomCommand {
    private UserServiceImpl userService = UserServiceImpl.getInstance();
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
