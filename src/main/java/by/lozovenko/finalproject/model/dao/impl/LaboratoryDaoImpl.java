package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.LaboratoryDao;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class LaboratoryDaoImpl implements LaboratoryDao {
    @Override
    public List<Laboratory> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Laboratory> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Laboratory laboratory) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Laboratory laboratory) throws DaoException {
        return false;
    }

    @Override
    public Laboratory update(Laboratory laboratory) throws DaoException {
        return null;
    }

    @Override
    public List<Laboratory> findAllByDepartment(Department department) throws DaoException {
        return null;
    }

    @Override
    public Optional<Laboratory> findLaboratoryByManager(Manager manager) throws DaoException {
        return Optional.empty();
    }
}
