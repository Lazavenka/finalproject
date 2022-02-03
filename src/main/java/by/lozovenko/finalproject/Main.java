package by.lozovenko.finalproject;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentTimeTable;
import by.lozovenko.finalproject.model.entity.OrderEquipment;
import by.lozovenko.finalproject.model.service.EquipmentTimeTableService;
import by.lozovenko.finalproject.model.service.impl.EquipmentTimeTableServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Main { //todo DELETE NAFIG!
    public static void main(String[] args) {
        Equipment hourEquipment = new Equipment();
        LocalTime averageResearchTime = LocalTime.of(1, 0);
        hourEquipment.setAverageResearchTime(averageResearchTime);

        EquipmentTimeTable equipmentTimeTable = new EquipmentTimeTable(hourEquipment);
        equipmentTimeTable.setEquipment(hourEquipment);
        EquipmentTimeTableService equipmentTimeTableService = new EquipmentTimeTableServiceImpl();
        try {
            equipmentTimeTableService.buildTimeTable(equipmentTimeTable, LocalDate.now(), 2);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        LocalDateTime startRentEquipment = LocalDateTime.of(2022, 2, 3, 19,25);
        OrderEquipment equipmentOrder = new OrderEquipment(1,1,startRentEquipment,LocalTime.of(0,25),1);
        LocalDateTime startRentEquipment3 = LocalDateTime.of(2022, 2, 4, 16,25);
        OrderEquipment equipmentOrder2 = new OrderEquipment(1,1,startRentEquipment3,LocalTime.of(0,25),1);
        List<OrderEquipment>  equipmentOrders = List.of(equipmentOrder, equipmentOrder2);

        LocalDateTime startRentEquipment2 = LocalDateTime.of(2022, 2, 3, 18,25);
        OrderEquipment assistantOrder = new OrderEquipment(1,1,startRentEquipment2,LocalTime.of(0,25),1);
        LocalDateTime startRentEquipment4 = LocalDateTime.of(2022, 2, 4, 11,25);
        OrderEquipment assistantOrder2 = new OrderEquipment(1,1,startRentEquipment4,LocalTime.of(0,25),1);

        List<OrderEquipment>  assistantOrders = List.of(assistantOrder, assistantOrder2);

        equipmentTimeTableService.setAvailability(equipmentTimeTable, equipmentOrders, assistantOrders);

        System.out.println(equipmentTimeTable);

    }
}
