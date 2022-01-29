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

import static by.lozovenko.finalproject.controller.PagePath.CLIENT_BALANCE_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.CLIENT_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.BALANCE;

public class AddBalanceCommand implements CustomCommand {
    private final UserService userService;
    public AddBalanceCommand() {
        this.userService = UserServiceImpl.getInstance();
    }
    @Override
    public Router execute(HttpServletRequest request) {
        logger.log(Level.INFO, "AddBalanceCommand");
        Router router = new Router();
        String balanceString = request.getParameter(BALANCE);
        HttpSession session = request.getSession();
        Object user = session.getAttribute(USER);
        if (user != null){
            long userId = ((User) user).getId();
            try {
                boolean success = userService.addBalance(userId, balanceString);
                logger.log(Level.DEBUG, "AddBalanceCommand Success = {}", success);
                if (success){
                    router.setPage(CLIENT_PAGE);
                }else{
                    request.setAttribute(INVALID_BALANCE, true);
                    router.setPage(CLIENT_BALANCE_PAGE);
                }
            }catch (ServiceException e){
                logger.log(Level.WARN, "AddBalanceCommand exception.", e);
            }
        }
        return router;
    }
}