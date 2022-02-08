package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.ManagerDegree;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.DepartmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.model.service.impl.LaboratoryServiceImpl;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static by.lozovenko.finalproject.controller.PagePath.ABOUT;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;

public class GoAboutPageCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        LaboratoryService laboratoryService = LaboratoryServiceImpl.getInstance();

        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        try {
            long departmentsCount = departmentService.countDepartments();
            long laboratoriesCount = laboratoryService.countLaboratories();
            long equipmentCount = equipmentService.countEquipment();
            long doctorsCount = userService.countManagersByDegree(ManagerDegree.DOCTOR);
            long mastersCount = userService.countManagersByDegree(ManagerDegree.MASTER);
            long bachelorCount = userService.countManagersByDegree(ManagerDegree.BACHELOR);
            request.setAttribute(DEPARTMENTS_COUNT, departmentsCount);
            request.setAttribute(LABORATORIES_COUNT, laboratoriesCount);
            request.setAttribute(EQUIPMENT_COUNT, equipmentCount);
            request.setAttribute(DOCTORS_COUNT, doctorsCount);
            request.setAttribute(MASTERS_COUNT, mastersCount);
            request.setAttribute(BACHELOR_COUNT, bachelorCount);
        }catch (ServiceException e){

        }
        return new Router(ABOUT, Router.DispatchType.FORWARD);
    }
}
