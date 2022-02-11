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
import by.lozovenko.finalproject.validator.impl.AssistantMapDataValidator;
import by.lozovenko.finalproject.validator.impl.CustomFieldValidatorImpl;
import by.lozovenko.finalproject.validator.impl.ManagerMapDataValidator;
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

import static by.lozovenko.finalproject.controller.RequestAttribute.*;
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
                throw new ServiceException("Can't handle signIn method in UserService. ", e);
            }
        } else {
            LOGGER.log(Level.INFO, "User login or password is invalid.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserById(long id) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findEntityById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAllManagers method in UserService. ", e);
        }
        return optionalUser;
    }

    @Override
    public List<Manager> findAllManagers() throws ServiceException {
        try {
            return userDao.findAllManagers().stream().map(Manager.class::cast).toList();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAllManagers method in UserService. ", e);
        }
    }

    @Override
    public Optional<Manager> findManagerById(String managerId) throws ServiceException {
        Optional<Manager> optionalUser;
        if (inputFieldValidator.isCorrectId(managerId)) {
            Long id = Long.parseLong(managerId);
            optionalUser = findManagerById(id);
        } else {
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
            Client client = createClientFromMapData(userData);
            String tokenValue = UserTokenGenerator.generateToken();
            LocalDateTime registerDateTime = LocalDateTime.now();
            Token token = new Token(tokenValue, registerDateTime);
            long createdClientId = userDao.createClient(client, token);
            if (createdClientId > 0) {
                String mailBody = MailMessageBuilder.buildMessage(tokenValue);
                Mail.sendMail(userData.get(EMAIL), "You registered new account!", mailBody);
                return true;
            } else {
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
            if (token.isPresent()) {
                long userId = token.get().getId();
                result = userDao.confirmUserRegistration(userId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Can't handle confirmUserRegistration method in UserService. ", e);
        }
        return result;
    }

    @Override
    public Optional<BigDecimal> checkUserBalanceById(long userId) throws ServiceException {
        Optional<BigDecimal> optionalBalance;
        try {
            optionalBalance = userDao.checkUserBalanceByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle checkUserBalanceById method in UserService. ", e);
        }
        return optionalBalance;
    }

    @Override
    public boolean addBalance(Long userId, String balanceToAdd) throws ServiceException {
        if (!inputFieldValidator.isCorrectBalance(balanceToAdd)) {
            LOGGER.log(Level.DEBUG, "InvalidBalance - {}", balanceToAdd);
            return false;
        }
        boolean result;
        try {
            Optional<BigDecimal> optionalCurrentBalance = userDao.checkUserBalanceByUserId(userId);
            if (optionalCurrentBalance.isPresent()) {
                BigDecimal newBalance = new BigDecimal(balanceToAdd).add(optionalCurrentBalance.get());
                LOGGER.log(Level.DEBUG, "New Balance = {}", newBalance.floatValue());
                result = userDao.updateUserBalanceById(userId, newBalance);
            } else {
                result = false;
                LOGGER.log(Level.INFO, "UserService - user:{} balance not found", userId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Can't handle addBalance method in UserService. ", e);
        }
        return result;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
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

    @Override
    public boolean updateAvatar(User user, String path) throws ServiceException {
        UserRole role = user.getRole();
        long id = user.getId();
        boolean result;
        try {
            switch (role) {
                case MANAGER -> result = userDao.updateManagerAvatarPath(id, path) != 0;
                case ASSISTANT -> result = userDao.updateAssistantAvatarPath(id, path) != 0;
                default -> result = false;
            }
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateAvatar method in UserService. ", e);
        }
        return result;
    }

    @Override
    public List<Assistant> findAssistantsByLaboratoryId(long laboratoryId) throws ServiceException {
        try {
            return userDao.findAssistantByLaboratoryId(laboratoryId).stream().map(Assistant.class::cast).toList();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAssistantsByLaboratoryId method in UserService. ", e);
        }
    }

    @Override
    public boolean updatePassword(String login, Map<String, String> passwordData) throws ServiceException {
        String oldPassword = passwordData.get(OLD_PASSWORD);
        try {
            boolean checkResult = inputFieldValidator.isCorrectPassword(oldPassword);
            LOGGER.log(Level.DEBUG, "checkResult oldPassword correct = {}", checkResult);
            boolean existUser = userDao.isExistUser(login, PasswordEncryptor.encryptMd5Apache(oldPassword));
            LOGGER.log(Level.DEBUG, "existUser login and password correct = {}", checkResult);

            if (checkResult && existUser) {
                String newPassword = passwordData.get(NEW_PASSWORD);
                if (!inputFieldValidator.isCorrectPassword(newPassword)) {
                    passwordData.put(NEW_PASSWORD, INVALID_PASSWORD);
                    checkResult = false;
                    LOGGER.log(Level.DEBUG, "Incorrect new password");
                }
                String confirmedPassword = passwordData.get(CONFIRMED_PASSWORD);
                if (!inputFieldValidator.isMatchesPasswords(newPassword, confirmedPassword)) {
                    passwordData.put(CONFIRMED_PASSWORD, PASSWORDS_MISMATCH);
                    checkResult = false;
                    LOGGER.log(Level.DEBUG, "Mismatching passwords were entered");

                }
                if (checkResult) {
                    String newEncryptedPassword = PasswordEncryptor.encryptMd5Apache(newPassword);
                    checkResult = userDao.updatePasswordByLogin(newEncryptedPassword, login);
                    LOGGER.log(Level.DEBUG, "Update user password result = {}", checkResult);
                }
            } else {
                passwordData.clear();
                passwordData.put(OLD_PASSWORD, INCORRECT_OLD_PASSWORD);
                LOGGER.log(Level.DEBUG, "Incorrect old password");
            }
            return checkResult;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updatePassword method in UserService. ", e);
        }
    }

    @Override
    public boolean updateManagerDescriptionByUserId(long id, String description) throws ServiceException {
        try {
            if (!inputFieldValidator.isCorrectManagerDescription(description)) {
                return false;
            }
            return userDao.updateManagerDescriptionByUserId(id, description) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateManagerDescriptionByUserId method in UserService. ", e);

        }
    }

    @Override
    public boolean updateUserProfile(long id, Map<String, String> profileData) throws ServiceException {
        boolean checkResult = true;
        try {
            String firstName = profileData.get(FIRST_NAME);
            String lastName = profileData.get(LAST_NAME);
            String phone = profileData.get(PHONE);

            if (!inputFieldValidator.isCorrectName(lastName)) {
                profileData.put(LAST_NAME, INVALID_LAST_NAME);
                checkResult = false;
            }
            if (!inputFieldValidator.isCorrectName(firstName)) {
                profileData.put(FIRST_NAME, INVALID_FIRST_NAME);
                checkResult = false;
            }
            if (!inputFieldValidator.isCorrectPhone(phone)) {
                profileData.put(PHONE, INVALID_PHONE);
                checkResult = false;
            }

            if (checkResult) {
                checkResult = userDao.updateUserDataById(id, lastName, firstName, phone) != 0;
            }
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateUserProfile method in UserService. ", e);
        }
        return checkResult;
    }

    @Override
    public boolean addAssistant(Map<String, String> assistantData) throws ServiceException {
        try {
            dataValidator = AssistantMapDataValidator.getInstance();
            boolean isValidData = dataValidator.validateMapData(assistantData);
            if (!isValidData) {
                return false;
            }
            String login = assistantData.get(LOGIN);
            Optional<User> existUserLogin = userDao.findUserByLogin(login);
            if (existUserLogin.isPresent()) {
                assistantData.put(LOGIN, LOGIN_EXISTS);
                return false;
            }
            String email = assistantData.get(EMAIL);
            if (userDao.isExistUserWithEmail(email)) {
                assistantData.put(EMAIL, NOT_UNIQUE_EMAIL);
                return false;
            }
            Assistant assistant = createAssistantFromMapData(assistantData);
            return userDao.createAssistant(assistant) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle addAssistant method in UserService. ", e);
        }
    }

    @Override
    public boolean addAdmin(Map<String, String> adminData) throws ServiceException {
        try {
            dataValidator = AssistantMapDataValidator.getInstance();
            boolean isValidData = dataValidator.validateMapData(adminData);
            if (!isValidData) {
                return false;
            }
            String login = adminData.get(LOGIN);
            Optional<User> existUserLogin = userDao.findUserByLogin(login);
            if (existUserLogin.isPresent()) {
                adminData.put(LOGIN, LOGIN_EXISTS);
                return false;
            }
            String email = adminData.get(EMAIL);
            if (userDao.isExistUserWithEmail(email)) {
                adminData.put(EMAIL, NOT_UNIQUE_EMAIL);
                return false;
            }
            User admin = createUserFromMapData(adminData);
            admin.setRole(UserRole.ADMIN);
            admin.setState(UserState.ACTIVE);
            return userDao.create(admin) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle addAdmin method in UserService. ", e);
        }
    }

    @Override
    public boolean addManager(Map<String, String> managerData) throws ServiceException {
        try {
            dataValidator = ManagerMapDataValidator.getInstance();
            boolean isValidData = dataValidator.validateMapData(managerData);
            if (!isValidData) {
                return false;
            }
            String login = managerData.get(LOGIN);
            Optional<User> existUserLogin = userDao.findUserByLogin(login);
            if (existUserLogin.isPresent()) {
                managerData.put(LOGIN, LOGIN_EXISTS);
                return false;
            }
            String email = managerData.get(EMAIL);
            if (userDao.isExistUserWithEmail(email)) {
                managerData.put(EMAIL, NOT_UNIQUE_EMAIL);
                return false;
            }
            Manager manager = createManagerFromMapData(managerData);
            return userDao.createManager(manager) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle addAssistant method in UserService. ", e);
        }
    }

    @Override
    public long countManagersByDegree(ManagerDegree degree) throws ServiceException {
        long managersCount;
        try {
            managersCount = userDao.countManagersByDegree(degree);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle countManagersByDegree request at UserService", e);
        }
        return managersCount;
    }

    @Override
    public boolean deleteUserById(String userId) throws ServiceException {
        if (!inputFieldValidator.isCorrectId(userId)) {
            return false;
        }
        try {
            long id = Long.parseLong(userId);
            return userDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle deleteUserById request at UserService", e);
        }
    }

    @Override
    public boolean updateUserStateById(String userStateString, String userId) throws ServiceException {
        if (!inputFieldValidator.isCorrectId(userId) || !inputFieldValidator.isCorrectUserState(userStateString)) {
            return false;
        }
        try {
            UserState userState = UserState.valueOf(userStateString);
            long id = Long.parseLong(userId);
            return userDao.updateUserStateById(userState, id);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateUserStateById request at UserService", e);
        }
    }

    @Override
    public Optional<Assistant> findAssistantById(long assistantId) throws ServiceException {
        try {
            Optional<User> optionalAssistant = userDao.findAssistantById(assistantId);
            return optionalAssistant.isPresent() ? optionalAssistant.map(Assistant.class::cast) : Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAssistantById request at UserService", e);
        }
    }

    private User createUserFromMapData(Map<String, String> userData) {
        User user = new User();
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

    private Client createClientFromMapData(Map<String, String> userData) {
        User user = createUserFromMapData(userData);
        Client client = new Client(user);
        user.setRole(UserRole.CLIENT);
        user.setState(UserState.REGISTRATION);
        return client;
    }

    private Assistant createAssistantFromMapData(Map<String, String> userData) {
        User user = createUserFromMapData(userData);
        Assistant assistant = new Assistant(user);
        assistant.setState(UserState.ACTIVE);
        assistant.setRole(UserRole.ASSISTANT);
        long laboratoryId = Long.parseLong(userData.get(LABORATORY_ID));
        assistant.setLaboratoryId(laboratoryId);
        return assistant;
    }

    private Manager createManagerFromMapData(Map<String, String> userData) {
        User user = createUserFromMapData(userData);
        Manager manager = new Manager(user);
        manager.setState(UserState.ACTIVE);
        manager.setRole(UserRole.MANAGER);
        ManagerDegree degree = ManagerDegree.getDegreeByString(userData.get(MANAGER_DEGREE));
        manager.setManagerDegree(degree);
        manager.setDescription(userData.get(DESCRIPTION));
        long departmentId = Long.parseLong(userData.get(DEPARTMENT_ID));
        manager.setDepartmentId(departmentId);
        long laboratoryId = Long.parseLong(userData.get(LABORATORY_ID));
        manager.setLaboratoryId(laboratoryId);

        return manager;
    }
}
