package by.lozovenko.finalproject.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.entity.OrderEquipment;
import by.lozovenko.finalproject.entity.User;

import java.util.List;

public interface OrderEquipmentDao extends BaseDao<Long, OrderEquipment> {
    List<OrderEquipment> findOrderEquipmentsByAssistant(User user) throws DaoException; //todo question Assistant or User?
}
