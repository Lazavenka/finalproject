package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<Order> findOrdersByClientId(long userId) throws ServiceException;
    List<Order> findOrdersByEquipmentId(long equipmentId) throws ServiceException;
    List<Order> findOrdersByEquipmentIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException;
    List<Order> findOrdersByAssistantIdAtPeriod(long assistantId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException;
    List<Order> findPayedOrdersByAssistantIdFromNow(long assistantId) throws ServiceException;
}
