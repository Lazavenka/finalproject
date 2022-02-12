package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.PaginationConstants;
import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class BookEquipmentCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        OrderService orderService = OrderServiceImpl.getInstance();
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        if (user.getRole() == UserRole.CLIENT) {
            Client client = (Client) user;
            long clientId = client.getClientId();
            String selectedEquipmentId = request.getParameter(EQUIPMENT_ID);
            String[] orderDateAssistant = request.getParameterValues(ORDER_DATE_ASSISTANT);
            String isNeedAssistant = request.getParameter(IS_NEED_ASSISTANT);
            try {
                Optional<Equipment> optionalEquipment = equipmentService.findById(selectedEquipmentId);
                if (optionalEquipment.isPresent()) {
                    Equipment selectedEquipment = optionalEquipment.get();
                    if (orderService.createOrders(clientId, isNeedAssistant, orderDateAssistant, selectedEquipment)) {
                        int page = PaginationConstants.START_PAGE;
                        int recordsPerPage = PaginationConstants.ORDERS_PER_PAGE;
                        String pageParameter = request.getParameter(PAGE);
                        if (pageParameter != null) {
                            page = Integer.parseInt(pageParameter);
                        }
                        int startRecord = (page - 1) * recordsPerPage;
                        int numberOfRecords = orderService.countClientOrders(clientId);
                        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
                        List<Order> clientOrders = orderService.findOrdersByClientId(clientId, startRecord, recordsPerPage);
                        if (clientOrders.isEmpty()) {
                            request.setAttribute(EMPTY_LIST, true);
                        }
                        request.setAttribute(ORDER_LIST, clientOrders);
                        request.setAttribute(PAGINATION_PAGE, page);
                        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
                        request.setAttribute(SUCCESS_MESSAGE, true);
                        router.setPage(CLIENT_ORDERS_PAGE);
                    } else {
                        request.setAttribute(SELECTED_EQUIPMENT, selectedEquipment);
                        request.setAttribute(ERROR_MESSAGE, true);
                        router.setPage(BOOK_ITEM_DETAILS_PAGE);
                    }
                }
            } catch (ServiceException e) {
                logger.error("Error at FindAllDepartmentsInCommand", e);
                request.setAttribute(EXCEPTION, e);
                router.setPage(ERROR_404_PAGE);
                router.setRedirect();
            }
        } else {
            router.setPage(LOGIN_PAGE);
        }
        return router;
    }
}
