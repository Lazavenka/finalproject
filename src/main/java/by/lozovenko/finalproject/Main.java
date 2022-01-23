package by.lozovenko.finalproject;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.impl.UserDaoImpl;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import by.lozovenko.finalproject.util.PasswordEncryptor;
import by.lozovenko.finalproject.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserServiceImpl.getInstance();
        Optional<Manager> optionalManager = Optional.empty();
        try {
            optionalManager = userService.findManagerById(4L);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        System.out.println(optionalManager.get());
    }
}
