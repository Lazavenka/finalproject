package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.lozovenko.finalproject.model.mapper.impl.DepartmentMapper.DEPARTMENT_ID;

public class LaboratoryMapper implements CustomRowMapper<Laboratory> {

    public static final String LABORATORY_ID = "laboratory_id";
    public static final String LABORATORY_NAME = "laboratory_name";
    public static final String LABORATORY_LOCATION = "laboratory_location";
    public static final String LABORATORY_PHOTO_LINK = "laboratory_photo_link";
    public static final String LABORATORY_DESCRIPTION = "laboratory_description";

    private static LaboratoryMapper instance;

    public static LaboratoryMapper getInstance() {
        if (instance == null) {
            instance = new LaboratoryMapper();
        }
        return instance;
    }

    @Override
    public Optional<Laboratory> rowMap(Laboratory laboratory, ResultSet resultSet) throws DaoException {
        Optional<Laboratory> optionalLaboratory;
        try {
            laboratory.setId(resultSet.getLong(LABORATORY_ID));
            laboratory.setName(resultSet.getString(LABORATORY_NAME));
            laboratory.setDescription(resultSet.getString(LABORATORY_DESCRIPTION));
            laboratory.setLocation(resultSet.getString(LABORATORY_LOCATION));
            laboratory.setImageFilePath(resultSet.getString(LABORATORY_PHOTO_LINK));
            laboratory.setDepartmentId(resultSet.getLong(DEPARTMENT_ID));
            optionalLaboratory = Optional.of(laboratory);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in laboratory mapping class! {}", e.getMessage());
            optionalLaboratory = Optional.empty();
        }
        return optionalLaboratory;
    }
}
