package by.lozovenko.finalproject.controller.command;

import by.lozovenko.finalproject.controller.Router;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface CustomCommand {
    Logger logger = LogManager.getLogger();

    Router execute(HttpServletRequest request);

    default void refresh(){
    }
}
