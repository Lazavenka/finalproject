package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Client;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import by.lozovenko.finalproject.model.service.OrderPaymentCode;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.EMPTY_LIST;
import static by.lozovenko.finalproject.controller.RequestParameter.ORDER_ID;

public class PayOrderCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        String orderIdString = request.getParameter(ORDER_ID);
        OrderService orderService = OrderServiceImpl.getInstance();
        User user = (User) session.getAttribute(USER);
        if (user.getRole() == UserRole.CLIENT) {
            Client client = (Client) user;
            long clientId = client.getClientId();
            long userId = client.getId();
            try {
                OrderPaymentCode paymentResult = orderService.payOrder(userId, orderIdString);
                List<Order> clientOrders = orderService.findOrdersByClientId(clientId);
                request.setAttribute(ORDER_LIST, clientOrders);
                if (clientOrders.isEmpty()) {
                    request.setAttribute(EMPTY_LIST, true);
                }
                switch (paymentResult){
                    case PAYED -> {
                        router.setPage(SUCCESS_PAGE);
                        router.setRedirect();
                    }
                    case NOT_ENOUGH_MONEY -> {
                        request.setAttribute(LACK_OF_MONEY_ERROR, true);
                        router.setPage(CLIENT_ORDERS_PAGE);
                    }
                    case ORDER_NOT_EXIST, ERROR, INVALID_DATA -> {
                        request.setAttribute(ERROR_MESSAGE, true);
                        router.setPage(CLIENT_ORDERS_PAGE);
                    }
                }
            } catch (ServiceException e) {
                logger.error("Error at PayOrderCommand", e);
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
