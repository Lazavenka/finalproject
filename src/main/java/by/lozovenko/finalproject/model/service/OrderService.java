package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.entity.OrderState;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findOrdersByClientId(long userId) throws ServiceException;

    List<Order> findOrdersByEquipmentIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException;

    List<Order> findOrdersByAssistantIdAtPeriod(long assistantId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException;

    List<Order> findPayedOrdersByAssistantIdFromNow(long assistantId) throws ServiceException;

    boolean createOrders(long clientId, String isNeedAssistant, String[] orderDateAssistant, Equipment selectedEquipment) throws ServiceException;

    OrderPaymentCode payOrder(long userId, String orderIdString) throws ServiceException;

    boolean updateOrderStateById(String orderIdString, OrderState orderState) throws ServiceException;

    List<Order> findOrdersByLaboratoryId(long laboratoryId) throws ServiceException;

    Optional<Order> findOrderById(String orderIdString) throws ServiceException;

    Optional<Order> findOrderById(long orderId) throws ServiceException;
}
