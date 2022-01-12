package by.lozovenko.finalproject.model.mapper;

import by.lozovenko.finalproject.model.entity.CustomEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface CustomRowMapper <T extends CustomEntity> {
    Optional<T> rowMap(ResultSet resultSet);
}
