package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends BaseDao<Long, Order> {
    List<Order> findAllOrdersByClientId(long clientId) throws DaoException;
    List<Order> findOrdersByLaboratoryId(long laboratoryId) throws DaoException;
    List<Order> findOrderByStateAndAssistantId(OrderState orderState, Long assistantId) throws DaoException;

    List<Order> findOrdersByEquipmentIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws DaoException;

    List<Order> findOrdersByAssistantIdAtPeriod(long assistantId, LocalDate startPeriod, LocalDate endPeriod) throws DaoException;

    int[] createOrders(List<Order> orderList) throws DaoException;

    boolean payOrder(long userId, BigDecimal newUserBalance, long orderId, OrderState newOrderState) throws DaoException;

    int updateOrderState(long orderId, OrderState orderState) throws DaoException;

    List<Order> findOrdersByStateAndAssistantIdSince(OrderState orderState, long assistantId, LocalDateTime localDateTime) throws DaoException;
}
