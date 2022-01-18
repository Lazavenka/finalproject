package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class RegistrationCommand implements CustomCommand {
    private UserService userService;
    public RegistrationCommand(){
        this.userService = UserServiceImpl.getInstance();
    }
    @Override
    public Router execute(HttpServletRequest request){


        return null;
    }
}
