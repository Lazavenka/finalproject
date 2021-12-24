package by.lozovenko.finalproject.dao.impl;

import by.lozovenko.finalproject.dao.OrderDao;
import by.lozovenko.finalproject.entity.Order;
import by.lozovenko.finalproject.entity.User;
import by.lozovenko.finalproject.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Order order) throws DaoException {
        return false;
    }

    @Override
    public Order update(Order order) throws DaoException {
        return null;
    }

    @Override
    public List<Order> findAllOrdersByUser(User client) throws DaoException {
        return null;
    }
}
