package by.lozovenko.finalproject.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.entity.CustomEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends CustomEntity> {
    Logger LOGGER = LogManager.getLogger();

    List<T> findAll() throws DaoException;

    Optional<T> findEntityById(K id) throws DaoException;

    boolean delete(T t) throws DaoException;

    boolean deleteById(K id) throws DaoException;

    boolean create(T t) throws DaoException;

    T update(T t) throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Error while close statement! {}", e.getMessage());
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Error while close connection! {}", e.getMessage());
        }
    }
}
