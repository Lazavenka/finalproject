package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    boolean updatePasswordByLogin(String password, String login) throws DaoException;

    long createManager(Manager manager) throws DaoException;

    long createClient(Client client, Token token) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    boolean updateUserStateById(UserState userState, Long userId) throws DaoException;

    Optional<Token> findUserTokenByValue(String tokenValue) throws DaoException;

    boolean confirmUserRegistration(Long userId) throws DaoException;

    List<User> findAllManagers() throws DaoException;

    Optional<User> findManagerById(Long managerId) throws DaoException;

    Optional<User> findAssistantById(Long assistantId) throws DaoException;

    boolean isExistUserWithEmail(String email) throws DaoException;

    Optional<BigDecimal> checkUserBalanceByUserId(Long userId) throws DaoException;

    boolean updateUserBalanceById(Long userId, BigDecimal newBalance) throws DaoException;

    Optional<User> findManagerByLaboratoryId(long laboratoryId) throws DaoException;

    int updateAssistantAvatarPath(long id, String path) throws DaoException;

    int updateManagerAvatarPath(long id, String path) throws DaoException;

    List<User> findAssistantByLaboratoryId(long laboratoryId) throws DaoException;

    boolean isExistUser(String login, String encryptedPassword) throws DaoException;

    int updateManagerDescriptionByUserId(long id, String description) throws DaoException;

    int updateUserDataById(long id, String lastName, String firstName, String phone) throws DaoException;

    long createAssistant(Assistant assistant) throws DaoException;

    long countManagersByDegree(ManagerDegree degree) throws DaoException;
}
