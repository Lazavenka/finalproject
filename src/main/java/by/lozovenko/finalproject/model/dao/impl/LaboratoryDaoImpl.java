package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.LaboratoryDao;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.mapper.impl.LaboratoryMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LaboratoryDaoImpl implements LaboratoryDao {
    private static LaboratoryDao instance;

    private static final String GET_LABORATORY_NAME_BY_ID = "SELECT laboratory_name FROM laboratories WHERE laboratory_id = ?";
    private static final String GET_LABORATORY_BY_ID = """
            SELECT laboratory_name, laboratory_id, department_id, laboratory_location, laboratory_photo_link, laboratory_description
            FROM laboratories WHERE laboratory_id = ?""";

    private static final String GET_LABORATORIES_BY_DEPARTMENT_ID = """
            SELECT laboratory_id, laboratory_name, department_id, laboratory_location, laboratory_photo_link, laboratory_description
            FROM laboratories WHERE department_id = ?""";

    private static final String GET_ALL_LABORATORIES = """
            SELECT laboratory_id, laboratory_name, department_id, laboratory_location, laboratory_photo_link, laboratory_description
            FROM laboratories""";

    private static final String GET_LABORATORY_BY_MANAGER_ID = """
            SELECT l.laboratory_name, l.laboratory_id, l.department_id, l.laboratory_location,
            l.laboratory_photo_link, l.laboratory_description FROM laboratories AS l
            JOIN managers AS m on m.laboratory_id = l.laboratory_id WHERE m.manager_id = ?""";
    private static final String CREATE_LABORATORY = """
            INSERT INTO laboratories (laboratory_name, department_id, laboratory_location, laboratory_description) 
            VALUES (?, ?, ?, ?)""";

    private static final String GET_LABS_WITHOUT_MANAGER = """
            SELECT laboratory_id, laboratory_name, department_id, laboratory_location,
            laboratory_photo_link, laboratory_description FROM laboratories
            WHERE NOT EXISTS(SELECT manager_id FROM managers WHERE managers.laboratory_id = laboratories.laboratory_id)""";
    private static final String COUNT_LABORATORIES = "SELECT count(laboratory_id) from laboratories";

    private LaboratoryDaoImpl() {
    }

    public static LaboratoryDao getInstance() {
        if (instance == null) {
            instance = new LaboratoryDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Laboratory> findAll() throws DaoException {
        List<Laboratory> laboratories = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LABORATORIES)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Laboratory laboratory = new Laboratory();
                    Optional<Laboratory> optionalUser = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
                    optionalUser.ifPresent(laboratories::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findAll method LaboratoryDao class. Unable to get access to database.", e);
        }
        return laboratories;
    }

    @Override
    public Optional<Laboratory> findEntityById(Long id) throws DaoException {
        Optional<Laboratory> optionalLaboratory = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABORATORY_BY_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Laboratory laboratory = new Laboratory();
                    optionalLaboratory = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
                    String loggerResult = optionalLaboratory.isPresent() ? String.format("Laboratory with id = %d was found.", id)
                            : String.format("Laboratory with id %d doesn't exist", id);
                    LOGGER.log(Level.INFO, "findEntityById completed successfully. {}", loggerResult);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findEntityById method LaboratoryDao class. Unable to get access to database.", e);
        }
        return optionalLaboratory;
    }

    @Override
    public boolean delete(Laboratory laboratory) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public long create(Laboratory laboratory) throws DaoException {
        long laboratoryId = -1;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement createLaboratoryStatement = connection.prepareStatement(CREATE_LABORATORY, Statement.RETURN_GENERATED_KEYS)) {
            createLaboratoryStatement.setString(1, laboratory.getName());
            createLaboratoryStatement.setLong(2, laboratory.getDepartmentId());
            createLaboratoryStatement.setString(3, laboratory.getLocation());
            createLaboratoryStatement.setString(4, laboratory.getDescription());
            createLaboratoryStatement.executeUpdate();
            try(ResultSet generatedIdResultSet = createLaboratoryStatement.getGeneratedKeys()) {
                if (generatedIdResultSet.next()) {
                    laboratoryId = generatedIdResultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in create method LaboratoryDao class. Unable to get access to database.", e);
        }
        return laboratoryId;
    }

    @Override
    public long update(Laboratory laboratory) throws DaoException {
        throw new UnsupportedOperationException("update(Laboratory laboratory) method is not supported");
    }

    @Override
    public List<Laboratory> findAllByDepartmentId(Long departmentId) throws DaoException {
        List<Laboratory> laboratories = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABORATORIES_BY_DEPARTMENT_ID)) {
            preparedStatement.setLong(1, departmentId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Laboratory laboratory = new Laboratory();
                    Optional<Laboratory> optionalLaboratory = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
                    optionalLaboratory.ifPresent(laboratories::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findAllByDepartmentId method LaboratoryDao class. Unable to get access to database.", e);
        }
        return laboratories;
    }

    @Override
    public Optional<Laboratory> findLaboratoryByManagerId(Long managerId) throws DaoException {
        Optional<Laboratory> optionalLaboratory = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABORATORY_BY_MANAGER_ID)) {
            preparedStatement.setLong(1, managerId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Laboratory laboratory = new Laboratory();
                    optionalLaboratory = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findLaboratoryByManagerId method LaboratoryDao class. Unable to get access to database.", e);
        }
        return optionalLaboratory;
    }

    @Override
    public Optional<String> findLaboratoryNameById(Long id) throws DaoException {
        Optional<String> optionalLaboratoryName = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABORATORY_NAME_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalLaboratoryName = Optional.of(resultSet.getString(LaboratoryMapper.LABORATORY_NAME));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findLaboratoryNameById method LaboratoryDao class. Unable to get access to database.", e);
        }
        return optionalLaboratoryName;
    }

    @Override
    public List<Laboratory> findLaboratoriesWithoutManager() throws DaoException {
        List<Laboratory> laboratoryList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABS_WITHOUT_MANAGER)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Laboratory laboratory = new Laboratory();
                    Optional<Laboratory> optionalLaboratory = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
                    optionalLaboratory.ifPresent(laboratoryList::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findLaboratoriesWithoutManager method LaboratoryDao class. Unable to get access to database.", e);
        }
        return laboratoryList;
    }

    @Override
    public long countLaboratories() throws DaoException {
        long count = 0;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_LABORATORIES)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in countLaboratories method LaboratoryDao class. Unable to get access to database.", e);
        }
        return count;
    }
}
