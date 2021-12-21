package by.lozovenko.finalproject.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.entity.*;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    boolean updatePasswordByLogin(String password, String login) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;

    List<User> findAllManagers() throws DaoException;

    List<User> findManagersByDepartment(Department department) throws DaoException;

    List<User> findManagersByDegree(ManagerDegree managerDegree) throws DaoException;

    Optional<User> findManagerByName(String patternName) throws DaoException;

    List<User> findAllAssistants() throws DaoException;

    List<User> findAssistantsByEquipmentType(EquipmentType equipmentType) throws DaoException;

    Optional<User> findAssistantByOrderEquipment(OrderEquipment orderEquipment) throws DaoException;

    List<User> findAssistantsByOrder(Order order) throws DaoException;


}
