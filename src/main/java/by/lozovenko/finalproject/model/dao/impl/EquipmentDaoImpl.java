package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.EquipmentDao;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentState;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.mapper.impl.EquipmentMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipmentDaoImpl implements EquipmentDao {

    private static EquipmentDao instance;

    private static final String GET_ALL_EQUIPMENT = """
            SELECT equipment_id, equipment_type_id, laboratory_id, equipment_name, equipment_description,
            price_per_hour, average_research_time, is_need_assistant, equipment_state, equipment_photo_link FROM equipment""";
    private static final String GET_EQUIPMENT_BY_ID = """
            SELECT equipment_id, equipment_type_id, laboratory_id, equipment_name, equipment_description,
            price_per_hour, average_research_time, is_need_assistant, equipment_state,
            equipment_photo_link FROM equipment WHERE equipment_id = (?)""";

    private static final String GET_EQUIPMENT_BY_TYPE = """
            SELECT equipment_id, equipment_type_id, laboratory_id, equipment_name, equipment_description,
            price_per_hour, average_research_time, is_need_assistant, equipment_state,
            equipment_photo_link FROM equipment WHERE equipment_type_id = (?)""";

    private static final String GET_EQUIPMENT_BY_LABORATORY_ID = """
            SELECT equipment_id, equipment_type_id, laboratory_id, equipment_name, equipment_description,
            price_per_hour, average_research_time, is_need_assistant, equipment_state,
            equipment_photo_link FROM equipment WHERE laboratory_id = (?)""";

    private static final String CREATE_NEW_EQUIPMENT_ITEM = """
            INSERT INTO equipment(equipment_type_id, laboratory_id, equipment_name, equipment_description,
            price_per_hour, average_research_time, is_need_assistant, equipment_state,
            equipment_photo_link) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)""";

    private static final String UPDATE_EQUIPMENT_PHOTO_BY_ID = "UPDATE equipment SET equipment_photo_link = ? WHERE equipment_id = ?";
    private static final String UPDATE_EQUIPMENT = """
            UPDATE equipment SET equipment_type_id = ?, laboratory_id = ?, equipment_name = ?, equipment_description = ?,
            price_per_hour = ?, average_research_time = ?, is_need_assistant = ?, equipment_state = ?
            WHERE equipment_id = ?""";
    private static final String COUNT_EQUIPMENT = "SELECT count(equipment_id) from equipment";

    private EquipmentDaoImpl() {
    }

    public static EquipmentDao getInstance() {
        if (instance == null) {
            instance = new EquipmentDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Equipment> findAll() throws DaoException {
        List<Equipment> equipmentList = new ArrayList<>();
        EquipmentMapper mapper = EquipmentMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_EQUIPMENT)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Equipment equipment = new Equipment();
                    Optional<Equipment> optionalEquipment = mapper.rowMap(equipment, resultSet);
                    optionalEquipment.ifPresent(equipmentList::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findAll method EquipmentDao class. Unable to get access to database.", e);
        }
        return equipmentList;
    }

    @Override
    public Optional<Equipment> findEntityById(Long id) throws DaoException {
        Optional<Equipment> optionalEquipment = Optional.empty();
        EquipmentMapper mapper = EquipmentMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EQUIPMENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Equipment equipment = new Equipment();
                    optionalEquipment = mapper.rowMap(equipment, resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findEntityById method EquipmentDao class. Unable to get access to database.", e);
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
    public long create(Equipment equipment) throws DaoException {
        long result = -1;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_EQUIPMENT_ITEM, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, equipment.getEquipmentTypeId());
            preparedStatement.setLong(2, equipment.getLaboratoryId());
            preparedStatement.setString(3, equipment.getName());
            preparedStatement.setString(4, equipment.getDescription());
            preparedStatement.setBigDecimal(5, equipment.getPricePerHour());
            preparedStatement.setTime(6, Time.valueOf(equipment.getAverageResearchTime()));
            preparedStatement.setBoolean(7, equipment.isNeedAssistant());
            preparedStatement.setString(8, String.valueOf(equipment.getState()));
            preparedStatement.setString(9, equipment.getImageFilePath());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    result = generatedKey.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Error in create method EquipmentDao class. Unable to create entity into database.", e);
        }
        return result;
    }

    @Override
    public long update(Equipment equipment) throws DaoException {
        long result = -1;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EQUIPMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, equipment.getEquipmentTypeId());
            preparedStatement.setLong(2, equipment.getLaboratoryId());
            preparedStatement.setString(3, equipment.getName());
            preparedStatement.setString(4, equipment.getDescription());
            preparedStatement.setBigDecimal(5, equipment.getPricePerHour());
            preparedStatement.setTime(6, Time.valueOf(equipment.getAverageResearchTime()));
            preparedStatement.setBoolean(7, equipment.isNeedAssistant());
            preparedStatement.setString(8, String.valueOf(equipment.getState()));
            preparedStatement.setLong(9, equipment.getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                result = generatedKey.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Error in update method EquipmentDao class. Unable to create entity into database.", e);
        }
        return result;
    }

    @Override
    public boolean updateEquipmentStateById(Long id, EquipmentState state) throws DaoException {
        return false;
    }

    @Override
    public List<Equipment> findEquipmentByType(EquipmentType type) throws DaoException {
        List<Equipment> equipmentList = new ArrayList<>();
        EquipmentMapper mapper = EquipmentMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EQUIPMENT_BY_TYPE)) {
            preparedStatement.setLong(1, type.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Equipment equipment = new Equipment();
                    Optional<Equipment> optionalEquipment = mapper.rowMap(equipment, resultSet);
                    optionalEquipment.ifPresent(equipmentList::add);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Error in findEquipmentByType method EquipmentDao class. Unable to get access to database.", e);
        }
        return equipmentList;
    }

    @Override
    public List<Equipment> findEquipmentByLaboratoryId(long laboratoryId) throws DaoException {
        List<Equipment> equipmentList = new ArrayList<>();
        EquipmentMapper mapper = EquipmentMapper.getInstance();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EQUIPMENT_BY_LABORATORY_ID)) {
            preparedStatement.setLong(1, laboratoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Equipment equipment = new Equipment();
                    Optional<Equipment> optionalEquipment = mapper.rowMap(equipment, resultSet);
                    optionalEquipment.ifPresent(equipmentList::add);
                }
            }
            LOGGER.log(Level.INFO, "findEquipmentByLaboratoryId (laboratoryId = {}) method found {} items", laboratoryId, equipmentList.size());
        } catch (SQLException e) {
            throw new DaoException("Error in findEquipmentByLaboratoryId method EquipmentDao class. Unable to get access to database.", e);
        }
        return equipmentList;
    }

    @Override
    public Optional<Equipment> findEquipmentByName(String patternName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Equipment> findAllActiveEquipmentByLaboratory(Laboratory laboratory) throws DaoException {
        return null;
    }

    @Override
    public int updateEquipmentPhoto(long id, String databasePath) throws DaoException {
        int result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EQUIPMENT_PHOTO_BY_ID)) {
            preparedStatement.setString(1, databasePath);
            preparedStatement.setLong(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error in updateEquipmentPhoto method. Database access error.", e);
        }
        return result;
    }

    @Override
    public long countEquipment() throws DaoException {
        long count = 0;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_EQUIPMENT)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in countEquipment method EquipmentDao class. Unable to get access to database.", e);
        }
        return count;
    }
}
