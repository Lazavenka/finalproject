package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.*;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    boolean updatePasswordByLogin(String password, String login) throws DaoException;
    long createManager(Long userId, Manager manager) throws DaoException;
    long createClient(Long userId) throws DaoException;
    UserRole findUserRoleByLogin(String login) throws  DaoException;
    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;

    List<User> findAllManagers() throws DaoException;

    List<User> findManagersByDepartmentId(Long departmentId) throws DaoException;

    List<User> findManagersByDegree(ManagerDegree managerDegree) throws DaoException;

    Optional<User> findManagerByName(String patternName) throws DaoException;

    Optional<User> findManagerById(Long managerId) throws DaoException;

    List<User> findAllAssistants() throws DaoException;

    Optional<User> findAssistantById(Long assistantId) throws DaoException;

    List<User> findAssistantsByEquipmentType(EquipmentType equipmentType) throws DaoException;

    Optional<User> findAssistantByOrderEquipment(OrderEquipment orderEquipment) throws DaoException;

    List<User> findAssistantsByOrder(Order order) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;
}
