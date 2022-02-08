package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.DepartmentDao;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.mapper.impl.DepartmentMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoImpl implements DepartmentDao {

    private static final String GET_DEPARTMENT_NAME_BY_ID = "SELECT department_name FROM departments WHERE department_id = ?";
    private static final String GET_DEPARTMENT_BY_ID = "SELECT department_id, department_name, department_description, department_address FROM departments WHERE department_id = ?";
    private static final String GET_ALL_DEPARTMENTS = "SELECT department_id, department_name, department_description, department_address FROM departments";
    private static final String CREATE_DEPARTMENT = "INSERT INTO departments (department_name, department_description, department_address) VALUES (?, ?, ?)";
    private static final String COUNT_DEPARTMENTS = "SELECT count(department_id) from departments";

    private static DepartmentDao instance;

    private DepartmentDaoImpl() {
    }

    public static DepartmentDao getInstance() {
        if (instance == null) {
            instance = new DepartmentDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Department> findAll() throws DaoException {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DEPARTMENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Department department = new Department();
                Optional<Department> optionalUser = DepartmentMapper.getInstance().rowMap(department, resultSet);
                optionalUser.ifPresent(departments::add);
            }
        }catch (SQLException e){
            throw new DaoException("Error in findAll method DepartmentDao class. Unable to get access to database.", e);
        }
        return departments;
    }

    @Override
    public Optional<Department> findEntityById(Long id) throws DaoException {
        Optional<Department> optionalDepartment = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Department department = new Department();
                optionalDepartment = DepartmentMapper.getInstance().rowMap(department, resultSet);
                String loggerResult = optionalDepartment.isPresent() ? String.format("Department with id = %d was found.", id)
                        : String.format("Department with id %d doesn't exist", id);
                LOGGER.log(Level.INFO, "findEntityById completed successfully. {}", loggerResult);
            }
        }catch (SQLException e){
            throw new DaoException("Error in findEntityById method DepartmentDao class. Unable to get access to database.", e);
        }
        return optionalDepartment;
    }

    @Override
    public boolean delete(Department department) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public long create(Department department) throws DaoException {
        long departmentId = -1;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement createDepartmentStatement = connection.prepareStatement(CREATE_DEPARTMENT, Statement.RETURN_GENERATED_KEYS)){

            createDepartmentStatement.setString(1, department.getName());
            createDepartmentStatement.setString(2, department.getDescription());
            createDepartmentStatement.setString(3, department.getAddress());

            createDepartmentStatement.executeUpdate();
            ResultSet generatedIdResultSet = createDepartmentStatement.getGeneratedKeys();
            if (generatedIdResultSet.next()){
                departmentId = generatedIdResultSet.getLong(1);
            }
        }catch (SQLException e){
            throw new DaoException("Error in create method DepartmentDao class. Unable to get access to database.", e);
        }
        return departmentId;
    }

    @Override
    public long update(Department department) throws DaoException {
        throw new UnsupportedOperationException("update(Department department) method is not supported");
    }

    @Override
    public Optional<String> findDepartmentNameById(Long id) throws DaoException {
        Optional<String> optionalDepartmentName;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENT_NAME_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalDepartmentName = Optional.of(resultSet.getString(DepartmentMapper.DEPARTMENT_NAME));
            } else {
                optionalDepartmentName = Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findDepartmentNameById method DepartmentDao class. Unable to get access to database.", e);
        }
        return optionalDepartmentName;
    }

    @Override
    public long countDepartments() throws DaoException {
        long count = 0;
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_DEPARTMENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                count = resultSet.getInt(1);
            }
        }catch (SQLException e){
            throw new DaoException("Error in countDepartments method DepartmentDao class. Unable to get access to database.", e);
        }
        return count;
    }
}
