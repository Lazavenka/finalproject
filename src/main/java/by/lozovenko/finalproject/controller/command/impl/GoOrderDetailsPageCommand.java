package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.*;
import by.lozovenko.finalproject.model.service.impl.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ORDER_DETAILS_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.ORDER_ID;

public class GoOrderDetailsPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(ORDER_DETAILS_PAGE, Router.DispatchType.FORWARD);
        String orderIdString = request.getParameter(ORDER_ID);
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            Optional<Order> optionalOrder = orderService.findOrderById(orderIdString);
            if (optionalOrder.isPresent()) {
                Order selectedOrder = optionalOrder.get();
                request.setAttribute(SELECTED_ORDER, selectedOrder);
                long orderEquipmentId = selectedOrder.getEquipmentId();
                EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
                Optional<Equipment> optionalEquipment = equipmentService.findById(orderEquipmentId);
                if (optionalEquipment.isPresent()) {
                    Equipment selectedEquipment = optionalEquipment.get();
                    request.setAttribute(SELECTED_EQUIPMENT, selectedEquipment);
                    long laboratoryId = selectedEquipment.getLaboratoryId();
                    LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
                    Optional<Laboratory> optionalLaboratory = laboratoryService.findLaboratoryById(laboratoryId);
                    if (optionalLaboratory.isPresent()) {
                        Laboratory selectedLaboratory = optionalLaboratory.get();
                        request.setAttribute(SELECTED_LABORATORY, selectedLaboratory);
                        long departmentId = selectedLaboratory.getDepartmentId();
                        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
                        Optional<Department> optionalDepartment = departmentService.findDepartmentById(departmentId);
                        if (optionalDepartment.isPresent()) {
                            Department selectedDepartment = optionalDepartment.get();
                            request.setAttribute(SELECTED_DEPARTMENT, selectedDepartment);
                        } else {
                            request.setAttribute(DEPARTMENT_NOT_FOUND, true);
                        }
                    } else {
                        request.setAttribute(LABORATORY_NOT_FOUND, true);
                    }


                } else {
                    request.setAttribute(EQUIPMENT_NOT_FOUND, true);
                }
                UserService userService = UserServiceImpl.getInstance();
                Optional<Assistant> optionalAssistant = userService.findAssistantById(selectedOrder.getAssistantId());
                if (optionalAssistant.isPresent()){
                    Assistant assistant = optionalAssistant.get();
                    request.setAttribute(SELECTED_ASSISTANT, assistant);
                }else {
                    request.setAttribute(ASSISTANT_NOT_FOUND, true);
                }
            } else {
                request.setAttribute(ORDER_NOT_FOUND, true);
            }
        } catch (ServiceException e) {
            logger.error("Error at GoOrderDetailsPage", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        }
        return router;
    }
}