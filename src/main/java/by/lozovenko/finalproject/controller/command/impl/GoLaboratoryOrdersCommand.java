package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class GoLaboratoryOrdersCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        Optional<Object> optionalManager = Optional.ofNullable(session.getAttribute(USER));
        if (optionalManager.isPresent()){
            router.setPage(LABORATORY_ORDERS_PAGE);
            OrderService orderService = OrderServiceImpl.getInstance();
            try {
                Manager loggedManager = (Manager) optionalManager.get();
                long laboratoryId = loggedManager.getLaboratoryId();
                List<Order> orderList = orderService.findOrdersByLaboratoryId(laboratoryId);
                request.setAttribute(ORDER_LIST, orderList);
                request.setAttribute(DATE_TIME_NOW, LocalDateTime.now());
                if (orderList.isEmpty()){
                    request.setAttribute(EMPTY_LIST, true);
                }
            }catch (ServiceException e){
                logger.error("Error at GoLaboratoryOrdersCommand", e);
                request.setAttribute(EXCEPTION, e);
                router.setPage(ERROR_404_PAGE);
                router.setRedirect();
            }
        }else {
            router.setPage(LOGIN_PAGE);
        }
        return router;
    }
}
