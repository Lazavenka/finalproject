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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.lozovenko.finalproject.model.mapper.impl.ClientMapper.BALANCE;
import static by.lozovenko.finalproject.model.mapper.impl.UserMapper.*;

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
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT user_id, login, password, first_name, last_name, email,
            phone_number, user_role, user_state FROM users WHERE login = ? AND password = ?""";
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
    private static final String GET_MANAGER_BY_LABORATORY_ID = """
            SELECT u.user_id, u.first_name, u.last_name, u.login, u.password, u.email, u.phone_number, u.user_role,
            u.user_state, m.manager_id, m.department_id, m.laboratory_id, m.avatar_link,
            m.description, m.degree FROM users AS u
            JOIN managers AS m ON m.user_id = u.user_id WHERE u.user_role = 'MANAGER' and m.laboratory_id = ?""";
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
    private static final String GET_ASSISTANTS_BY_LABORATORY_ID = """
            SELECT u.user_id, login, password, first_name, last_name, email, phone_number, user_role,
            user_state, a.avatar_link, a.laboratory_id, a.assistant_id FROM users AS u
            JOIN assistants AS a ON u.user_id = a.user_id WHERE a.laboratory_id = ?""";

    private static final String GET_USER_ROLE_BY_LOGIN = "SELECT user_role FROM users WHERE (login = ?)";

    private static final String CREATE_USER = """
            INSERT INTO users (login, password, first_name, last_name, email, phone_number, user_role, user_state)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

    private static final String CREATE_MANAGER = """
            INSERT INTO managers (user_id, department_id, laboratory_id, avatar_link, description, degree)
            VALUES (?, ?, ?, ?, ?, ?)""";

    private static final String CREATE_CLIENT = "INSERT INTO clients (user_id) VALUES (?)";
    private static final String CREATE_TOKEN = "INSERT INTO user_tokens (user_id, user_token, register_timestamp) values (?, ?, ?)";
    private static final String FIND_TOKEN_BY_VALUE = "SELECT user_id, user_token, register_timestamp FROM user_tokens WHERE user_token = ?";
    private static final String DELETE_TOKEN_BY_ID = "DELETE FROM user_tokens WHERE user_id = ?";

    private static final String UPDATE_USER_STATE_BY_ID = "UPDATE users SET user_state = ? WHERE user_id = ?";
    private static final String UPDATE_USER_PASSWORD_BY_LOGIN = "UPDATE users SET password = ? WHERE login = ?";
    private static final String UPDATE_USER_PROFILE_BY_USER_ID = "UPDATE users SET first_name = ?, last_name = ?, phone_number = ? WHERE user_id = ?";
    private static final String UPDATE_CLIENT_BALANCE_BY_ID = "UPDATE clients SET balance = ? WHERE user_id = ?";
    private static final String UPDATE_MANAGER_AVATAR_BY_USER_ID = "UPDATE managers SET avatar_link = ? WHERE user_id = ?";
    private static final String UPDATE_MANAGER_DESCRIPTION_BY_USER_ID = "UPDATE managers SET description = ? WHERE user_id = ?";
    private static final String UPDATE_ASSISTANT_AVATAR_BY_USER_ID = "UPDATE assistants SET avatar_link = ? WHERE user_id = ?";

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
            throw new DaoException("Error in findAll method UserDao class. Unable to get access to database.", e);
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
            throw new DaoException("Error in findEntityById method UserDao class. Unable to get access to database.", e);
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
            setUserColumnsInStatement(user, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Error in create method UserDao class. Unable to get access to database.", e);
        }
        return result;
    }

    private void setUserColumnsInStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPhone());
        preparedStatement.setString(7, user.getRole().name());
        preparedStatement.setString(8, user.getState().name());
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public boolean updatePasswordByLogin(String password, String login) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PASSWORD_BY_LOGIN)){
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, login);
            return preparedStatement.executeUpdate() != 0;
        }catch (SQLException e){
            throw new DaoException("Error in updatePasswordByLogin method UserDao class. Unable to get access to database.", e);
        }
    }

    @Override
    public long createManager(Manager manager) throws DaoException {
        long generatedManagerId = 0;
        Connection connection = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement createUserStatement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement createManagerStatement = connection.prepareStatement(CREATE_MANAGER, Statement.RETURN_GENERATED_KEYS)) {
                setUserColumnsInStatement(manager, createUserStatement);
                createUserStatement.executeUpdate();
                ResultSet userResultSet = createUserStatement.getGeneratedKeys();
                long userId = 0;
                if (userResultSet.next()) {
                    userId = userResultSet.getLong(1);
                    createManagerStatement.setLong(1, userId);
                    createManagerStatement.setLong(2, manager.getDepartmentId());
                    createManagerStatement.setLong(3, manager.getLaboratoryId());
                    createManagerStatement.setString(4, manager.getImageFilePath());
                    createManagerStatement.setString(5, manager.getDescription());
                    createManagerStatement.setString(6, manager.getManagerDegree().getValue());
                    createManagerStatement.executeUpdate();
                    ResultSet managerResultSet = createManagerStatement.getResultSet();
                    if (managerResultSet.next()) {
                        generatedManagerId = managerResultSet.getLong(1);
                    }
                }
                connection.commit();
                LOGGER.log(Level.INFO, "createManager method complete successfully. Generated userId = {}, managerId = {}. Returned managerId", userId, generatedManagerId);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Change cancellation error in createManager transaction:", throwables);
            }
            throw new DaoException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Database access error occurs:", throwables);
            }
        }
        return generatedManagerId;
    }

    @Override
    public long createClient(Client client, Token token) throws DaoException {
        long generatedClientId = -1;
        Connection connection = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement createUserStatement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement createClientStatement = connection.prepareStatement(CREATE_CLIENT, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement createTokenStatement = connection.prepareStatement(CREATE_TOKEN, Statement.RETURN_GENERATED_KEYS)) {
                setUserColumnsInStatement(client, createUserStatement);
                createUserStatement.executeUpdate();
                ResultSet resultSet = createUserStatement.getGeneratedKeys();
                long userId = 0;
                long tokenId = 0;
                if (resultSet.next()) {
                    userId = resultSet.getLong(1);
                    createClientStatement.setLong(1, userId);
                    createClientStatement.executeUpdate();
                    ResultSet clientResultSet = createClientStatement.getGeneratedKeys();
                    if (clientResultSet.next()) {
                        generatedClientId = clientResultSet.getLong(1);
                    }
                    createTokenStatement.setLong(1, userId);
                    createTokenStatement.setString(2, token.getValue());
                    createTokenStatement.setTimestamp(3, Timestamp.valueOf(token.getRegisterDateTime()));
                    createTokenStatement.executeUpdate();

                    ResultSet tokenResultSet = createTokenStatement.getGeneratedKeys();
                    if (tokenResultSet.next()) {
                        tokenId = clientResultSet.getLong(1);
                    }
                }
                connection.commit();
                LOGGER.log(Level.INFO, "createClient method complete successfully. Generated userId = {}, clientId = {}, tokenId = {}. Returned clientId", userId, generatedClientId, tokenId);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Change cancellation error in createClient transaction:", throwables);
            }
            throw new DaoException("Error in createClient method. Impossible to insert client into database.", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Database access error occurs:", throwables);
            }
        }
        return generatedClientId;
    }

    @Override
    public Optional<Token> findUserTokenByValue(String tokenValue) throws DaoException {
        Optional<Token> optionalToken = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TOKEN_BY_VALUE)) {
            preparedStatement.setString(1, tokenValue);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long userId = resultSet.getLong(USER_ID_COLUMN_NAME);
                String userToken = resultSet.getString(USER_TOKEN_COLUMN_NAME);
                LocalDateTime registerDateTime = resultSet.getTimestamp(REGISTER_TIMESTAMP_COLUMN_NAME).toLocalDateTime();
                Token token = new Token(userId, userToken, registerDateTime);
                optionalToken = Optional.of(token);
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findUserTokenByValue method UserDao class. Unable to get access to database.", e);
        }
        return optionalToken;
    }

    @Override
    public boolean confirmUserRegistration(Long userId) throws DaoException {
        boolean result;
        Connection connection = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement updateUserStatement = connection.prepareStatement(UPDATE_USER_STATE_BY_ID);
                 PreparedStatement deleteTokenStatement = connection.prepareStatement(DELETE_TOKEN_BY_ID)) {
                updateUserStatement.setString(1, UserState.ACTIVE.name());
                updateUserStatement.setLong(2, userId);
                boolean userUpdate = updateUserStatement.executeUpdate() != 0;
                deleteTokenStatement.setLong(1, userId);
                boolean tokenDelete = deleteTokenStatement.executeUpdate() != 0;
                connection.commit();
                result = userUpdate && tokenDelete;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Change cancellation error in confirmUserRegistration transaction:", throwables);
            }
            throw new DaoException("Error in confirmUserRegistration method. Impossible to update userState and delete user token from database.", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Database access error occurs:", throwables);
            }
        }
        return result;
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
            throw new DaoException("Error in findUserRoleByLogin method. Can't find userRole by login.", e);
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
            throw new DaoException("Error in findUserByLogin method. Can't find user by login.", e);
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

            result = preparedStatement.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new DaoException("Error in updateUserStateById method. Can't update user. Database access error.", e);
        }
        return result;

    }

    public Optional<User> findUserByEmail(String email) throws DaoException {
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
        } catch (SQLException e) {
            throw new DaoException("Error in findUserByEmail method. Can't find user by email. Database access error.", e);
        }
        return optionalUser;
    }

    @Override
    public boolean isExistUserWithEmail(String email) throws DaoException {
        boolean result = false;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException("Error in isExistUserWithEmail method UserDao class. Unable to get access to database.", e);
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
            if (resultSet.next()) {
                BigDecimal balance = resultSet.getBigDecimal(BALANCE);
                optionalBalance = Optional.of(balance);
            }
        } catch (SQLException e) {
            throw new DaoException("Error in checkUserBalanceByUserId method. Can't find clientBalance by id. Database access error.", e);
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

            result = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Error in updateUserBalanceById method. Can't update clientBalance by id. Database access error.", e);
        }
        return result;
    }

    @Override
    public Optional<User> findManagerByLaboratoryId(long laboratoryId) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MANAGER_BY_LABORATORY_ID)) {
            preparedStatement.setLong(1, laboratoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                UserMapper.getInstance().rowMap(user, resultSet);
                optionalUser = ManagerMapper.getInstance().rowMap(user, resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findManagerByLaboratoryId method. Can't find Manager by managerId. Database access error.", e);
        }
        return optionalUser;
    }

    @Override
    public int updateAssistantAvatarPath(long id, String path) throws DaoException {
        int result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ASSISTANT_AVATAR_BY_USER_ID)) {
            preparedStatement.setString(1, path);
            preparedStatement.setLong(2, id);

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error in updateManagerAvatarPath method. Database access error.", e);
        }
        return result;
    }

    @Override
    public int updateManagerAvatarPath(long id, String path) throws DaoException {
        int result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MANAGER_AVATAR_BY_USER_ID)) {
            preparedStatement.setString(1, path);
            preparedStatement.setLong(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error in updateManagerAvatarPath method. Database access error.", e);
        }
        return result;
    }

    @Override
    public List<User> findAssistantByLaboratoryId(long laboratoryId) throws DaoException {
        List<User> assistants = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
        PreparedStatement getAssistantStatement = connection.prepareStatement(GET_ASSISTANTS_BY_LABORATORY_ID)){
            getAssistantStatement.setLong(1, laboratoryId);
            ResultSet resultSet = getAssistantStatement.executeQuery();
            getAssistantsFromResultSet(assistants, resultSet);
            LOGGER.log(Level.INFO, "findAssistantByLaboratoryId (laboratoryId = {}) found {} assistants in database", laboratoryId, assistants.size());
        }catch (SQLException e){
            throw new DaoException("Error in findAssistantByLaboratoryId method. Can't find Assistants in database. Database access error.", e);
        }
        return assistants;
    }

    @Override
    public boolean isExistUser(String login, String encryptedPassword) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, encryptedPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        }catch (SQLException e){
            throw new DaoException("Error in isExistUser method. Can't find Assistants in database. Database access error.", e);
        }
    }

    @Override
    public int updateManagerDescriptionByUserId(long id, String description) throws DaoException {
        int result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MANAGER_DESCRIPTION_BY_USER_ID)) {
            preparedStatement.setString(1, description);
            preparedStatement.setLong(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error in updateManagerDescriptionByUserId method. Database access error.", e);
        }
        return result;
    }

    @Override
    public int updateUserDataById(long id, String lastName, String firstName, String phone) throws DaoException {
        int result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PROFILE_BY_USER_ID)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phone);
            preparedStatement.setLong(4, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error in updateManagerDescriptionByUserId method. Database access error.", e);
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
            throw new DaoException("Error in findAllManagers method. Can't find Managers in database. Database access error.", e);
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
            throw new DaoException("Error in findManagersByDepartmentId method. Can't find Managers by departmentId. Database access error.", e);
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
            throw new DaoException("Error in findManagersByDegree method. Can't find Managers by degree. Database access error.", e);
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

    private void getAssistantsFromResultSet(List<User> assistants, ResultSet resultSet) throws SQLException, DaoException {
        while (resultSet.next()) {
            User assistant = new User();
            UserMapper.getInstance().rowMap(assistant, resultSet);
            Optional<User> optionalUser = AssistantMapper.getInstance().rowMap(assistant, resultSet);
            optionalUser.ifPresent(assistants::add);
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
            throw new DaoException("Error in findManagerById method. Can't find Manager by managerId. Database access error.", e);
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
    public Optional<User> findAssistantByOrderEquipment(Order order) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findAssistantByOrder(Order order) throws DaoException {
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
            throw new DaoException("Database access error. Can't handle with addClientColumns method.", e);
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
            throw new DaoException("Database access error. Can't handle with addManagersColumns method.", e);
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
            throw new DaoException("Database access error. Can't handle with addAssistantColumns method.", e);
        }
        return optionalUser;
    }
}
