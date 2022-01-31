package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserMapper implements CustomRowMapper<User> {

    public static final String USER_ID = "user_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String USER_ROLE = "user_role";
    public static final String USER_STATE = "user_state";

    public static final String USER_ID_COLUMN_NAME = "user_id";
    public static final String USER_TOKEN_COLUMN_NAME = "user_token";
    public static final String REGISTER_TIMESTAMP_COLUMN_NAME = "register_timestamp";


    private static UserMapper instance;

    public static UserMapper getInstance(){
        if (instance == null){
            instance = new UserMapper();
        }
        return instance;
    }

    @Override
    public Optional<User> rowMap(User user, ResultSet resultSet) throws DaoException {
        Optional<User> optionalUser;
        try {
            user.setId(resultSet.getLong(USER_ID));
            user.setLogin(resultSet.getString(LOGIN));
            user.setPassword(resultSet.getString(PASSWORD));
            user.setEmail(resultSet.getString(EMAIL));
            user.setPhone(resultSet.getString(PHONE_NUMBER));
            user.setFirstName(resultSet.getString(FIRST_NAME));
            user.setLastName(resultSet.getString(LAST_NAME));
            UserRole currentUserRole = UserRole.valueOf(resultSet.getString(USER_ROLE).toUpperCase().strip());
            user.setRole(currentUserRole);
            UserState currentUserState = UserState.valueOf(resultSet.getString(USER_STATE).toUpperCase().strip());
            user.setState(currentUserState);
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in user mapping class!", e);
            throw new DaoException(e);
        }
        return optionalUser;
    }
}
