package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EquipmentTypeMapper implements CustomRowMapper<EquipmentType> {

    public static final String EQUIPMENT_TYPE_ID = "equipment_type_id";
    public static final String EQUIPMENT_TYPE_NAME = "equipment_type_name";
    public static final String EQUIPMENT_TYPE_DESCRIPTION = "equipment_type_description";
    private static EquipmentTypeMapper instance;

    private EquipmentTypeMapper() {

    }

    public static EquipmentTypeMapper getInstance() {
        if (instance == null) {
            instance = new EquipmentTypeMapper();
        }
        return instance;
    }
    @Override
    public Optional<EquipmentType> rowMap(EquipmentType equipmentType, ResultSet resultSet) throws DaoException {
        Optional<EquipmentType> optionalEquipmentType;
        try {
            equipmentType.setId(resultSet.getLong(EQUIPMENT_TYPE_ID));
            equipmentType.setName(resultSet.getString(EQUIPMENT_TYPE_NAME));
            equipmentType.setDescription(resultSet.getString(EQUIPMENT_TYPE_DESCRIPTION));
            optionalEquipmentType = Optional.of(equipmentType);
        }catch (SQLException e) {
            LOGGER.log(Level.WARN, "Mapping error in EquipmentTypeMapperClass ", e);
            optionalEquipmentType = Optional.empty();
        }

        return optionalEquipmentType;
    }
}
