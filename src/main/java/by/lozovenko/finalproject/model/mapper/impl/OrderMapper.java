package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.util.Optional;

public class OrderMapper implements CustomRowMapper<Order> {
    @Override
    public Optional<Order> rowMap(ResultSet resultSet) {
        return Optional.empty();
    }
}
