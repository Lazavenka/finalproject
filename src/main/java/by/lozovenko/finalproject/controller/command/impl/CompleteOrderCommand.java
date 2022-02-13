package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.PaginationConstants;
import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.entity.OrderState;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.ORDER_ID;
import static by.lozovenko.finalproject.controller.RequestParameter.PAGE;

public class CompleteOrderCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
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
                int page = PaginationConstants.START_PAGE;
                int recordsPerPage = PaginationConstants.ORDERS_PER_PAGE;
                String pageParameter = request.getParameter(PAGE);
                if (pageParameter != null ){
                    page = Integer.parseInt(pageParameter);
                }
                long laboratoryId = ((Manager)session.getAttribute(USER)).getLaboratoryId();
                int startRecord = (page - 1) * recordsPerPage;
                int numberOfRecords = orderService.countLaboratoryOrders(laboratoryId);
                int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
                List<Order> laboratoryOrders = orderService.findOrdersByLaboratoryId(laboratoryId, startRecord, recordsPerPage);
                request.setAttribute(ORDER_LIST, laboratoryOrders);
                request.setAttribute(DATE_TIME_NOW, LocalDateTime.now());
                request.setAttribute(PAGINATION_PAGE, page);
                request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
                if (laboratoryOrders.isEmpty()) {
                    request.setAttribute(EMPTY_LIST, true);
                }
                request.setAttribute(ERROR_MESSAGE, true);
                router.setPage(LABORATORY_ORDERS_PAGE);
            }
        }catch (ServiceException e){
            throw new CommandException("Error in CompleteCommand", e);
        }
        return router;
    }
}
