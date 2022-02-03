package by.lozovenko.finalproject;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Assistant;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentTimeTable;
import by.lozovenko.finalproject.model.entity.OrderEquipment;
import by.lozovenko.finalproject.model.service.EquipmentTimeTableService;
import by.lozovenko.finalproject.model.service.impl.EquipmentTimeTableServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main { //todo DELETE NAFIG!
    public static void main(String[] args) {
        Equipment hourEquipment = new Equipment();
        LocalTime averageResearchTime = LocalTime.of(0, 21);
        hourEquipment.setAverageResearchTime(averageResearchTime);

        EquipmentTimeTable equipmentTimeTable = new EquipmentTimeTable(hourEquipment);
        equipmentTimeTable.setEquipment(hourEquipment);
        EquipmentTimeTableService equipmentTimeTableService = new EquipmentTimeTableServiceImpl();
        Assistant assistant1 = new Assistant();
        assistant1.setAssistantId(1);
        Assistant assistant2 = new Assistant();
        assistant2.setAssistantId(2);
        List<Assistant> laboratoryAssistants = new ArrayList<>();
        laboratoryAssistants.add(assistant1);
        laboratoryAssistants.add(assistant2);
        try {
            equipmentTimeTableService.buildTimeTable(laboratoryAssistants, equipmentTimeTable, LocalDate.now(), 2);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        LocalDateTime startRentEquipment = LocalDateTime.of(2022, 2, 3, 19,25);
        LocalDateTime endRentEquipment = startRentEquipment.plus(12, ChronoUnit.MINUTES);

        OrderEquipment equipmentOrder = new OrderEquipment(1,1,startRentEquipment,endRentEquipment,1);
        LocalDateTime startRentEquipment3 = LocalDateTime.of(2022, 2, 4, 16,25);
        LocalDateTime endRentEquipment3 = LocalDateTime.of(2022, 2, 4, 18,25);

        OrderEquipment equipmentOrder2 = new OrderEquipment(1,1,startRentEquipment3,endRentEquipment3,1);
        List<OrderEquipment>  equipmentOrders = List.of(equipmentOrder, equipmentOrder2);

        LocalDateTime startRentEquipment2 = LocalDateTime.of(2022, 2, 3, 18,25);
        LocalDateTime endRentEquipment2 = LocalDateTime.of(2022, 2, 3, 18,55);
        OrderEquipment assistantOrder = new OrderEquipment(1,1,startRentEquipment2,endRentEquipment2,1);
        LocalDateTime startRentEquipment4 = LocalDateTime.of(2022, 2, 4, 11,25);
        LocalDateTime endRentEquipment4 = LocalDateTime.of(2022, 2, 4, 13,25);
        OrderEquipment assistantOrder2 = new OrderEquipment(1,1,startRentEquipment4,endRentEquipment4,1);

        LocalDateTime startRentEquipment5 = LocalDateTime.of(2022, 2, 4, 12,25);
        LocalDateTime endRentEquipment5 = LocalDateTime.of(2022, 2, 4, 14,25);
        OrderEquipment assistantOrder3 = new OrderEquipment(2,1,startRentEquipment5,endRentEquipment5,2);

        List<OrderEquipment>  firstAssistantOrders = List.of(assistantOrder, assistantOrder2);
        List<OrderEquipment>  secondAssistantOrders = List.of(assistantOrder3);

        Map<Long,List<OrderEquipment>> assistantsOrders = new HashMap<>();
        assistantsOrders.put(1L, firstAssistantOrders);
        assistantsOrders.put(2L, secondAssistantOrders);
        equipmentTimeTableService.setAvailability(equipmentTimeTable, equipmentOrders, assistantsOrders);

        System.out.println(equipmentTimeTable);

    }
}
