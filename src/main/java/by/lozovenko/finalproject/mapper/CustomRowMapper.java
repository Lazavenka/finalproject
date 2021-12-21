package by.lozovenko.finalproject.mapper;

import by.lozovenko.finalproject.entity.CustomEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface CustomRowMapper <T extends CustomEntity> {
    Optional<T> rowMap(ResultSet resultSet);
}
