package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.UserValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserValidatorImplTest {
    private final UserValidator userValidator = UserValidatorImpl.getInstance();

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
        final String password = "P@$s_W0RD";

        boolean actual = userValidator.isCorrectPassword(password);

        assertTrue(actual);

    }
    @Test
    public void testIsCorrectPasswordSimplePass() {
        final String password = "aaaaaaaaaa";

        boolean actual = userValidator.isCorrectPassword(password);

        assertFalse(actual);

    }

    @Test
    public void testIsCorrectName() {
        final String name = "Andrey";

        boolean actual = userValidator.isCorrectName(name);

        assertTrue(actual);
    }

    @Test
    public void testIsMatchesPasswords() {
        final String pass = "P@$s_W0RD";
        final String confirmedPass = "P@$s_W0RD";

        boolean actual = userValidator.isMatchesPasswords(pass, confirmedPass);

        assertTrue(actual);
    }

    @Test
    public void testIsMatchesPasswordsNotEquals() {
        final String pass = "P@$s_W0RD";
        final String confirmedPass = "P@$s_W1RD";

        boolean actual = userValidator.isMatchesPasswords(pass, confirmedPass);

        assertFalse(actual);
    }
    @Test
    public void testIsCorrectEmail() {
        final String email = "mail@test.com";

        boolean actual = userValidator.isCorrectEmail(email);

        assertTrue(actual);
    }
    @Test
    public void testIsCorrectEmailOver55Characters() {
        final String email = "maijhfiuh984kiehtwoihtoithweoithweoithewwetwtel@test.com"; //56 char

        boolean actual = userValidator.isCorrectEmail(email);

        assertFalse(actual);
    }

    @Test
    public void testIsCorrectPhone() {
        final String phone = "291005089";

        boolean actual = userValidator.isCorrectPhone(phone);

        assertTrue(actual);
    }

    @Test
    public void testIsCorrectBalance() {
        String balanceStringToAdd = "991.00";

        boolean actual = userValidator.isCorrectBalance(balanceStringToAdd);

        assertTrue(actual);
    }
}