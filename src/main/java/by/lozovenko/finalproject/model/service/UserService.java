package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> signIn(String login, String password) throws ServiceException;

    List<Manager> findAllManagers() throws ServiceException;

    Optional<Manager> findManagerById(Long managerId) throws ServiceException;

    boolean registerUser(Map<String, String> userData) throws ServiceException;

    boolean confirmUserRegistration(String token) throws ServiceException;

    Optional<BigDecimal> checkUserBalanceById(long userId)throws ServiceException;

    boolean addBalance(Long userId, String balanceString) throws ServiceException;
}
