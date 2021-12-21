package by.lozovenko.finalproject.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.entity.Department;
import by.lozovenko.finalproject.entity.Laboratory;
import by.lozovenko.finalproject.entity.Manager;

import java.util.List;
import java.util.Optional;

public interface LaboratoryDao extends BaseDao<Long, Laboratory> {
    List<Laboratory> findAllByDepartment(Department department) throws DaoException;

    Optional<Laboratory> findLaboratoryByManager(Manager manager) throws DaoException;
}
