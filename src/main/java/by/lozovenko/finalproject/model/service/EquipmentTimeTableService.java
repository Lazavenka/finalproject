package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentTimeTable;
import by.lozovenko.finalproject.model.entity.OrderEquipment;

import java.time.LocalDate;
import java.util.List;

public interface EquipmentTimeTableService {
    void buildTimeTable(EquipmentTimeTable equipmentTimeTable) throws ServiceException;
    void buildTimeTable(EquipmentTimeTable equipmentTimeTable, LocalDate startDate, int daysCount) throws ServiceException;
    void setAvailability(EquipmentTimeTable equipmentTimeTable, List<OrderEquipment> equipmentOrders, List<OrderEquipment> assistantsOrders);
}
