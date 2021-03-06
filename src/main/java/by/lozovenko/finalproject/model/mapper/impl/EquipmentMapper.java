package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentState;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;

public class EquipmentMapper implements CustomRowMapper<Equipment> {

    public static final String EQUIPMENT_ID = "equipment_id";
    public static final String EQUIPMENT_NAME = "equipment_name";
    public static final String PRICE_PER_HOUR = "price_per_hour";
    public static final String AVERAGE_RESEARCH_TIME = "average_research_time";
    public static final String EQUIPMENT_STATE = "equipment_state";
    public static final String IS_NEED_ASSISTANT = "is_need_assistant";
    public static final String PHOTO_LINK = "equipment_photo_link";
    public static final String EQUIPMENT_DESCRIPTION = "equipment_description";

    private static EquipmentMapper instance;

    private EquipmentMapper() {

    }

    public static EquipmentMapper getInstance() {
        if (instance == null) {
            instance = new EquipmentMapper();
        }
        return instance;
    }

    @Override
    public Optional<Equipment> rowMap(Equipment equipment, ResultSet resultSet) throws DaoException {
        Optional<Equipment> optionalEquipment;
        try {
            equipment.setId(resultSet.getLong(EQUIPMENT_ID));
            equipment.setEquipmentTypeId(resultSet.getLong(EquipmentTypeMapper.EQUIPMENT_TYPE_ID));
            equipment.setLaboratoryId(resultSet.getLong(LaboratoryMapper.LABORATORY_ID));
            equipment.setName(resultSet.getString(EQUIPMENT_NAME));
            equipment.setPricePerHour(resultSet.getBigDecimal(PRICE_PER_HOUR));
            equipment.setNeedAssistant(resultSet.getBoolean(IS_NEED_ASSISTANT));
            equipment.setDescription(resultSet.getString(EQUIPMENT_DESCRIPTION));
            EquipmentState state = EquipmentState.valueOf(resultSet.getString(EQUIPMENT_STATE).toUpperCase().strip());
            equipment.setState(state);
            LocalTime researchTime = resultSet.getTime(AVERAGE_RESEARCH_TIME).toLocalTime();
            equipment.setAverageResearchTime(researchTime);
            equipment.setImageFilePath(resultSet.getString(PHOTO_LINK));
            optionalEquipment = Optional.of(equipment);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in equipment mapping class! {}", e.getMessage());
            optionalEquipment = Optional.empty();
        }
        return optionalEquipment;
    }
}
