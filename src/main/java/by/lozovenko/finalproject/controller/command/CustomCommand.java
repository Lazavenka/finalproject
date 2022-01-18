package by.lozovenko.finalproject.controller.command;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface CustomCommand {
    Router execute(HttpServletRequest request);
    default void refresh(){
    }
}
