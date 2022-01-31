package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Assistant;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.OrderEquipment;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.OrderEquipmentService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.OrderEquipmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.ASSISTANT_SCHEDULE_PAGE;
import static by.lozovenko.finalproject.controller.PagePath.ERROR_404_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class ShowScheduleCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(ASSISTANT_SCHEDULE_PAGE, Router.DispatchType.FORWARD);
        HttpSession session = request.getSession();
        Object user = session.getAttribute(USER);
        if (user != null && user.getClass() == Assistant.class){
            try {
                Assistant assistant = (Assistant) user;
                long assistantId = assistant.getAssistantId();
                OrderEquipmentService orderEquipmentService = OrderEquipmentServiceImpl.getInstance();
                EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
                List<OrderEquipment> orderEquipmentList = orderEquipmentService.findPayedOrderEquipmentByAssistantId(assistantId);
                List<Equipment> equipmentList = new ArrayList<>();
                for (OrderEquipment item: orderEquipmentList) {
                    Optional<Equipment> optionalEquipment = equipmentService.findById(item.getEquipmentId());
                    optionalEquipment.ifPresent(equipmentList::add);
                }
                request.setAttribute(ORDER_EQUIPMENT_LIST, orderEquipmentList);
                request.setAttribute(EQUIPMENT_LIST, equipmentList);
                if (equipmentList.isEmpty()){
                    request.setAttribute(EMPTY_LIST, true);
                }
            }catch (ServiceException e){
                logger.log(Level.ERROR, "Error in ShowScheduleCommand");
                request.setAttribute(EXCEPTION, e);
                router.setPage(ERROR_404_PAGE);
                router.setRedirect();
            }
        }
        return router;
    }
}
