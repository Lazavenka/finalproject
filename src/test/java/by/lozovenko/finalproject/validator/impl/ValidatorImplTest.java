package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.validator.Validator;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ValidatorImplTest {
    private final Validator validator = ValidatorImpl.getInstance();

    @Test
    public void testIsCorrectLogin() {
        final String login = "root";

        boolean actual = validator.isCorrectLogin(login);

        assertTrue(actual);
    }

    @Test
    public void testIsCorrectLoginThreeSymbols() {
        final String login = "roo";

        boolean actual = validator.isCorrectLogin(login);

        assertFalse(actual);
    }
    @Test
    public void testIsCorrectPassword() {
        final String password = "P@$s_W0RD";

        boolean actual = validator.isCorrectPassword(password);

        assertTrue(actual);

    }
    @Test
    public void testIsCorrectPasswordSimplePass() {
        final String password = "aaaaaaaaaa";

        boolean actual = validator.isCorrectPassword(password);

        assertFalse(actual);

    }

    @Test
    public void testIsCorrectName() {
        final String name = "Andrey";

        boolean actual = validator.isCorrectName(name);

        assertTrue(actual);
    }

    @Test
    public void testIsMatchesPasswords() {
        final String pass = "P@$s_W0RD";
        final String confirmedPass = "P@$s_W0RD";

        boolean actual = validator.isMatchesPasswords(pass, confirmedPass);

        assertTrue(actual);
    }

    @Test
    public void testIsMatchesPasswordsNotEquals() {
        final String pass = "P@$s_W0RD";
        final String confirmedPass = "P@$s_W1RD";

        boolean actual = validator.isMatchesPasswords(pass, confirmedPass);

        assertFalse(actual);
    }
    @Test
    public void testIsCorrectEmail() {
        final String email = "mail@test.com";

        boolean actual = validator.isCorrectEmail(email);

        assertTrue(actual);
    }
    @Test
    public void testIsCorrectEmailOver55Characters() {
        final String email = "maijhfiuh984kiehtwoihtoithweoithweoithewwetwtel@test.com"; //56 char

        boolean actual = validator.isCorrectEmail(email);

        assertFalse(actual);
    }

    @Test
    public void testIsCorrectPhone() {
        final String phone = "291005089";

        boolean actual = validator.isCorrectPhone(phone);

        assertTrue(actual);
    }

    @Test
    public void testIsCorrectBalance() {
        String balanceStringToAdd = "991.00";

        boolean actual = validator.isCorrectBalance(balanceStringToAdd);

        assertTrue(actual);
    }
}