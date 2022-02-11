package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.EquipmentTypeDao;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.mapper.impl.EquipmentTypeMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipmentTypeDaoImpl implements EquipmentTypeDao {
    private static EquipmentTypeDao instance;

    private static final String GET_ALL_EQUIPMENT_TYPES = """
            SELECT equipment_type_id, equipment_type_name, equipment_type_description FROM equipment_types ORDER BY equipment_type_name
            """;
    private static final String GET_EQUIPMENT_TYPE_BY_ID = """
            SELECT equipment_type_id, equipment_type_name, equipment_type_description FROM equipment_types
            WHERE equipment_type_id = ?""";

    private static final String CREATE_EQUIPMENT_TYPE = "INSERT INTO equipment_types (equipment_type_name, equipment_type_description) VALUES (?, ?)";

    private EquipmentTypeDaoImpl() {
    }

    public static EquipmentTypeDao getInstance() {
        if (instance == null) {
            instance = new EquipmentTypeDaoImpl();
        }
        return instance;
    }

    @Override
    public List<EquipmentType> findAll() throws DaoException {
        List<EquipmentType> equipmentTypes = new ArrayList<>();
        EquipmentTypeMapper mapper = EquipmentTypeMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_EQUIPMENT_TYPES)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    EquipmentType equipmentType = new EquipmentType();
                    Optional<EquipmentType> optionalEquipmentType = mapper.rowMap(equipmentType, resultSet);
                    optionalEquipmentType.ifPresent(equipmentTypes::add);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Error in findAll method EquipmentTypeDao class. Unable to get access to database.", e);
        }
        return equipmentTypes;
    }

    @Override
    public Optional<EquipmentType> findEntityById(Long id) throws DaoException {
        Optional<EquipmentType> optionalEquipmentType = Optional.empty();
        EquipmentTypeMapper mapper = EquipmentTypeMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EQUIPMENT_TYPE_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    EquipmentType equipmentType = new EquipmentType();
                    optionalEquipmentType = mapper.rowMap(equipmentType, resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findEntityById method EquipmentTypeDao class. Unable to get access to database.", e);
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
        long equipmentTypeId = -1;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement createEquipmentTypeStatement = connection.prepareStatement(CREATE_EQUIPMENT_TYPE, Statement.RETURN_GENERATED_KEYS)) {
            createEquipmentTypeStatement.setString(1, equipmentType.getName());
            createEquipmentTypeStatement.setString(2, equipmentType.getDescription());

            createEquipmentTypeStatement.executeUpdate();
            try(ResultSet generatedIdResultSet = createEquipmentTypeStatement.getGeneratedKeys()) {
                if (generatedIdResultSet.next()) {
                    equipmentTypeId = generatedIdResultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in create method EquipmentTypeDao class. Unable to get access to database.", e);
        }
        return equipmentTypeId;
    }

    @Override
    public long update(EquipmentType equipmentType) throws DaoException {
        throw new UnsupportedOperationException("update(EquipmentType equipmentType) method is not supported");
    }
}
