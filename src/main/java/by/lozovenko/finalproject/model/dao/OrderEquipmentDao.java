package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Assistant;
import by.lozovenko.finalproject.model.entity.OrderEquipment;
import by.lozovenko.finalproject.model.entity.User;

import java.util.List;

public interface OrderEquipmentDao extends BaseDao<Long, OrderEquipment> {
    List<OrderEquipment> findOrderEquipmentsByAssistant(Assistant assistant) throws DaoException; //todo question Assistant or User?
    List<OrderEquipment> findOrderEquipmentsByOrderId(long orderId) throws DaoException; //todo question Assistant or User?
}
