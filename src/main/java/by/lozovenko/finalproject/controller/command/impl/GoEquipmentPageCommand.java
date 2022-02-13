package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.PaginationConstants;
import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.EquipmentTypeService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.EquipmentTypeServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.lozovenko.finalproject.controller.PagePath.EQUIPMENT_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.PAGE;

public class GoEquipmentPageCommand implements CustomCommand {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        EquipmentTypeService equipmentTypeService = EquipmentTypeServiceImpl.getInstance();
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        Router router = new Router(EQUIPMENT_PAGE, Router.DispatchType.FORWARD);
        int page = PaginationConstants.START_PAGE;
        int recordsPerPage = PaginationConstants.EQUIPMENT_PER_PAGE;
        String pageParameter = request.getParameter(PAGE);
        if (pageParameter != null ){
            page = Integer.parseInt(pageParameter);
        }
        try {
            int startRecord = (page - 1) * recordsPerPage;
            int numberOfRecords = equipmentService.countEquipment();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
            List<EquipmentType> equipmentTypeList = equipmentTypeService.findAll();
            List<Equipment> equipmentList = equipmentService.findAll(startRecord, numberOfPages);

            request.setAttribute(EQUIPMENT_TYPE_LIST, equipmentTypeList);
            request.setAttribute(EQUIPMENT_LIST, equipmentList);
            request.setAttribute(PAGINATION_PAGE, page);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            if (equipmentList.isEmpty()){
                request.setAttribute(EMPTY_LIST, true);
            }

        } catch (ServiceException e) {
            throw new CommandException("Error in GoEquipmentPageCommand", e);
        }
        return router;
    }
}
