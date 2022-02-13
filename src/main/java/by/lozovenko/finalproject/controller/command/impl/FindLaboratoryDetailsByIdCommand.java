package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class FindLaboratoryDetailsByIdCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(LABORATORY_DETAILS_PAGE, Router.DispatchType.FORWARD);
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        String laboratoryIdString = request.getParameter(LABORATORY_ID);
        if(laboratoryIdString == null){
            laboratoryIdString = request.getParameter(CURRENT_LABORATORY_ID);
        }
        try {
            Optional<Laboratory> optionalLaboratory = laboratoryService.findLaboratoryById(laboratoryIdString);
            if (optionalLaboratory.isPresent()){
                Laboratory selectedLaboratory = optionalLaboratory.get();
                long laboratoryDepartmentId = selectedLaboratory.getDepartmentId();
                long laboratoryId = selectedLaboratory.getId();
                request.setAttribute(SELECTED_LABORATORY, selectedLaboratory);
                List<Laboratory> laboratoryList = laboratoryService.findLaboratoriesByDepartmentId(laboratoryDepartmentId);
                request.setAttribute(DEPARTMENT_LABORATORIES, laboratoryList);

                Optional<Department> optionalDepartment = departmentService.findDepartmentById(laboratoryDepartmentId);
                if (optionalDepartment.isPresent()){
                    Department department = optionalDepartment.get();
                    request.setAttribute(DEPARTMENT, department);
                } else {
                    request.setAttribute(DEPARTMENT_NOT_FOUND, true);
                }
                Optional<Manager> optionalManager = userService.findManagerByLaboratoryId(laboratoryId);
                if (optionalManager.isPresent()){
                    Manager manager = optionalManager.get();
                    request.setAttribute(MANAGER, manager);
                } else {
                    request.setAttribute(MANAGER_NOT_FOUND, true);
                }
                List<Equipment> laboratoryEquipment = equipmentService.findEquipmentByLaboratoryId(laboratoryId);
                request.setAttribute(EQUIPMENT_LIST, laboratoryEquipment);
                if (laboratoryEquipment.isEmpty()) {
                    request.setAttribute(EMPTY_LIST, true);
                }
            }else {
                request.setAttribute(LABORATORY_NOT_FOUND, true);
            }
        }catch (ServiceException e){
            throw new CommandException("Error in FindLaboratoryDetailsByIdCommand", e);
        }
        return router;
    }
}
