package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.TokenDao;
import by.lozovenko.finalproject.model.entity.CustomEntity;
import by.lozovenko.finalproject.model.entity.Token;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TokenDaoImpl implements TokenDao {
    private static TokenDao instance;

    private static final String USER_ID_COLUMN_NAME = "user_id";
    private static final String USER_TOKEN_COLUMN_NAME = "user_token";
    private static final String REGISTER_TIMESTAMP_COLUMN_NAME = "register_timestamp";

    private static final String CREATE_TOKEN = "INSERT INTO user_tokens (user_id, user_token, register_timestamp) values (?, ?, ?)";
    private static final String FIND_TOKEN_BY_VALUE = "SELECT user_id, user_token, register_timestamp FROM user_tokens WHERE user_token = ?";
    private static final String DELETE_TOKEN_BY_ID = "DELETE FROM user_tokens WHERE user_id = ?";

    private TokenDaoImpl(){
    }
    public static TokenDao getInstance(){
        if (instance == null){
            instance = new TokenDaoImpl();
        }
        return instance;
    }
    @Override
    public List<Token> findAll() throws DaoException {
        throw new UnsupportedOperationException("findAll() method is not supported");

    }

    @Override
    public Optional<Token> findEntityById(Long id) throws DaoException {
        throw new UnsupportedOperationException("findEntityById(Long id) method is not supported");
    }

    @Override
    public boolean delete(Token token) throws DaoException {
        throw new UnsupportedOperationException("delete(Token token) method is not supported");
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        boolean result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TOKEN_BY_ID)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public long create(Token token) throws DaoException {
        long result;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TOKEN)) {
            LOGGER.log(Level.DEBUG, "insert username = {}", token.getId());
            preparedStatement.setLong(1, token.getId());
            preparedStatement.setString(2, token.getValue());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(token.getRegisterDateTime()));
            result = preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Token update(Token token) throws DaoException {
        throw new UnsupportedOperationException("update(Token token) method is not supported");
    }

    @Override
    public Optional<Token> findTokenByValue(String tokenValue) throws DaoException{
        Optional<Token> optionalToken = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TOKEN_BY_VALUE)) {
            preparedStatement.setString(1, tokenValue);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                long userId = resultSet.getLong(USER_ID_COLUMN_NAME);
                String userToken = resultSet.getString(USER_TOKEN_COLUMN_NAME);
                LocalDateTime registerDateTime = resultSet.getTimestamp(REGISTER_TIMESTAMP_COLUMN_NAME).toLocalDateTime();
                Token token = new Token(userId, userToken, registerDateTime);
                optionalToken = Optional.of(token);
            }
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return optionalToken;
    }
}
