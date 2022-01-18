package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.UserDao;
import by.lozovenko.finalproject.model.dao.impl.UserDaoImpl;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.util.PasswordEncryptor;
import by.lozovenko.finalproject.validator.UserValidator;
import by.lozovenko.finalproject.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static UserServiceImpl instance;
    private final UserDao userDao = UserDaoImpl.getInstance();
    UserValidator userValidator = UserValidatorImpl.getInstance();
    private UserServiceImpl(){
    }

    public static UserServiceImpl getInstance() {
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }
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
}
