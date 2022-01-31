package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.OrderEquipment;

import java.util.List;

public interface OrderEquipmentService {
    List<OrderEquipment> findPayedOrderEquipmentByAssistantId(long assistantId) throws ServiceException;

}
