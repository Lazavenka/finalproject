package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.UserDao;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.mapper.impl.UserMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;
import by.lozovenko.finalproject.util.PasswordEncryptor;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final UserMapper mapper = new UserMapper();
    private static UserDaoImpl instance;

    private static final String GET_USER_BY_LOGIN = """
            SELECT user_id, login, password, first_name, last_name, email,
            phone_number, user_role, user_state, first_name FROM users WHERE (login = ?)""";

    public static UserDaoImpl getInstance(){
        if (instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(User user) throws DaoException {
        return false;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public boolean updatePasswordByLogin(String password, String login) throws DaoException {
        return false;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                optionalUser = UserMapper.getInstance().rowMap(resultSet);
                String loggerResult = optionalUser.isPresent() ? String.format("User with id = %s was found.", optionalUser.get().getId())
                        : String.format("User with login %s doesn't exist", login);
                LOGGER.log(Level.INFO, "findUserByLogin completed successfully. {}", loggerResult);
            }
        } catch (SQLException e) {
            throw new DaoException("Database access error. Can't find user by login.", e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAllManagers() throws DaoException {
        return null;
    }

    @Override
    public List<User> findManagersByDepartment(Department department) throws DaoException {
        return null;
    }

    @Override
    public List<User> findManagersByDegree(ManagerDegree managerDegree) throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findManagerByName(String patternName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAllAssistants() throws DaoException {
        return null;
    }

    @Override
    public List<User> findAssistantsByEquipmentType(EquipmentType equipmentType) throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findAssistantByOrderEquipment(OrderEquipment orderEquipment) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAssistantsByOrder(Order order) throws DaoException {
        return null;
    }
}
