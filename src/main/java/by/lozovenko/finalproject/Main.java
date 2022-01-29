package by.lozovenko.finalproject;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.impl.UserDaoImpl;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import by.lozovenko.finalproject.util.PasswordEncryptor;
import by.lozovenko.finalproject.util.UserTokenGenerator;
import by.lozovenko.finalproject.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String token = "/finalproject_war_exploded/jsp/common/login.jsp";
        int idx = token.indexOf("ded");
        String newStr = token.substring(idx+3);
        System.out.println(token);
        System.out.println(newStr);
    }
}
