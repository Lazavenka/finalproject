package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.PaginationConstants;
import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
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
import static by.lozovenko.finalproject.controller.RequestParameter.PAGE;

public class GoLaboratoryOrdersCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        int page = PaginationConstants.START_PAGE;
        int recordsPerPage = PaginationConstants.ORDERS_PER_PAGE;
        String pageParameter = request.getParameter(PAGE);
        if (pageParameter != null ){
            page = Integer.parseInt(pageParameter);
        }
        Router router = new Router();
        HttpSession session = request.getSession();
        Optional<Object> optionalManager = Optional.ofNullable(session.getAttribute(USER));
        if (optionalManager.isPresent()){
            router.setPage(LABORATORY_ORDERS_PAGE);
            OrderService orderService = OrderServiceImpl.getInstance();
            try {
                Manager loggedManager = (Manager) optionalManager.get();
                long laboratoryId = loggedManager.getLaboratoryId();
                int startRecord = (page - 1) * recordsPerPage;
                int numberOfRecords = orderService.countLaboratoryOrders(laboratoryId);
                int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
                List<Order> orderList = orderService.findOrdersByLaboratoryId(laboratoryId, startRecord, recordsPerPage);
                request.setAttribute(ORDER_LIST, orderList);
                request.setAttribute(DATE_TIME_NOW, LocalDateTime.now());
                request.setAttribute(PAGINATION_PAGE, page);
                request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
                if (orderList.isEmpty()){
                    request.setAttribute(EMPTY_LIST, true);
                }
            }catch (ServiceException e){
                throw new CommandException("Error in GoLaboratoryOrdersCommand", e);
            }
        }else {
            router.setPage(LOGIN_PAGE);
        }
        return router;
    }
}
