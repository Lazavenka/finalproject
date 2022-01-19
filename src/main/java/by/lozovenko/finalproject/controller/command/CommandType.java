package by.lozovenko.finalproject.controller.command;

import by.lozovenko.finalproject.controller.command.impl.*;
import by.lozovenko.finalproject.model.entity.UserRole;


import java.util.EnumSet;
import java.util.Set;

import static by.lozovenko.finalproject.model.entity.UserRole.*;

public enum CommandType {
    CHANGE_LOCALE_COMMAND(new ChangeLocaleCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    LOGIN_COMMAND(new LoginCommand(), EnumSet.of(GUEST)),
    LOGOUT_COMMAND(new LogoutCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT)),
    REGISTRATION_COMMAND(new RegistrationCommand(), EnumSet.of(GUEST)),

    GO_HOME_COMMAND(new GoHomeCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST));

    private final CustomCommand command;
    private final EnumSet<UserRole> allowedRoles;

    CommandType(CustomCommand command, EnumSet<UserRole> allowedRoles){
        this.command = command;
        this.allowedRoles = allowedRoles;
    }

    public CustomCommand getCommand(){
        return command;
    }
    public Set<UserRole> getAllowedRoles() {
        return allowedRoles;
    }
}
