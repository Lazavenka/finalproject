package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserMapper implements CustomRowMapper<User> {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String USER_ID = "user_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String USER_ROLE = "user_role";
    public static final String USER_STATE = "user_state";
    public static final String CLIENT_ID = "client_id";
    public static final String BALANCE = "balance";
    public static final String ASSISTANT_ID = "assistant_id";
    public static final String AVATAR_LINK = "avatar_link";
    public static final String MANAGER_ID = "manager_id";
    public static final String DESCRIPTION = "description";
    public static final String DEGREE = "degree";

    private static UserMapper instance;

    public static UserMapper getInstance(){
        if (instance == null){
            instance = new UserMapper();
        }
        return instance;
    }

    @Override
    public Optional<User> rowMap(ResultSet resultSet) { //FIXME not working result set
        User user = new User();
        Optional<User> optionalUser;
        try {
            user.setId(resultSet.getLong(USER_ID));
            user.setLogin(resultSet.getString(LOGIN));
            user.setPassword(resultSet.getString(PASSWORD));
            user.setEmail(resultSet.getString(EMAIL));
            user.setPhone(resultSet.getString(PHONE_NUMBER));
            user.setFirstName(resultSet.getString(FIRST_NAME));
            user.setLastName(resultSet.getString(LAST_NAME));
            UserRole currentUserRole = UserRole.valueOf(resultSet.getString(USER_ROLE).toUpperCase().strip());
            user.setRole(currentUserRole);
            UserState currentUserState = UserState.valueOf(resultSet.getString(USER_STATE).toUpperCase().strip());
            user.setState(currentUserState);
            switch (currentUserRole) {
                case ADMIN -> {
                    optionalUser = Optional.of(user);
                    return optionalUser;
                }
                case CLIENT -> {
                    Client client = new Client(user);
                    client.setBalance(resultSet.getBigDecimal(BALANCE));
                    client.setClientId(resultSet.getLong(CLIENT_ID));
                    optionalUser = Optional.of(client);
                }
                case ASSISTANT -> {
                    Assistant assistant = new Assistant(user);
                    assistant.setEquipmentTypeId(resultSet.getLong(EquipmentTypeMapper.EQUIPMENT_TYPE_ID));
                    assistant.setImageFilePath(resultSet.getString(AVATAR_LINK));
                    assistant.setAssistantId(resultSet.getLong(ASSISTANT_ID));
                    optionalUser = Optional.of(assistant);
                }
                case MANAGER -> {
                    Manager manager = new Manager(user);
                    manager.setManagerId(resultSet.getLong(MANAGER_ID));
                    ManagerDegree managerDegree = ManagerDegree.valueOf(resultSet.getString(DEGREE).toUpperCase().strip());
                    manager.setManagerDegree(managerDegree);
                    manager.setDescription(resultSet.getString(DESCRIPTION));
                    manager.setDepartmentId(resultSet.getLong(DepartmentMapper.DEPARTMENT_ID));
                    manager.setLaboratoryId(resultSet.getLong(LaboratoryMapper.LABORATORY_ID));
                    manager.setImageFilePath(resultSet.getString(AVATAR_LINK));
                    optionalUser = Optional.of(manager);
                }
                default -> throw new IllegalStateException("Unexpected value: " + currentUserRole);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in user mapping class!", e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
