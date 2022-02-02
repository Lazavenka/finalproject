package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class CheckBalanceCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Object currentUser = session.getAttribute(USER);
        if (currentUser != null){
            try {
                long userId = ((User)currentUser).getId();
                Optional<BigDecimal> optionalUserBalance = userService.checkUserBalanceById(userId);
                optionalUserBalance.ifPresent(bigDecimal -> session.setAttribute(USER_BALANCE, bigDecimal));
                router.setPage(currentPage);
            }catch (ServiceException e){
                logger.log(Level.ERROR, "Error in CheckBalanceCommand", e);
                request.setAttribute(EXCEPTION, e);
                router.setPage(ERROR_404_PAGE);
                router.setRedirect();
            }

        }
        return router;
    }
}
