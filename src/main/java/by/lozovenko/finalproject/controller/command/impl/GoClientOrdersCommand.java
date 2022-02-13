package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.PaginationConstants;
import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Client;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.CLIENT_ORDERS_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.PAGE;

public class GoClientOrdersCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        int page = PaginationConstants.START_PAGE;
        int recordsPerPage = PaginationConstants.ORDERS_PER_PAGE;
        String pageParameter = request.getParameter(PAGE);
        if (pageParameter != null ){
            page = Integer.parseInt(pageParameter);
        }
        Router router = new Router(CLIENT_ORDERS_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        OrderService orderService = OrderServiceImpl.getInstance();
        if (user.getRole() == UserRole.CLIENT) {
            Client client = (Client) user;
            long clientId = client.getClientId();
            try {
                int startRecord = (page - 1) * recordsPerPage;
                int numberOfRecords = orderService.countClientOrders(clientId);
                int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
                List<Order> clientOrders = orderService.findOrdersByClientId(clientId , startRecord, recordsPerPage);
                request.setAttribute(ORDER_LIST, clientOrders);
                request.setAttribute(PAGINATION_PAGE, page);
                request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
                if (clientOrders.isEmpty()) {
                    request.setAttribute(EMPTY_LIST, true);
                }
            } catch (ServiceException e) {
                throw new CommandException("Error in GoClientOrdersCommand", e);
            }
        }
        return router;
    }
}
