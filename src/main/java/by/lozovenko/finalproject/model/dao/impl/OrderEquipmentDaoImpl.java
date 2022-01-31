package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.LaboratoryDao;
import by.lozovenko.finalproject.model.dao.OrderEquipmentDao;
import by.lozovenko.finalproject.model.entity.Assistant;
import by.lozovenko.finalproject.model.entity.OrderEquipment;

import java.util.List;
import java.util.Optional;

public class OrderEquipmentDaoImpl implements OrderEquipmentDao {
    private static OrderEquipmentDao instance;





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
}
