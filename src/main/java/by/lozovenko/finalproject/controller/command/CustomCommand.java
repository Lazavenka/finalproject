package by.lozovenko.finalproject.controller.command;

import by.lozovenko.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface CustomCommand {
    String execute(HttpServletRequest request) throws CommandException;
}
