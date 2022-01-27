package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.UserDao;
import by.lozovenko.finalproject.model.dao.impl.UserDaoImpl;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import by.lozovenko.finalproject.model.entity.UserState;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.util.PasswordEncryptor;
import by.lozovenko.finalproject.util.mail.Mail;
import by.lozovenko.finalproject.validator.UserValidator;
import by.lozovenko.finalproject.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestAttribute.LOGIN_EXISTS;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static UserService instance;

    private final UserDao userDao = UserDaoImpl.getInstance();
    UserValidator userValidator = UserValidatorImpl.getInstance();
    private UserServiceImpl(){
    }

    public static UserService getInstance() {
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException{
        Optional<User> optionalUser;
        if (userValidator.isCorrectLogin(login) && userValidator.isCorrectPassword(password)){
            try {
                optionalUser = userDao.findUserByLogin(login);
                if (optionalUser.isPresent()){
                    String hashedPassword = PasswordEncryptor.encryptMd5Apache(password);
                    if (!optionalUser.get().getPassword().equals(hashedPassword)){
                        LOGGER.log(Level.INFO,"User login or password is incorrect");
                        return Optional.empty();
                    }
                }
                return optionalUser;
            }catch (DaoException e){
                throw new ServiceException("Can't handle signIn method in UserService. "+ e);
            }
        }else {
            LOGGER.log(Level.INFO, "User login or password is invalid.");
            return Optional.empty();
        }
    }

    @Override
    public List<Manager> findAllManagers() throws ServiceException {
        try {
            return userDao.findAllManagers().stream().map(Manager.class::cast).toList();
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Manager> findManagerById(Long managerId) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findManagerById(managerId);
            return optionalUser.isPresent() ? optionalUser.map(Manager.class::cast) : Optional.empty();
        }catch (DaoException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException{
        try {
            boolean isValidData = userValidator.checkUserData(userData);
            if (!isValidData){
                return false;
            }
            String login = userData.get(LOGIN);
            Optional<User> existUser = userDao.findUserByLogin(login);
            if (existUser.isPresent()){
                userData.put(LOGIN, LOGIN_EXISTS);
                return false;
            }
            String email = userData.get(EMAIL);
            existUser = userDao.findUserByEmail(email);
            if (existUser.isPresent()){
                userData.put(LOGIN, LOGIN_EXISTS);
                return false;
            }
            long createdUserId = createUser(userData);
            if (createdUserId > 0 && createClient(createdUserId)){
                    Mail.sendMail(userData.get(EMAIL), "CONGRATS! You registered new account!", "Your password "+userData.get(EMAIL)); //fixme add mailText
            }
            return true;
        } catch (DaoException | IOException e) {
            throw new ServiceException(e);
        }
    }

    private boolean createClient(long createdUserId) throws DaoException{
        return userDao.createClient(createdUserId) > 0;
    }

    private long createUser(Map<String, String> userData) throws DaoException{
        User user = new User();
        String password = userData.get(PASSWORD);
        String email = userData.get(EMAIL);
        String hashedPassword = PasswordEncryptor.encryptMd5Apache(password);
        user.setLogin(userData.get(LOGIN));
        user.setPassword(hashedPassword);
        user.setFirstName(userData.get(FIRST_NAME));
        user.setLastName(userData.get(LAST_NAME));
        user.setEmail(email);
        user.setPhone(userData.get(PHONE));
        user.setRole(UserRole.CLIENT);
        user.setState(UserState.REGISTRATION);
        return userDao.create(user);
    }
}
