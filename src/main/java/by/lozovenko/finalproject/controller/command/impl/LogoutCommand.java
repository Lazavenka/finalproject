package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request){
        return null;
    }
}
