package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.model.entity.OrderEquipment;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.util.Optional;

public class OrderEquipmentMapper implements CustomRowMapper<OrderEquipment> {
    @Override
    public Optional<OrderEquipment> rowMap(ResultSet resultSet) {
        return Optional.empty();
    }
}