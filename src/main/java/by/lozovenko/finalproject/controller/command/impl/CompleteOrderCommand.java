package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Client;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.entity.OrderState;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.ORDER_ID;

public class CompleteOrderCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String orderIdString = request.getParameter(ORDER_ID);
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            OrderState cancelled = OrderState.COMPLETED;
            if (orderService.updateOrderStateById(orderIdString, cancelled)){
                router.setPage(SUCCESS_PAGE);
                router.setRedirect();
            }else {
                HttpSession session = request.getSession();
                long laboratoryId = ((Manager)session.getAttribute(USER)).getLaboratoryId();
                List<Order> laboratoryOrders = orderService.findOrdersByLaboratoryId(laboratoryId);
                request.setAttribute(ORDER_LIST, laboratoryOrders);
                if (laboratoryOrders.isEmpty()) {
                    request.setAttribute(EMPTY_LIST, true);
                }
                request.setAttribute(ERROR_MESSAGE, true);
                router.setPage(CLIENT_ORDERS_PAGE);
            }
        }catch (ServiceException e){
            logger.error("Error at CancelOrderCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}
