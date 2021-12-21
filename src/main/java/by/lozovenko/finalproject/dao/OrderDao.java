package by.lozovenko.finalproject.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.entity.Order;
import by.lozovenko.finalproject.entity.User;

import java.util.List;

public interface OrderDao extends BaseDao<Long, Order> {
    List<Order> findAllOrdersByUser(User client) throws DaoException;
}
