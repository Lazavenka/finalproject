package by.lozovenko.finalproject.model.mapper;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.CustomEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.Optional;

public interface CustomRowMapper <T extends CustomEntity> {
    Logger LOGGER = LogManager.getLogger();
    Optional<T> rowMap(T entity, ResultSet resultSet) throws DaoException;
}
