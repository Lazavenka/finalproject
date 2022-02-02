package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.UserDao;
import by.lozovenko.finalproject.model.dao.impl.UserDaoImpl;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.util.PasswordEncryptor;
import by.lozovenko.finalproject.util.UserTokenGenerator;
import by.lozovenko.finalproject.util.mail.Mail;
import by.lozovenko.finalproject.util.mail.MailMessageBuilder;
import by.lozovenko.finalproject.validator.CustomFieldValidator;
import by.lozovenko.finalproject.validator.CustomMapDataValidator;
import by.lozovenko.finalproject.validator.impl.CustomFieldValidatorImpl;
import by.lozovenko.finalproject.validator.impl.UserMapDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestAttribute.LOGIN_EXISTS;
import static by.lozovenko.finalproject.controller.RequestAttribute.NOT_UNIQUE_EMAIL;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static UserService instance;

    private final UserDao userDao = UserDaoImpl.getInstance();

    private CustomMapDataValidator dataValidator;

    private final CustomFieldValidator inputFieldValidator = CustomFieldValidatorImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        Optional<User> optionalUser;
        if (inputFieldValidator.isCorrectLogin(login) && inputFieldValidator.isCorrectPassword(password)) {
            try {
                optionalUser = userDao.findUserByLogin(login);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    String userPassword = user.getPassword();
                    String hashedPassword = PasswordEncryptor.encryptMd5Apache(password);
                    if (!userPassword.equals(hashedPassword)) {
                        LOGGER.log(Level.INFO, "User login or password is incorrect");
                        return Optional.empty();
                    }
                }
                return optionalUser;
            } catch (DaoException e) {
                throw new ServiceException("Can't handle signIn method in UserService. " + e);
            }
        } else {
            LOGGER.log(Level.INFO, "User login or password is invalid.");
            return Optional.empty();
        }
    }

    @Override
    public List<Manager> findAllManagers() throws ServiceException {
        try {
            return userDao.findAllManagers().stream().map(Manager.class::cast).toList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Manager> findManagerById(String managerId) throws ServiceException {
        Optional<Manager> optionalUser;
        if (inputFieldValidator.isCorrectId(managerId)){
            Long id = Long.parseLong(managerId);
            optionalUser = findManagerById(id);
        }else {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }

    @Override
    public Optional<Manager> findManagerById(Long managerId) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findManagerById(managerId);
            return optionalUser.isPresent() ? optionalUser.map(Manager.class::cast) : Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findManagerById method in UserService. ", e);
        }
    }

    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException {
        try {
            dataValidator = UserMapDataValidator.getInstance();
            boolean isValidData = dataValidator.validateMapData(userData);
            if (!isValidData) {
                return false;
            }
            String login = userData.get(LOGIN);
            Optional<User> existUserLogin = userDao.findUserByLogin(login);
            if (existUserLogin.isPresent()) {
                LOGGER.log(Level.INFO, "User with login {} found. User - {}", login, existUserLogin.get());
                userData.put(LOGIN, LOGIN_EXISTS);
                return false;
            }
            String email = userData.get(EMAIL);
            if (userDao.isExistUserWithEmail(email)) {
                LOGGER.log(Level.INFO, "Not unique email {}.", email);
                userData.put(EMAIL, NOT_UNIQUE_EMAIL);
                return false;
            }
            Client client = createClientFromUserData(userData);
            String tokenValue = UserTokenGenerator.generateToken();
            LocalDateTime registerDateTime = LocalDateTime.now();
            Token token = new Token(tokenValue, registerDateTime);
            long createdClientId = userDao.createClient(client, token);
            if (createdClientId > 0) {
                String mailBody = MailMessageBuilder.buildMessage(tokenValue);
                Mail.sendMail(userData.get(EMAIL), "You registered new account!", mailBody);
                return true;
            }else {
                return false;
            }
        } catch (DaoException | IOException e) {
            throw new ServiceException("Can't handle registerUser method in UserService. ", e);
        }
    }

    @Override
    public boolean confirmUserRegistration(String tokenValue) throws ServiceException {
        boolean result = false;
        try {
            Optional<Token> token = userDao.findUserTokenByValue(tokenValue);
            if (token.isPresent()){
                long userId = token.get().getId();
                result = userDao.confirmUserRegistration(userId);
            }
        }catch (DaoException e){
            throw new ServiceException("Can't handle confirmUserRegistration method in UserService. ", e);
        }
        return result;
    }

    @Override
    public Optional<BigDecimal> checkUserBalanceById(long userId) throws ServiceException {
        Optional<BigDecimal> optionalBalance;
        try {
            optionalBalance = userDao.checkUserBalanceByUserId(userId);
        }catch (DaoException e){
            throw new ServiceException("Can't handle checkUserBalanceById method in UserService. ", e);
        }
        return optionalBalance;
    }

    @Override
    public boolean addBalance(Long userId, String balanceToAdd) throws ServiceException {
        if (!inputFieldValidator.isCorrectBalance(balanceToAdd)){
            LOGGER.log(Level.DEBUG, "InvalidBalance - {}", balanceToAdd);
            return false;
        }
        boolean result;
        try {
            Optional<BigDecimal> optionalCurrentBalance = userDao.checkUserBalanceByUserId(userId);
            if (optionalCurrentBalance.isPresent()){
                BigDecimal newBalance = new BigDecimal(balanceToAdd).add(optionalCurrentBalance.get());
                LOGGER.log(Level.DEBUG, "New Balance = {}", newBalance.floatValue());
                result = userDao.updateUserBalanceById(userId, newBalance);
            }else {
                result = false;
                LOGGER.log(Level.INFO, "UserService - user:{} balance not found", userId);
            }
        }catch (DaoException e){
            throw new ServiceException("Can't handle addBalance method in UserService. ", e);
        }
        return result;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            return userDao.findAll();
        }catch (DaoException e){
            throw new ServiceException("Can't handle findAllUsers method in UserService. ", e);
        }
    }

    @Override
    public Optional<Manager> findManagerByLaboratoryId(long laboratoryId) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findManagerByLaboratoryId(laboratoryId);
            return optionalUser.isPresent() ? optionalUser.map(Manager.class::cast) : Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findManagerByLaboratoryId method in UserService. ", e);
        }
    }

    private Client createClientFromUserData(Map<String, String> userData){
        Client user = new Client();
        String hashedPassword = PasswordEncryptor.encryptMd5Apache(userData.get(PASSWORD));
        user.setLogin(userData.get(LOGIN));
        user.setPassword(hashedPassword);
        user.setFirstName(userData.get(FIRST_NAME));
        user.setLastName(userData.get(LAST_NAME));
        user.setEmail(userData.get(EMAIL));
        user.setPhone(userData.get(PHONE));
        user.setRole(UserRole.CLIENT);
        user.setState(UserState.REGISTRATION);
        return user;
    }
}
