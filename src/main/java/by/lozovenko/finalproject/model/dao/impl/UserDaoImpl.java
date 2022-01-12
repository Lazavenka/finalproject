package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.UserDao;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(User user) throws DaoException {
        return false;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public boolean updatePasswordByLogin(String password, String login) throws DaoException {
        return false;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAllManagers() throws DaoException {
        return null;
    }

    @Override
    public List<User> findManagersByDepartment(Department department) throws DaoException {
        return null;
    }

    @Override
    public List<User> findManagersByDegree(ManagerDegree managerDegree) throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findManagerByName(String patternName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAllAssistants() throws DaoException {
        return null;
    }

    @Override
    public List<User> findAssistantsByEquipmentType(EquipmentType equipmentType) throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findAssistantByOrderEquipment(OrderEquipment orderEquipment) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAssistantsByOrder(Order order) throws DaoException {
        return null;
    }
}
