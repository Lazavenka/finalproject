package by.lozovenko.finalproject.controller.command;

import java.util.Arrays;
import java.util.Optional;

public class CommandProvider {
    public static Optional<CustomCommand> defineCommand(String commandName){
            return Arrays.stream(CommandType.values())
                    .filter(o -> o.name().equalsIgnoreCase(commandName))
                    .map(CommandType::getCommand)
                    .findAny();
    }
}
