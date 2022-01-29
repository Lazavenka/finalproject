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

import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class CheckBalanceCommand implements CustomCommand {
    private final UserService userService;
    public CheckBalanceCommand() {
        this.userService = UserServiceImpl.getInstance();
    }
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Object currentUser = session.getAttribute(USER);
        if (currentUser != null){
            try {
                long userId = ((User)currentUser).getId();
                Optional<BigDecimal> optionalUserBalance = userService.checkUserBalanceById(userId);
                optionalUserBalance.ifPresent(bigDecimal -> request.setAttribute(USER_BALANCE, bigDecimal));
                router.setPage(currentPage);
            }catch (ServiceException e){
                logger.log(Level.WARN, "Error in CheckBalanceCommand", e);
            }

        }
        return router;
    }
}
