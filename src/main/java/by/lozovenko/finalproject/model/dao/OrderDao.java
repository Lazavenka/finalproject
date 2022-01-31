package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.*;

import java.util.List;

public interface OrderDao extends BaseDao<Long, Order> {
    List<Order> findAllOrdersByUser(Client client) throws DaoException;
    List<Order> findOrdersByState(OrderState orderState) throws DaoException;
    List<OrderEquipment> findOrderEquipmentByStateAndAssistantId(OrderState orderState, Long assistantId) throws DaoException;
}
