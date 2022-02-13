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
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.EQUIPMENT_PAGE;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class FindEquipmentByTypeCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(EQUIPMENT_PAGE, Router.DispatchType.FORWARD);
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        EquipmentTypeService equipmentTypeService = EquipmentTypeServiceImpl.getInstance();
        String equipmentTypeId = request.getParameter(EQUIPMENT_TYPE_ID);
        if (equipmentTypeId == null) {
            equipmentTypeId = request.getParameter(CURRENT_EQUIPMENT_TYPE_ID);
        }

        int page = PaginationConstants.START_PAGE;
        int recordsPerPage = PaginationConstants.EQUIPMENT_PER_PAGE;
        String pageParameter = request.getParameter(PAGE);
        if (pageParameter != null ){
            page = Integer.parseInt(pageParameter);
        }

        try {
            Optional<EquipmentType> equipmentTypeOptional = equipmentTypeService.findById(equipmentTypeId);
            List<Equipment> equipmentList;
            int startRecord;
            int numberOfPages;
            if (equipmentTypeOptional.isPresent()) {
                EquipmentType equipmentType = equipmentTypeOptional.get();
                startRecord = (page - 1) * recordsPerPage;
                int numberOfRecords = equipmentService.countEquipmentByType(equipmentType);
                numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
                equipmentList = equipmentService.findAllByType(equipmentType, startRecord, recordsPerPage);
                request.setAttribute(SELECTED_EQUIPMENT_TYPE, equipmentType);
            } else {
                startRecord = (page - 1) * recordsPerPage;
                int numberOfRecords = equipmentService.countEquipment();
                numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
                equipmentList = equipmentService.findAll(startRecord, recordsPerPage);
            }
            List<EquipmentType> equipmentTypeList = equipmentTypeService.findAll();
            request.setAttribute(EQUIPMENT_TYPE_LIST, equipmentTypeList);
            request.setAttribute(EQUIPMENT_LIST, equipmentList);
            request.setAttribute(PAGINATION_PAGE, page);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            if (equipmentList.isEmpty()) {
                request.setAttribute(EMPTY_LIST, true);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error in FindEquipmentByTypeCommand", e);
        }
        logger.log(Level.DEBUG, "FindEqyByType QUERY = {}",request.getQueryString() );
        logger.log(Level.DEBUG, "send page = {}",router.getPage());

        return router;
    }
}
