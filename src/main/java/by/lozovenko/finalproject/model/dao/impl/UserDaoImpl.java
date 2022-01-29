package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.UserDao;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.mapper.impl.AssistantMapper;
import by.lozovenko.finalproject.model.mapper.impl.ClientMapper;
import by.lozovenko.finalproject.model.mapper.impl.ManagerMapper;
import by.lozovenko.finalproject.model.mapper.impl.UserMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.model.mapper.impl.ClientMapper.BALANCE;

public class UserDaoImpl implements UserDao {
    private static UserDao instance;
    private static final String GET_ALL_USERS = """
            SELECT user_id, login, password, first_name, last_name, email,
            phone_number, user_role, user_state FROM users""";
    private static final String GET_USER_BY_ID = """
            SELECT user_id, login, password, first_name, last_name, email,
            phone_number, user_role, user_state FROM users WHERE (user_id = ?)""";
    private static final String GET_USER_BY_LOGIN = """
            SELECT user_id, login, password, first_name, last_name, email,
            phone_number, user_role, user_state FROM users WHERE (login = ?)""";
    private static final String GET_USER_BY_EMAIL = """
            SELECT user_id, login, password, first_name, last_name, email,
            phone_number, user_role, user_state FROM users WHERE (email = ?)""";
    private static final String GET_MANAGER_COLUMNS_BY_USER_ID = """
            SELECT manager_id, user_id, department_id, laboratory_id, avatar_link,
            description, degree FROM managers WHERE (user_id = ?)""";
    private static final String GET_ASSISTANT_COLUMNS_BY_USER_ID = """
            SELECT assistant_id, user_id, avatar_link,
            laboratory_id FROM assistants WHERE (user_id = ?)""";
    private static final String GET_CLIENT_COLUMNS_BY_USER_ID = "SELECT client_id, user_id, balance FROM clients WHERE (user_id = ?)";
    private static final String GET_CLIENT_BALANCE_BY_USER_ID = "SELECT balance FROM clients WHERE (user_id = ?)";
    private static final String GET_ALL_MANAGERS = """
            SELECT u.user_id, u.first_name, u.last_name, u.login, u.password, u.email, u.phone_number, u.user_role,
            u.user_state, m.manager_id, m.department_id, m.laboratory_id, m.avatar_link,
            m.description, m.degree FROM users AS u
            JOIN managers AS m ON m.user_id = u.user_id WHERE u.user_role = 'MANAGER'""";
    private static final String GET_MANAGERS_BY_DEPARTMENT_ID = """
            SELECT u.user_id, u.first_name, u.last_name, u.login, u.password, u.email, u.phone_number, u.user_role,
            u.user_state, m.manager_id, m.department_id, m.laboratory_id, m.avatar_link,
            m.description, m.degree FROM users AS u
            JOIN managers AS m ON m.user_id = u.user_id WHERE u.user_role = 'MANAGER' and m.department_id = ?""";
    private static final String GET_MANAGER_BY_ID = """
            SELECT u.user_id, u.first_name, u.last_name, u.login, u.password, u.email, u.phone_number, u.user_role,
            u.user_state, m.manager_id, m.department_id, m.laboratory_id, m.avatar_link,
            m.description, m.degree FROM users AS u
            JOIN managers AS m ON m.user_id = u.user_id WHERE u.user_role = 'MANAGER' and m.manager_id = ?""";
    private static final String GET_MANAGERS_BY_DEGREE = """
            SELECT u.user_id, u.first_name, u.last_name, u.login, u.password, u.email, u.phone_number, u.user_role,
            u.user_state, m.manager_id, m.department_id, m.laboratory_id, m.avatar_link,
            m.description, m.degree FROM users AS u
            JOIN managers AS m ON m.user_id = u.user_id WHERE u.user_role = 'MANAGER' and m.degree = ?""";

    private static final String GET_USER_ROLE_BY_LOGIN = "SELECT user_role FROM users WHERE (login = ?)";

