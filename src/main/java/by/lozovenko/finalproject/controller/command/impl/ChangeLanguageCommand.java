package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements CustomCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
