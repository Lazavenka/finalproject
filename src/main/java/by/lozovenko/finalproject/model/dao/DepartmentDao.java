package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.BaseDao;
import by.lozovenko.finalproject.model.entity.Department;

import java.util.Optional;

public interface DepartmentDao extends BaseDao<Long, Department> {

    Optional<String> findDepartmentNameById(Long id) throws DaoException;
}
