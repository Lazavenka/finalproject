package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.model.service.UserService;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;

    private UserServiceImpl(){
    }

    public static UserServiceImpl getInstance() {
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }
}
