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
import static by.lozovenko.finalproject.controller.RequestAttribute.CONFIRM_FAILED;
import static by.lozovenko.finalproject.controller.RequestAttribute.EXCEPTION;
import static by.lozovenko.finalproject.controller.RequestParameter.TOKEN;

public class ConfirmRegistrationCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        Router router = new Router(CONFIRM_REGISTRATION_PAGE, Router.DispatchType.FORWARD);
        String token = request.getParameter(TOKEN);
        try {
            if (userService.confirmUserRegistration(token)) {
                request.setAttribute(CONFIRM_FAILED, true);
            } else {
                router.setRedirect();
            }
        }catch (ServiceException e){
            logger.log(Level.WARN, "ERROR in ConfirmRegistrationCommand. ", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
