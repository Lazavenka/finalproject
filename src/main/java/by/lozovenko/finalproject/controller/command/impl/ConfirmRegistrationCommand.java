package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.lozovenko.finalproject.controller.PagePath.CONFIRM_REGISTRATION_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestParameter.TOKEN;

public class ConfirmRegistrationCommand implements CustomCommand {
    UserService userService;
    public ConfirmRegistrationCommand(){
        userService = UserServiceImpl.getInstance();
    }
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String token = request.getParameter(TOKEN);
        try {
            boolean result = userService.confirmUserRegistration(token);
            if (result){
                router.setPage(CONFIRM_REGISTRATION_PAGE);
            }else {
                router.setPage(ERROR_404_PAGE); //TODO другую какую страницу
            }
        }catch (ServiceException e){
            logger.log(Level.WARN, "Confirm registration is fail. ", e);
        }
        return router;
    }
}
