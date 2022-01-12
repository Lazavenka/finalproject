package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.util.Optional;

public class EquipmentTypeMapper implements CustomRowMapper<EquipmentType> {

    public static final String EQUIPMENT_TYPE_ID = "equipment_type_id";
    public static final String EQUIPMENT_TYPE_NAME = "equipment_type_name";
    public static final String EQUIPMENT_TYPE_DESCRIPTION = "equipment_type_description";

    @Override
    public Optional<EquipmentType> rowMap(ResultSet resultSet) {
        return Optional.empty();
    }
}
