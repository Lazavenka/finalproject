package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EquipmentTimeTableService {
    void buildTimeTable(List<Assistant> laboratoryAssistants, EquipmentTimeTable equipmentTimeTable) throws ServiceException;

    void buildTimeTable(List<Assistant> laboratoryAssistants, EquipmentTimeTable equipmentTimeTable, LocalDate startDate, int daysCount) throws ServiceException;

    void setAvailability(EquipmentTimeTable equipmentTimeTable, List<Order> equipmentOrders, Map<Long, List<Order>> assistantsOrders);
}
