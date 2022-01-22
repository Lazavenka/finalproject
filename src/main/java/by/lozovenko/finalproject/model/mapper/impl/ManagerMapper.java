package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.ManagerDegree;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ManagerMapper implements CustomRowMapper<User> {
    public static final String AVATAR_LINK = "avatar_link";
    public static final String MANAGER_ID = "manager_id";
    public static final String DESCRIPTION = "description";
    public static final String DEGREE = "degree";

    private static ManagerMapper instance;

    public static ManagerMapper getInstance(){
        if (instance == null){
            instance = new ManagerMapper();
        }
        return instance;
    }
    @Override
    public Optional<User> rowMap(User entity, ResultSet resultSet) throws DaoException {
        Optional<User> optionalUser;
        try {
            Manager manager = new Manager(entity);
            manager.setManagerId(resultSet.getLong(MANAGER_ID));
            ManagerDegree managerDegree = ManagerDegree.getDegreeByString(resultSet.getString(DEGREE));
            manager.setManagerDegree(managerDegree);
            manager.setDescription(resultSet.getString(DESCRIPTION));
            manager.setDepartmentId(resultSet.getLong(DepartmentMapper.DEPARTMENT_ID));
            manager.setLaboratoryId(resultSet.getLong(LaboratoryMapper.LABORATORY_ID));
            manager.setImageFilePath(resultSet.getString(AVATAR_LINK));
            optionalUser = Optional.of(manager);
        }catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in ManagerMapper class!", e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
