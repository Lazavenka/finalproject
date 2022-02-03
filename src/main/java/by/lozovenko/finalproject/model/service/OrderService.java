package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findOrdersByClientId(long userId) throws ServiceException;
}
