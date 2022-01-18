package by.lozovenko.finalproject.validator.impl;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserValidatorImplTest {
    private final UserValidatorImpl userValidator = UserValidatorImpl.getInstance();

    @Test
    public void testIsCorrectLogin() {
        final String login = "root";

        boolean actual = userValidator.isCorrectLogin(login);

        assertTrue(actual);
    }

    @Test
    public void testIsCorrectLoginThreeSymbols() {
        final String login = "roo";

        boolean actual = userValidator.isCorrectLogin(login);

        assertFalse(actual);
    }
    @Test
    public void testIsCorrectPassword() {
        final String password = "P@$S_W0RD";

        boolean actual = userValidator.isCorrectPassword(password);

        assertTrue(actual);

    }
}