package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.LaboratoryDao;
import by.lozovenko.finalproject.model.dao.OrderEquipmentDao;
import by.lozovenko.finalproject.model.entity.Assistant;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.OrderEquipment;
import by.lozovenko.finalproject.model.mapper.impl.EquipmentMapper;
import by.lozovenko.finalproject.model.mapper.impl.OrderEquipmentMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderEquipmentDaoImpl implements OrderEquipmentDao {
    private static OrderEquipmentDao instance;
    private static final String GET_ALL_ORDER_EQUIPMENT_BY_ORDER_ID = """
                    SELECT order_id, equipment_id, rent_start,
                    rent_end, assistant_id FROM order_equipment WHERE order_id = ?""";




    private OrderEquipmentDaoImpl(){
    }
    public static OrderEquipmentDao getInstance(){
        if (instance == null){
            instance = new OrderEquipmentDaoImpl();
        }
        return instance;
    }
    @Override
    public List<OrderEquipment> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<OrderEquipment> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(OrderEquipment orderEquipment) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public long create(OrderEquipment orderEquipment) throws DaoException {
        return 0;
    }

    @Override
    public OrderEquipment update(OrderEquipment orderEquipment) throws DaoException {
        return null;
    }

    @Override
    public List<OrderEquipment> findOrderEquipmentsByAssistant(Assistant assistant) throws DaoException {
        return null;
    }

    @Override
    public List<OrderEquipment> findOrderEquipmentsByOrderId(long orderId) throws DaoException {
        List<OrderEquipment> orderEquipmentList = new ArrayList<>();
        OrderEquipmentMapper mapper = OrderEquipmentMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDER_EQUIPMENT_BY_ORDER_ID)){
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                OrderEquipment orderEquipment = new OrderEquipment();
                Optional<OrderEquipment> optionalOrderEquipment = mapper.rowMap(orderEquipment, resultSet);
                optionalOrderEquipment.ifPresent(orderEquipmentList::add);
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findOrderEquipmentsByOrderId method OrderEquipmentDao class. Unable to get access to database.", e);
        }
        return orderEquipmentList;
    }
}
