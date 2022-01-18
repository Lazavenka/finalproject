package by.lozovenko.finalproject;

import by.lozovenko.finalproject.model.pool.CustomConnectionPool;

public class Main {
    public static void main(String[] args) {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    }
}
