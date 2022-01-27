package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.DepartmentDao;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.mapper.impl.DepartmentMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoImpl implements DepartmentDao {

    private static final String GET_DEPARTMENT_NAME_BY_ID = "SELECT department_name FROM departments WHERE department_id = ?";
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
        return null;
    }

    @Override
    public Optional<Department> findEntityById(Long id) throws DaoException {
        return Optional.empty();
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
        return -1;
    }

    @Override
    public Department update(Department department) throws DaoException {
        return null;
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
            throw new DaoException(e);
        }
        return optionalDepartmentName;
    }
}
