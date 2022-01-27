package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Assistant;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AssistantMapper implements CustomRowMapper<User> {
    public static final String ASSISTANT_ID = "assistant_id";
    public static final String AVATAR_LINK = "avatar_link";

    private static AssistantMapper instance;

    public static AssistantMapper getInstance(){
        if (instance == null){
            instance = new AssistantMapper();
        }
        return instance;
    }
    @Override
    public Optional<User> rowMap(User entity, ResultSet resultSet) throws DaoException {
        Optional<User> optionalUser;
        try {
            Assistant assistant = new Assistant(entity);
            assistant.setLaboratoryId(resultSet.getLong(LaboratoryMapper.LABORATORY_ID));
            assistant.setImageFilePath(resultSet.getString(AVATAR_LINK));
            assistant.setAssistantId(resultSet.getLong(ASSISTANT_ID));
            optionalUser = Optional.of(assistant);
        }catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in AssistantMapper class!", e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
