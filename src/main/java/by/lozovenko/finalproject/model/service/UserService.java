package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Assistant;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.ManagerDegree;
import by.lozovenko.finalproject.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> signIn(String login, String password) throws ServiceException;

    Optional<User> findUserById(long id) throws ServiceException;

    List<Manager> findAllManagers() throws ServiceException;

    Optional<Manager> findManagerById(String managerId) throws ServiceException;

    Optional<Manager> findManagerById(Long managerId) throws ServiceException;

    boolean registerUser(Map<String, String> userData) throws ServiceException;

    boolean confirmUserRegistration(String token) throws ServiceException;

    Optional<BigDecimal> checkUserBalanceById(long userId) throws ServiceException;

    boolean addBalance(Long userId, String balanceString) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;

    Optional<Manager> findManagerByLaboratoryId(long laboratoryId) throws ServiceException;

    boolean updateAvatar(User user, String path) throws ServiceException;

    List<Assistant> findAssistantsByLaboratoryId(long laboratoryId) throws ServiceException;

    boolean updatePassword(String login, Map<String, String> passwordData) throws ServiceException;

    boolean updateManagerDescriptionByUserId(long id, String description) throws ServiceException;

    boolean updateUserProfile(long id, Map<String, String> profileData) throws ServiceException;

    boolean addAssistant(Map<String, String> assistantData) throws ServiceException;

    boolean addAdmin(Map<String, String> adminData) throws ServiceException;

    boolean addManager(Map<String, String> managerData) throws ServiceException;


    long countManagersByDegree(ManagerDegree degree) throws ServiceException;

    boolean deleteUserById(String userId) throws ServiceException;

    boolean updateUserStateById(String userStateString, String userId) throws ServiceException;
}
