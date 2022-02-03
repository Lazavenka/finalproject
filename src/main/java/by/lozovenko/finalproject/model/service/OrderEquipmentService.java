package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.OrderEquipment;

import java.time.LocalDate;
import java.util.List;

public interface OrderEquipmentService {

    List<OrderEquipment> findOrderEquipmentByOrderId(long orderId) throws ServiceException;
    List<OrderEquipment> findPayedOrderEquipmentByAssistantId(long assistantId) throws ServiceException;
    List<OrderEquipment> findOrderEquipmentByAssistantIdAtPeriod(long assistantId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException;
    List<OrderEquipment> findOrderEquipmentByEquipmentIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException;

}
