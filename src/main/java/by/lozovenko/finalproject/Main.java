package by.lozovenko.finalproject;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.impl.UserDaoImpl;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import by.lozovenko.finalproject.util.PasswordEncryptor;
import by.lozovenko.finalproject.validator.UserValidator;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserServiceImpl.getInstance();
        String username = "root";
        String password = "P@$s_W0RD";
        Optional<User> admin = Optional.empty();
        try {
            admin = userService.signIn(username, password);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        System.out.println(admin.get());

        String hashedPass = PasswordEncryptor.encryptMd5Apache("1qQ!1111");

        System.out.println(hashedPass);
    }
}
