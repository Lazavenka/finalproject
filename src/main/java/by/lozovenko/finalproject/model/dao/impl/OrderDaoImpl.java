package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.OrderDao;
import by.lozovenko.finalproject.model.dao.OrderEquipmentDao;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.mapper.impl.OrderEquipmentMapper;
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

    private static final String GET_ORDER_EQUIPMENT_BY_STATE_AND_ASSISTANT_ID = """
            SELECT oe.order_id, oe.equipment_id, oe.rent_start, oe.rent_end, assistant_id FROM order_equipment AS oe
            JOIN orders AS o ON oe.order_id = o.order_id WHERE assistant_id = ? AND order_state = ?""";

    private OrderDaoImpl(){
    }
    public static OrderDao getInstance(){
        if (instance == null){
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
    public Order update(Order order) throws DaoException {
        return null;
    }

    @Override
    public List<Order> findAllOrdersByClientId(long clientId) throws DaoException {
        return null;
    }

    @Override
    public List<Order> findOrdersByState(OrderState orderState) throws DaoException {
        return null;
    }

    @Override
    public List<Order> findOrdersByLaboratoryId(long laboratoryId) throws DaoException {
        return null;
    }

    @Override
    public List<OrderEquipment> findOrderEquipmentByStateAndAssistantId(OrderState orderState, Long assistantId) throws DaoException {
        List<OrderEquipment> orderEquipmentList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_EQUIPMENT_BY_STATE_AND_ASSISTANT_ID)){
            preparedStatement.setLong(1, assistantId);
            preparedStatement.setString(2, orderState.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                OrderEquipment orderEquipment = new OrderEquipment();
                Optional<OrderEquipment> optionalOrderEquipment = OrderEquipmentMapper.getInstance().rowMap(orderEquipment, resultSet);
                optionalOrderEquipment.ifPresent(orderEquipmentList::add);
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return orderEquipmentList;
    }
}
