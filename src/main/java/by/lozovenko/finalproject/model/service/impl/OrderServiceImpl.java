package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.OrderDao;
import by.lozovenko.finalproject.model.dao.UserDao;
import by.lozovenko.finalproject.model.dao.impl.OrderDaoImpl;
import by.lozovenko.finalproject.model.dao.impl.UserDaoImpl;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.entity.OrderState;
import by.lozovenko.finalproject.model.service.OrderPaymentCode;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.util.TotalOrderCostCalculator;
import by.lozovenko.finalproject.validator.CustomFieldValidator;
import by.lozovenko.finalproject.validator.impl.CustomFieldValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static OrderService instance;

    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final CustomFieldValidator inputFieldValidator = CustomFieldValidatorImpl.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Order> findOrdersByClientId(long clientId, int offset, int recordsPerPge) throws ServiceException {
        try {
            return orderDao.findOrdersByClientId(clientId, offset, recordsPerPge);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findOrdersByClientId method in OrderService. ", e);
        }

    }

    @Override
    public List<Order> findOrdersByEquipmentIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException {
        try {
            if (startPeriod.isAfter(endPeriod)) {
                return Collections.emptyList();
            }
            return orderDao.findOrdersByEquipmentIdAtPeriod(equipmentId, startPeriod, endPeriod);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findOrdersByEquipmentIdAtPeriod method in OrderService. ", e);
        }
    }

    @Override
    public List<Order> findOrdersByAssistantIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws ServiceException {
        try {
            if (startPeriod.isAfter(endPeriod)) {
                return Collections.emptyList();
            }
            return orderDao.findOrdersByAssistantIdAtPeriod(equipmentId, startPeriod, endPeriod);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findOrdersByAssistantIdAtPeriod method in OrderService. ", e);
        }
    }

    @Override
    public List<Order> findPayedOrdersByAssistantIdFromNow(long assistantId) throws ServiceException {
        try {
            OrderState payed = OrderState.PAYED;
            LocalDateTime now = LocalDateTime.now();
            return orderDao.findOrdersByStateAndAssistantIdSince(payed, assistantId, now);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findPayedOrdersByAssistantIdFromNow method in OrderService. ", e);
        }
    }

    @Override
    public boolean createOrders(long clientId, String isNeedAssistant, String[] orderDateAssistant, Equipment selectedEquipment) throws ServiceException {
        if (orderDateAssistant == null) {
            return false;
        }
        List<Order> orderList = new ArrayList<>();
        LocalTime researchTime = selectedEquipment.getAverageResearchTime();
        int researchTimeHours = researchTime.getHour();
        int researchTimeMinutes = researchTime.getMinute();
        BigDecimal pricePerHour = selectedEquipment.getPricePerHour();
        BigDecimal orderCost = TotalOrderCostCalculator.calculateTotalCost(pricePerHour, researchTime);
        boolean needAssistant = Boolean.parseBoolean(isNeedAssistant);
        long equipmentId = selectedEquipment.getId();
        for (String currentOrderBookParameters : orderDateAssistant) {
            Order order = new Order();
            order.setEquipmentId(equipmentId);
            String[] currentOrderDateAssistant = currentOrderBookParameters.split("\\|");
            String orderStartDateTimeString = currentOrderDateAssistant[0];
            String currentAssistantId = currentOrderDateAssistant[1];
            LocalDateTime orderStartDateTime = LocalDateTime.parse(orderStartDateTimeString);
            LocalDateTime orderEndDateTime = orderStartDateTime.plus(researchTimeHours, ChronoUnit.HOURS).plus(researchTimeMinutes, ChronoUnit.MINUTES);
            order.setRentStartTime(orderStartDateTime);
            order.setRentEndTime(orderEndDateTime);
            order.setClientId(clientId);
            long assistantId = needAssistant ? Long.parseLong(currentAssistantId) : 0;
            order.setAssistantId(assistantId);
            order.setTotalCost(orderCost);
            order.setState(OrderState.BOOKED);
            orderList.add(order);
        }
        try {
            return orderDao.createOrders(orderList) != null;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle createOrders method in OrderService. ", e);
        }

    }

    @Override
    public OrderPaymentCode payOrder(long userId, String orderIdString) throws ServiceException {
        if (!inputFieldValidator.isCorrectId(orderIdString)) {
            LOGGER.log(Level.DEBUG, "PayOrder - invalid data (userId={}, orderStringId={}", userId, orderIdString);
            return OrderPaymentCode.INVALID_DATA;
        }
        UserDao userDao = UserDaoImpl.getInstance();
        OrderPaymentCode result;
        try {
            Optional<BigDecimal> optionalClientBalance = userDao.checkUserBalanceByUserId(userId);
            long orderId = Long.parseLong(orderIdString);
            Optional<Order> optionalOrder = orderDao.findEntityById(orderId);
            if (optionalClientBalance.isPresent() && optionalOrder.isPresent()) {
                BigDecimal clientBalance = optionalClientBalance.get();
                BigDecimal orderTotalCost = optionalOrder.get().getTotalCost();
                if (clientBalance.compareTo(orderTotalCost) > 0) {
                    BigDecimal newUserBalance = clientBalance.subtract(orderTotalCost);
                    OrderState newOrderState = OrderState.PAYED;
                    result = orderDao.payOrder(userId, newUserBalance, orderId, newOrderState) ? OrderPaymentCode.PAYED : OrderPaymentCode.ERROR;
                } else {
                    LOGGER.log(Level.DEBUG, "PayOrder - not enough money (userBalance = {}, totalCost={}", clientBalance, orderTotalCost);
                    result = OrderPaymentCode.NOT_ENOUGH_MONEY;
                }
            } else {
                LOGGER.log(Level.DEBUG, "PayOrder - order orderId={} not found", orderIdString);

                result = OrderPaymentCode.ORDER_NOT_EXIST;
            }
        } catch (DaoException e) {
            throw new ServiceException("Can't handle payOrder method in OrderService. ", e);
        }
        return result;
    }

    @Override
    public boolean updateOrderStateById(String orderIdString, OrderState orderState) throws ServiceException {
        if (!inputFieldValidator.isCorrectId(orderIdString)) {
            return false;
        }
        long orderId = Long.parseLong(orderIdString);
        try {
            return orderDao.updateOrderState(orderId, orderState) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateOrderStateById method in OrderService. ", e);
        }
    }

    @Override
    public List<Order> findOrdersByLaboratoryId(long laboratoryId, int offset, int recordsPerPage) throws ServiceException {
        try {
            return orderDao.findOrdersByLaboratoryId(laboratoryId, offset, recordsPerPage);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findOrdersByLaboratoryId method in OrderService. ", e);
        }
    }

    @Override
    public Optional<Order> findOrderById(String orderIdString) throws ServiceException {
        Optional<Order> optionalOrder;
        if (inputFieldValidator.isCorrectId(orderIdString)) {
            long id = Long.parseLong(orderIdString);
            optionalOrder = findOrderById(id);
        } else {
            optionalOrder = Optional.empty();
        }
        return optionalOrder;
    }

    @Override
    public Optional<Order> findOrderById(long orderId) throws ServiceException {
        try {
            return orderDao.findEntityById(orderId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findOrderById method in OrderService. ", e);
        }
    }

    @Override
    public int countClientOrders(long clientId) throws ServiceException {
        try {
            return orderDao.countClientOrders(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle countClientOrders method in OrderService. ", e);

        }
    }

    @Override
    public int countLaboratoryOrders(long laboratoryId) throws ServiceException {
        try {
            return orderDao.countLaboratoryOrders(laboratoryId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle countLaboratoryOrders method in OrderService. ", e);

        }
    }
}
