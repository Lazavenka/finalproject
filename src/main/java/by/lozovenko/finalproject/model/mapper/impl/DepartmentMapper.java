package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DepartmentMapper implements CustomRowMapper<Department> {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String DEPARTMENT_ID = "department_id";
    public static final String DEPARTMENT_NAME = "department_name";
    public static final String DEPARTMENT_DESCRIPTION = "department_description";
    public static final String DEPARTMENT_ADDRESS = "department_address";

    @Override
    public Optional<Department> rowMap(ResultSet resultSet) {
        Department department = new Department();
        Optional<Department> optionalDepartment;
        try {
            department.setId(resultSet.getLong(DEPARTMENT_ID));
            department.setName(resultSet.getString(DEPARTMENT_NAME));
            department.setDescription(resultSet.getString(DEPARTMENT_DESCRIPTION));
            department.setAddress(resultSet.getString(DEPARTMENT_ADDRESS));

            optionalDepartment = Optional.of(department);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in department mapping class! {}", e.getMessage());
            optionalDepartment = Optional.empty();
        }
        return optionalDepartment;
    }
}