    private static final String CREATE_USER = """
            INSERT INTO users (login, password, first_name, last_name, email, phone_number, user_role, user_state)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

    private static final String CREATE_MANAGER = """
            INSERT INTO managers (user_id, department_id, laboratory_id, avatar_link, description, degree)
            VALUES (?, ?, ?, ?, ?, ?)""";

    private static final String CREATE_CLIENT = "INSERT INTO clients (user_id) VALUES (?)";

    private static final String UPDATE_USER_STATE_BY_ID = "UPDATE users SET user_state = ? WHERE user_id = ?";
    private static final String UPDATE_CLIENT_BALANCE_BY_ID = "UPDATE clients SET balance = ? WHERE user_id = ?";

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                Optional<User> optionalUser = UserMapper.getInstance().rowMap(user, resultSet);
                optionalUser.ifPresent(users::add);
            }
        } catch (SQLException e) {
            throw new DaoException(e); //fixme message
        }
        return users;
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                optionalUser = UserMapper.getInstance().rowMap(user, resultSet);
                if (optionalUser.isPresent()) {
                    UserRole role = user.getRole();
                    optionalUser = addColumnsByUserRole(user, role, connection);
                }
                String loggerResult = optionalUser.isPresent() ? String.format("User with id = %d was found.", id)
                        : String.format("User with id %d doesn't exist", id);
                LOGGER.log(Level.INFO, "findUserById completed successfully. {}", loggerResult);
            }
        } catch (SQLException e) {
            throw new DaoException(e); //fixme message
        }
        return optionalUser;
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
    public long create(User user) throws DaoException {
        long result = -1;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getRole().name());
            preparedStatement.setString(8, user.getState().name());
            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
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
    public long createManager(Long userId, Manager manager) throws DaoException {  //fixme
        long generatedUserId = -1;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_MANAGER, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, manager.getDepartmentId());
            preparedStatement.setLong(3, manager.getLaboratoryId());
            preparedStatement.setString(4, manager.getImageFilePath());
            preparedStatement.setString(5, manager.getDescription());
            preparedStatement.setString(6, manager.getManagerDegree().getValue());
            preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getLong(1);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return generatedUserId;
    }

    @Override
    public long createClient(Long userId) throws DaoException {
        long generatedClientId = -1;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                generatedClientId = generatedKey.getLong(1);
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return generatedClientId;
    }

    @Override
    public UserRole findUserRoleByLogin(String login) throws DaoException {
        UserRole role;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ROLE_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = UserRole.valueOf(resultSet.getString(UserMapper.USER_ROLE));
            } else {
                role = UserRole.GUEST;
            }
        } catch (SQLException e) {
            throw new DaoException("Database access error. Can't find user by login.", e);
        }
        return role;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                optionalUser = UserMapper.getInstance().rowMap(user, resultSet);
                if (optionalUser.isPresent()) {
                    UserRole role = user.getRole();
                    optionalUser = addColumnsByUserRole(user, role, connection);
                }
                String loggerResult = optionalUser.isPresent() ? String.format("User with id = %s was found.", user.getId())
                        : String.format("User with login %s doesn't exist", login);
                LOGGER.log(Level.INFO, "findUserByLogin completed successfully. {}", loggerResult);
            }
        } catch (SQLException e) {
            throw new DaoException("Database access error. Can't find user by login.", e);
        }
        return optionalUser;
    }

    @Override
    public boolean updateUserStateById(UserState userState, Long userId) throws DaoException {
        boolean result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_STATE_BY_ID)) {
            preparedStatement.setString(1, userState.name());
            preparedStatement.setLong(2, userId);

            result = preparedStatement.executeUpdate() > 0;

        }catch (SQLException e){
            throw new DaoException(e);
        }
        return result;

    }
    public Optional<User> findUserByEmail(String email) throws DaoException{
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                optionalUser = UserMapper.getInstance().rowMap(user, resultSet);
                if (optionalUser.isPresent()) {
                    UserRole role = user.getRole();
                    optionalUser = addColumnsByUserRole(user, role, connection);
                }
                String loggerResult = optionalUser.isPresent() ? String.format("User with email = %s was found.", user.getId())
                        : String.format("User with email %s doesn't exist", email);
                LOGGER.log(Level.INFO, "findUserByEmail completed successfully. {}", loggerResult);
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public boolean isExistUserWithEmail(String email) throws DaoException {
        boolean result = false;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)){
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                result = true;
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<BigDecimal> checkUserBalanceByUserId(Long userId) throws DaoException {
        Optional<BigDecimal> optionalBalance = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_BALANCE_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                BigDecimal balance = resultSet.getBigDecimal(BALANCE);
                optionalBalance = Optional.of(balance);
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return optionalBalance;
    }

    @Override
    public boolean updateUserBalanceById(Long userId, BigDecimal newBalance) throws DaoException {
        boolean result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_BALANCE_BY_ID)) {
            preparedStatement.setBigDecimal(1, newBalance);
            preparedStatement.setLong(2, userId);

            result = preparedStatement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAllManagers() throws DaoException {
        List<User> managers = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_MANAGERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            getManagersFromResultSet(managers, resultSet);
            LOGGER.log(Level.INFO, "findAllManagers found {} managers in database", managers.size());
        } catch (SQLException e) {
            throw new DaoException(e); //fixme message
        }
        return managers;
    }

    @Override
    public List<User> findManagersByDepartmentId(Long departmentId) throws DaoException {
        List<User> managers = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MANAGERS_BY_DEPARTMENT_ID)) {
            preparedStatement.setLong(1, departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            getManagersFromResultSet(managers, resultSet);
            LOGGER.log(Level.INFO, "findManagersById (id = {}) found {} managers in database", departmentId, managers.size());
        } catch (SQLException e) {
            throw new DaoException(e); //fixme message
        }
        return managers;
    }

    @Override
    public List<User> findManagersByDegree(ManagerDegree managerDegree) throws DaoException {
        List<User> managers = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MANAGERS_BY_DEGREE)) {
            preparedStatement.setString(1, managerDegree.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            getManagersFromResultSet(managers, resultSet);
            LOGGER.log(Level.INFO, "findManagersByDegree (degree = {}) found {} managers in database", managerDegree.getValue(), managers.size());
        } catch (SQLException e) {
            throw new DaoException(e); //fixme message
        }
        return managers;
    }

    private void getManagersFromResultSet(List<User> managers, ResultSet resultSet) throws SQLException, DaoException {
        while (resultSet.next()) {
            User manager = new User();
            UserMapper.getInstance().rowMap(manager, resultSet);
            Optional<User> optionalUser = ManagerMapper.getInstance().rowMap(manager, resultSet);
            optionalUser.ifPresent(managers::add);
        }
    }

    @Override
    public Optional<User> findManagerByName(String patternName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findManagerById(Long managerId) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MANAGER_BY_ID)) {
            preparedStatement.setLong(1, managerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                UserMapper.getInstance().rowMap(user, resultSet);
                optionalUser = ManagerMapper.getInstance().rowMap(user, resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e); //fixme message
        }
        return optionalUser;
    }


    @Override
    public List<User> findAllAssistants() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findAssistantById(Long assistantId) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAssistantsByEquipmentType(EquipmentType equipmentType) throws DaoException {
        return Collections.emptyList();
    }

    @Override
    public Optional<User> findAssistantByOrderEquipment(OrderEquipment orderEquipment) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAssistantsByOrder(Order order) throws DaoException {
        return null;
    }


    private Optional<User> addColumnsByUserRole(User user, UserRole role, Connection connection) throws DaoException {
        Optional<User> userOptional;
        switch (role) {
            case MANAGER -> userOptional = addManagersColumns(connection, user);
            case ASSISTANT -> userOptional = addAssistantColumns(connection, user);
            case CLIENT -> userOptional = addClientColumns(connection, user);
            case ADMIN -> {
                return Optional.of(user);
            }
            default -> throw new IllegalStateException("Unexpected value: " + role);
        }
        return userOptional;
    }

    private Optional<User> addClientColumns(Connection connection, User user) throws DaoException {
        Optional<User> optionalUser = Optional.empty();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_COLUMNS_BY_USER_ID)) {
            long userId = user.getId();
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalUser = ClientMapper.getInstance().rowMap(user, resultSet);
            } else {
                LOGGER.log(Level.INFO, "No columns for Client with user id = {} found", userId);
            }
        } catch (SQLException e) {
            throw new DaoException("Database access error. Can't find user by login.", e);
        }
        return optionalUser;
    }

    private Optional<User> addManagersColumns(Connection connection, User user) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_MANAGER_COLUMNS_BY_USER_ID)) {
            long userId = user.getId();
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalUser = ManagerMapper.getInstance().rowMap(user, resultSet);
            } else {
                LOGGER.log(Level.INFO, "No columns for Manager with user id = {} found", userId);
            }
        } catch (SQLException e) {
            throw new DaoException("Database access error. Can't find user by login.", e);
        }
        return optionalUser;
    }

    private Optional<User> addAssistantColumns(Connection connection, User user) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ASSISTANT_COLUMNS_BY_USER_ID)) {
            long userId = user.getId();
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalUser = AssistantMapper.getInstance().rowMap(user, resultSet);
            } else {
                LOGGER.log(Level.INFO, "No columns for Assistant with user id = {} found", userId);
            }
        } catch (SQLException e) {
            throw new DaoException("Database access error. Can't find user by login.", e);
        }
        return optionalUser;
    }
}
