package by.lozovenko.finalproject.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.dao.EquipmentDao;
import by.lozovenko.finalproject.entity.Equipment;
import by.lozovenko.finalproject.entity.EquipmentState;
import by.lozovenko.finalproject.entity.EquipmentType;
import by.lozovenko.finalproject.entity.Laboratory;
import by.lozovenko.finalproject.mapper.impl.EquipmentMapper;
import by.lozovenko.finalproject.pool.CustomConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipmentDaoImpl implements EquipmentDao {

    private static final String SELECT_ALL_EQUIPMENT = """
            SELECT equipment_id, equipment_type_id, laboratory_id, equipment_name, equipment_description,
            price_per_hour, average_research_time, is_need_assistant, equipment_state, equipment_photo_link FROM equipment""";
    private static final String SELECT_EQUIPMENT_BY_ID = """
            SELECT equipment_id, equipment_type_id, laboratory_id, equipment_name, equipment_description,
            price_per_hour, average_research_time, is_need_assistant, equipment_state,
            equipment_photo_link FROM equipment WHERE equipment_id = (?)""";
    private static final String SQL_INSERT_NEW_EQUIPMENT_ITEM = """
            INSERT INTO equipment(equipment_id, equipment_type_id, laboratory_id, equipment_name, equipment_description,
            price_per_hour, average_research_time, is_need_assistant, equipment_state,
            equipment_photo_link) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

    @Override
    public List<Equipment> findAll() throws DaoException {
        List<Equipment> equipmentList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = CustomConnectionPool.getInstance().getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_EQUIPMENT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Optional<Equipment> optionalEquipment = new EquipmentMapper().rowMap(resultSet);
                optionalEquipment.ifPresent(equipmentList::add);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(connection);
            close(preparedStatement);
        }
        return equipmentList;
    }

    @Override
    public Optional<Equipment> findEntityById(Long id) throws DaoException {
        Optional<Equipment> optionalEquipment;
        PreparedStatement preparedStatement = null;
        Connection connection = CustomConnectionPool.getInstance().getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_EQUIPMENT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            optionalEquipment = new EquipmentMapper().rowMap(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(connection);
            close(preparedStatement);
        }
        return optionalEquipment;
    }

    @Override
    public boolean delete(Equipment equipment) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Equipment equipment) throws DaoException {
        PreparedStatement preparedStatement = null;
        Connection connection = CustomConnectionPool.getInstance().getConnection();
        boolean result = false;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_EQUIPMENT_ITEM);
            preparedStatement.setLong(1, equipment.getId());
            preparedStatement.setLong(2, equipment.getEquipmentTypeId());
            preparedStatement.setLong(3, equipment.getLaboratoryId());
            preparedStatement.setString(4, equipment.getName());
            preparedStatement.setString(5, equipment.getDescription());
            preparedStatement.setBigDecimal(6, equipment.getPricePerHour());
            preparedStatement.setTime(7, Time.valueOf(equipment.getAverageResearchTime()));
            preparedStatement.setBoolean(8, equipment.isNeedAssistant());
            preparedStatement.setString(9, String.valueOf(equipment.getState()));
            preparedStatement.setString(10, equipment.getImageFilePath());
            result = preparedStatement.executeUpdate() != 0;
        }catch (SQLException e){
            throw new DaoException(e);
        }finally {
            close(connection);
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public Equipment update(Equipment equipment) throws DaoException {
        return null;
    }

    @Override
    public boolean updateEquipmentStateById(Long id, EquipmentState state) throws DaoException {
        return false;
    }

    @Override
    public List<Equipment> findEquipmentByType(EquipmentType type) throws DaoException {
        return null;
    }

    @Override
    public List<Equipment> findEquipmentByLaboratory(Laboratory laboratory) throws DaoException {
        return null;
    }

    @Override
    public Optional<Equipment> findEquipmentByName(String patternName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Equipment> findAllActiveEquipmentByLaboratory(Laboratory laboratory) throws DaoException {
        return null;
    }
}
