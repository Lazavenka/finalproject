package by.lozovenko.finalproject;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.impl.UserDaoImpl;
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
        String username = "root";
        String password = "P@$s_W0RD";
        Optional<User> admin = Optional.empty();
        List<User> users = new ArrayList<>();
        Optional<User> userById3 = Optional.empty();
        try {
            //admin = userService.signIn(username, password);
            users = UserDaoImpl.getInstance().findAll();
            //userById3 = UserDaoImpl.getInstance().findEntityById(3l);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        //System.out.println(admin.get());
        System.out.println(users);
        String hashedPass = PasswordEncryptor.encryptMd5Apache("4rR$4444");
        //System.out.println(userById3.get());
        System.out.println(hashedPass);
    }
}
