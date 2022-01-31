package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.EquipmentTypeDao;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.mapper.impl.EquipmentTypeMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipmentTypeDaoImpl implements EquipmentTypeDao {
    private static EquipmentTypeDao instance;

    private static final String GET_ALL_EQUIPMENT_TYPES = """
    SELECT equipment_type_id, equipment_type_name, equipment_type_description FROM equipment_types
    """;
    private static final String GET_EQUIPMENT_TYPE_BY_ID = """
    SELECT equipment_type_id, equipment_type_name, equipment_type_description FROM equipment_types
    WHERE equipment_type_id = ?""";

    private EquipmentTypeDaoImpl(){
    }

    public static EquipmentTypeDao getInstance(){
        if (instance == null){
            instance = new EquipmentTypeDaoImpl();
        }
        return instance;
    }
    @Override
    public List<EquipmentType> findAll() throws DaoException {
        List<EquipmentType> equipmentTypes = new ArrayList<>();
        EquipmentTypeMapper mapper = EquipmentTypeMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_EQUIPMENT_TYPES)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                EquipmentType equipmentType = new EquipmentType();
                Optional<EquipmentType> optionalEquipmentType = mapper.rowMap(equipmentType, resultSet);
                optionalEquipmentType.ifPresent(equipmentTypes::add);
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return equipmentTypes;
    }

    @Override
    public Optional<EquipmentType> findEntityById(Long id) throws DaoException {
        Optional<EquipmentType> optionalEquipmentType = Optional.empty();
        EquipmentTypeMapper mapper = EquipmentTypeMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EQUIPMENT_TYPE_BY_ID)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                EquipmentType equipmentType = new EquipmentType();
                optionalEquipmentType = mapper.rowMap(equipmentType, resultSet);
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return optionalEquipmentType;
    }

    @Override
    public boolean delete(EquipmentType equipmentType) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public long create(EquipmentType equipmentType) throws DaoException {
        return 0;
    }

    @Override
    public EquipmentType update(EquipmentType equipmentType) throws DaoException {
        return null;
    }
}
