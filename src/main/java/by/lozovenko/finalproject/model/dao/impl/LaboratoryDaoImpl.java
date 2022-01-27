package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.LaboratoryDao;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.mapper.impl.LaboratoryMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LaboratoryDaoImpl implements LaboratoryDao {
    private static LaboratoryDao instance;

    private static final String GET_LABORATORY_NAME_BY_ID = "SELECT laboratory_name FROM laboratories WHERE laboratory_id = ?";
    private static final String GET_LABORATORY_BY_ID = """
            SELECT laboratory_name, department_id, laboratory_location, laboratory_photo_link, laboratory_description
            FROM laboratories WHERE laboratory_id = ?""";

    private static final String GET_LABORATORIES_BY_DEPARTMENT_ID = """
            SELECT laboratory_name, department_id, laboratory_location, laboratory_photo_link, laboratory_description
            FROM laboratories WHERE department_id = ?""";

    private static final String GET_ALL_LABORATORIES = """
            SELECT laboratory_name, department_id, laboratory_location, laboratory_photo_link, laboratory_description
            FROM laboratories""";

    private static final String GET_LABORATORY_BY_MANAGER_ID = """
            SELECT l.laboratory_name, l.laboratory_id, l.department_id, l.laboratory_location,
            l.laboratory_photo_link, l.laboratory_description FROM laboratories AS l
            JOIN managers AS m on m.laboratory_id = l.laboratory_id WHERE m.manager_id = ?""";

    private LaboratoryDaoImpl(){
    }
    public static LaboratoryDao getInstance(){
        if (instance == null){
            instance = new LaboratoryDaoImpl();
        }
        return instance;
    }
    @Override
    public List<Laboratory> findAll() throws DaoException {
        List<Laboratory> laboratories = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LABORATORIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Laboratory laboratory = new Laboratory();
                Optional<Laboratory> optionalUser = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
                optionalUser.ifPresent(laboratories::add);
            }
        }catch (SQLException e){
            throw new DaoException(e); //fixme message
        }
        return laboratories;
    }

    @Override
    public Optional<Laboratory> findEntityById(Long id) throws DaoException {
        Optional<Laboratory> optionalLaboratory = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABORATORY_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Laboratory laboratory = new Laboratory();
                optionalLaboratory = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
                String loggerResult = optionalLaboratory.isPresent() ? String.format("User with id = %d was found.", id)
                        : String.format("User with id %d doesn't exist", id);
                LOGGER.log(Level.INFO, "findUserById completed successfully. {}", loggerResult);
            }
        }catch (SQLException e){
            throw new DaoException(e); //fixme message
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
        return -1;
    }

    @Override
    public Laboratory update(Laboratory laboratory) throws DaoException {
        return null;
    }

    @Override
    public List<Laboratory> findAllByDepartmentId(Long departmentId) throws DaoException {
        List<Laboratory> laboratories = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABORATORIES_BY_DEPARTMENT_ID)) {
            preparedStatement.setLong(1, departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Laboratory laboratory = new Laboratory();
                Optional<Laboratory> optionalUser = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
                optionalUser.ifPresent(laboratories::add);
            }
        }catch (SQLException e){
            throw new DaoException(e); //fixme message
        }
        return laboratories;
    }

    @Override
    public Optional<Laboratory> findLaboratoryByManagerId(Long managerId) throws DaoException {
        Optional<Laboratory> optionalLaboratory = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABORATORY_BY_MANAGER_ID)) {
            preparedStatement.setLong(1, managerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Laboratory laboratory = new Laboratory();
                optionalLaboratory = LaboratoryMapper.getInstance().rowMap(laboratory, resultSet);
            }
        }catch (SQLException e){
            throw new DaoException(e); //fixme message
        }
        return optionalLaboratory;
    }

    @Override
    public Optional<String> findLaboratoryNameById(Long id) throws DaoException {
        Optional<String> optionalLaboratoryName;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LABORATORY_NAME_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                optionalLaboratoryName = Optional.of(resultSet.getString(LaboratoryMapper.LABORATORY_NAME));
            }else {
                optionalLaboratoryName = Optional.empty();
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return optionalLaboratoryName;
    }
}
