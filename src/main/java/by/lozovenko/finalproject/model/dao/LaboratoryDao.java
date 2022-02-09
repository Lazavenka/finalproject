package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.entity.Manager;

import java.util.List;
import java.util.Optional;

public interface LaboratoryDao extends BaseDao<Long, Laboratory> {
    List<Laboratory> findAllByDepartmentId(Long departmentId) throws DaoException;

    Optional<Laboratory> findLaboratoryByManagerId(Long managerId) throws DaoException;

    Optional<String> findLaboratoryNameById(Long id) throws DaoException;

    List<Laboratory> findLaboratoriesWithoutManager() throws DaoException;

    long countLaboratories() throws DaoException;

    int updateLaboratoryPhoto(long id, String databasePath) throws DaoException;
}
