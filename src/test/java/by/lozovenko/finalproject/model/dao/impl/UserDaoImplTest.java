package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.dao.UserDao;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDaoImplTest {
    UserDao userDao = UserDaoImpl.getInstance();
    @Test
    public void testIsExistUser() throws DaoException {
        String existingUserLogin = "manager1";
        String existingUserHashedPassword = "a3fa9b3c87a0cc1ae23100b3399b854d";

        boolean actual = userDao.isExistUser(existingUserLogin, existingUserHashedPassword);

        assertTrue(actual);
    }
}