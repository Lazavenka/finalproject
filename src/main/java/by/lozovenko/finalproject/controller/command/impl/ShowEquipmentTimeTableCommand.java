package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentTimeTable;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.time.LocalDate;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.BOOK_ITEM_DETAILS_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.DATE;
import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_ID;


public class ShowEquipmentTimeTableCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(BOOK_ITEM_DETAILS_PAGE, Router.DispatchType.FORWARD);
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        String equipmentIdString = request.getParameter(EQUIPMENT_ID);
        String dateString = request.getParameter(DATE);
        try {
            Optional<EquipmentTimeTable> optionalTimeTable = equipmentService.provideEquipmentTimeTable(equipmentIdString, dateString);
            if (optionalTimeTable.isPresent()){
                EquipmentTimeTable timeTable = optionalTimeTable.get();
                Equipment selectedEquipment = timeTable.getEquipment();
                request.setAttribute(EQUIPMENT_TIMETABLE, timeTable);
                request.setAttribute(SELECTED_EQUIPMENT, selectedEquipment);
                request.setAttribute(DATE, dateString);
                request.setAttribute(AVERAGE_RESEARCH_TIME, selectedEquipment.getAverageResearchTime());

            }else {
                Optional<Equipment> optionalEquipment = equipmentService.findById(equipmentIdString);
                optionalEquipment.ifPresent(equipment -> request.setAttribute(SELECTED_EQUIPMENT, equipment));
                request.setAttribute(ERROR_MESSAGE, true);
            }



        }catch (ServiceException e){

        }
        return router;
    }
}
