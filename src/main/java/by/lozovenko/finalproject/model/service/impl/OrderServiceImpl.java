package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.OrderDao;
import by.lozovenko.finalproject.model.dao.impl.OrderDaoImpl;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.service.OrderEquipmentService;
import by.lozovenko.finalproject.model.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderEquipmentService orderEquipmentService = OrderEquipmentServiceImpl.getInstance();
    private OrderDao orderDao = OrderDaoImpl.getInstance();
    @Override
    public List<Order> findOrdersByClientId(long clientId) throws ServiceException {
        try {
            List<Order> clientOrders = orderDao.findAllOrdersByClientId(clientId);
        }catch (DaoException e){

        }
    }
}
