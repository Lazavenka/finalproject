package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Assistant;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.ASSISTANT_SCHEDULE_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class ShowScheduleCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(ASSISTANT_SCHEDULE_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        Assistant assistant = (Assistant) session.getAttribute(USER);
        try {
            long assistantId = assistant.getAssistantId();
            OrderService orderService = OrderServiceImpl.getInstance();
            List<Order> orderList = orderService.findPayedOrdersByAssistantIdFromNow(assistantId);

            request.setAttribute(ORDER_LIST, orderList);
            if (orderList.isEmpty()) {
                request.setAttribute(EMPTY_LIST, true);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error in ShowScheduleCommand", e);
        }
        return router;
    }
}
