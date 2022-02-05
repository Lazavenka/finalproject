package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.OrderDao;
import by.lozovenko.finalproject.model.dao.impl.OrderDaoImpl;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.entity.OrderState;
import by.lozovenko.finalproject.model.service.OrderService;

import java.time.LocalDate;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static OrderService instance;

    private final OrderDao orderDao = OrderDaoImpl.getInstance();

    private OrderServiceImpl(){
    }
    public static OrderService getInstance() {
        if(instance == null){
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Order> findOrdersByClientId(long clientId) throws ServiceException {
        try {
            return orderDao.findAllOrdersByClientId(clientId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Order> findOrdersByEquipmentId(long equipmentId) throws ServiceException {
        return null;
    }

    @Override
    public List<Order> findOrdersByEquipmentIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException {
        return null;
    }

    @Override
    public List<Order> findOrdersByAssistantIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException {
        return null;
    }

    @Override
    public List<Order> findPayedOrdersByAssistantIdFromNow(long assistantId) throws ServiceException {
        try {
            OrderState payed = OrderState.PAYED;
            return orderDao.findOrderByStateAndAssistantId(payed, assistantId);
        }catch (DaoException e){
            throw new ServiceException("Can't handle findPayedOrdersByAssistantIdFromNow method in OrderService. ", e);
        }
    }
}
