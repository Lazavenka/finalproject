package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.OrderDao;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.mapper.impl.OrderMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static OrderDao instance;

    private static final String GET_ORDERS_BY_STATE_AND_ASSISTANT_ID = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end, order_assistant_id
            FROM orders WHERE order_state = ? AND order_assistant_id = ?""";
    private static final String GET_ORDERS_BY_CLIENT_ID = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end, order_assistant_id
            FROM orders WHERE client_id = ?""";
    private static final String GET_ORDERS_BY_LABORATORY_ID = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end,
            order_assistant_id FROM orders
            JOIN equipment ON orders.order_equipment_id = equipment.equipment_id WHERE equipment.laboratory_id = ?""";

    private OrderDaoImpl() {
    }

    public static OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }

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
    public long create(Order order) throws DaoException {
        return -1;
    }

    @Override
    public long update(Order order) throws DaoException {
        throw new UnsupportedOperationException("update(Order order) method is not supported");
    }

    @Override
    public List<Order> findAllOrdersByClientId(long clientId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_CLIENT_ID)) {
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                optionalOrder.ifPresent(orderList::add);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersByState(OrderState orderState) throws DaoException {
        return null;
    }

    @Override
    public List<Order> findOrdersByLaboratoryId(long laboratoryId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_LABORATORY_ID)) {
            preparedStatement.setLong(1, laboratoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                optionalOrder.ifPresent(orderList::add);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrderByStateAndAssistantId(OrderState orderState, Long assistantId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_STATE_AND_ASSISTANT_ID)) {
            preparedStatement.setLong(1, assistantId);
            preparedStatement.setString(2, orderState.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                optionalOrder.ifPresent(orderList::add);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orderList;
    }
}
