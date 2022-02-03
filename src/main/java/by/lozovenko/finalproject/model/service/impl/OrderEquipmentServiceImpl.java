package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.OrderDao;
import by.lozovenko.finalproject.model.dao.OrderEquipmentDao;
import by.lozovenko.finalproject.model.dao.impl.OrderDaoImpl;
import by.lozovenko.finalproject.model.dao.impl.OrderEquipmentDaoImpl;
import by.lozovenko.finalproject.model.entity.OrderEquipment;
import by.lozovenko.finalproject.model.entity.OrderState;
import by.lozovenko.finalproject.model.service.OrderEquipmentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderEquipmentServiceImpl implements OrderEquipmentService {
    private static OrderEquipmentService instance;

    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final OrderEquipmentDao orderEquipmentDao = OrderEquipmentDaoImpl.getInstance();
    private OrderEquipmentServiceImpl(){
    }
    public static OrderEquipmentService getInstance() {
        if(instance == null){
            instance = new OrderEquipmentServiceImpl();
        }
        return instance;
    }

    @Override
    public List<OrderEquipment> findOrderEquipmentByOrderId(long orderId) throws ServiceException {
        try {
            return orderEquipmentDao.findOrderEquipmentsByOrderId(orderId);
        }catch (DaoException e){
            throw new ServiceException("Can't handle findOrderEquipmentByOrderId request at OrderEquipmentService", e);
        }
    }

    @Override
    public List<OrderEquipment> findPayedOrderEquipmentByAssistantId(long assistantId) throws ServiceException {
        List<OrderEquipment> orderEquipmentList;
        try {
            OrderState payed = OrderState.PAYED;
            orderEquipmentList = orderDao.findOrderEquipmentByStateAndAssistantId(payed, assistantId);

        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return orderEquipmentList;
    }

    @Override
    public List<OrderEquipment> findOrderEquipmentByAssistantIdAtPeriod(long assistantId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException {
        return null;
    }

    @Override
    public List<OrderEquipment> findOrderEquipmentByEquipmentIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException {
        return null;
    }


}
