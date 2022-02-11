package by.lozovenko.finalproject.model.dao.impl;

import by.lozovenko.finalproject.model.dao.OrderDao;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.mapper.impl.OrderMapper;
import by.lozovenko.finalproject.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static OrderDao instance;

    private static final String GET_ORDER_BY_ID = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end, order_assistant_id
            FROM orders WHERE order_id = ?""";
    private static final String GET_ORDERS_BY_STATE_AND_ASSISTANT_ID = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end, order_assistant_id
            FROM orders WHERE order_state = ? AND order_assistant_id = ?""";
    private static final String GET_ORDERS_BY_CLIENT_ID = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end, order_assistant_id
            FROM orders WHERE client_id = ? ORDER BY order_rent_start""";
    private static final String GET_ORDERS_BY_LABORATORY_ID = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end,
            order_assistant_id FROM orders
            JOIN equipment ON orders.order_equipment_id = equipment.equipment_id WHERE equipment.laboratory_id = ? ORDER BY order_rent_start""";

    private static final String GET_ORDERS_BY_EQUIPMENT_ID_AT_PERIOD = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end,
            order_assistant_id FROM orders WHERE order_equipment_id = ? AND order_rent_start BETWEEN ? AND ?""";
    private static final String GET_ORDERS_BY_ASSISTANT_ID_AT_PERIOD = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end,
            order_assistant_id FROM orders WHERE order_assistant_id = ? AND order_rent_start BETWEEN ? AND ?""";
    private static final String GET_ORDERS_BY_STATE_ASSISTANT_ID_SINCE = """
            SELECT order_id, client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end,
            order_assistant_id FROM orders WHERE order_state = ? AND order_assistant_id = ? AND order_rent_start > ?""";

    private static final String CREATE_ORDER = """
            INSERT INTO orders (client_id, order_state, order_total_cost, order_equipment_id, order_rent_start, order_rent_end, order_assistant_id) 
            VALUES (?,?,?,?,?,?,?)""";

    private static final String UPDATE_USER_BALANCE_BY_ID = "UPDATE clients SET balance = ? WHERE user_id = ?";
    private static final String UPDATE_ORDER_STATE_BY_ID = "UPDATE orders SET order_state = ? WHERE order_id = ?";


    private OrderDaoImpl() {
    }

    public static OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findEntityById(Long id) throws DaoException {
        Optional<Order> optionalOrder = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = new Order();
                    optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                    String loggerResult = optionalOrder.isPresent() ? String.format("Order with id = %d was found.", id)
                            : String.format("Order with id %d doesn't exist", id);
                    LOGGER.log(Level.INFO, "findEntityById completed successfully. {}", loggerResult);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findEntityById method OrderDao class. Unable to get access to database.", e);
        }
        return optionalOrder;
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public long create(Order order) throws DaoException {
        return -1;
    }

    @Override
    public long update(Order order) throws DaoException {
        throw new UnsupportedOperationException("update(Order order) method is not supported");
    }

    @Override
    public List<Order> findAllOrdersByClientId(long clientId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_CLIENT_ID)) {
            preparedStatement.setLong(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findAllOrdersByClientId method OrderDao class. Unable to get access to database.", e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersByLaboratoryId(long laboratoryId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_LABORATORY_ID)) {
            preparedStatement.setLong(1, laboratoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findOrdersByLaboratoryId method OrderDao class. Unable to get access to database.", e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrderByStateAndAssistantId(OrderState orderState, Long assistantId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_STATE_AND_ASSISTANT_ID)) {
            preparedStatement.setLong(1, assistantId);
            preparedStatement.setString(2, orderState.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findOrderByStateAndAssistantId method OrderDao class. Unable to get access to database.", e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersByEquipmentIdAtPeriod(long equipmentId, LocalDate startPeriod, LocalDate endPeriod) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_EQUIPMENT_ID_AT_PERIOD)) {
            preparedStatement.setLong(1, equipmentId);
            LocalDateTime startDateTime = LocalDateTime.of(startPeriod, LocalTime.MIN);
            LocalDateTime endDateTime = LocalDateTime.of(endPeriod, LocalTime.MIN);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startDateTime));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(endDateTime));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findOrdersByEquipmentIdAtPeriod method OrderDao class. Unable to get access to database.", e);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrdersByAssistantIdAtPeriod(long assistantId, LocalDate startPeriod, LocalDate endPeriod) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_ASSISTANT_ID_AT_PERIOD)) {
            preparedStatement.setLong(1, assistantId);
            LocalDateTime startDateTime = LocalDateTime.of(startPeriod, LocalTime.MIN);
            LocalDateTime endDateTime = LocalDateTime.of(endPeriod, LocalTime.MIN);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startDateTime));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(endDateTime));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findOrdersByAssistantIdAtPeriod method OrderDao class. Unable to get access to database.", e);
        }
        return orderList;
    }

    @Override
    public int[] createOrders(List<Order> orderList) throws DaoException {
        int[] updateCounts = null;
        Connection connection = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement createOrderStatement = connection.prepareStatement(CREATE_ORDER)){
                for (Order order: orderList) {
                    createOrderStatement.setLong(1,order.getClientId());
                    createOrderStatement.setString(2, order.getState().name());
                    createOrderStatement.setBigDecimal(3, order.getTotalCost());
                    createOrderStatement.setLong(4, order.getEquipmentId());
                    createOrderStatement.setTimestamp(5, Timestamp.valueOf(order.getRentStartTime()));
                    createOrderStatement.setTimestamp(6, Timestamp.valueOf(order.getRentEndTime()));
                    createOrderStatement.setLong(7, order.getAssistantId());
                    createOrderStatement.addBatch();
                }
                updateCounts = createOrderStatement.executeBatch();
            }
        }catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Change cancellation error in createOrders transaction:", throwables);
            }
            throw new DaoException(e);
        }finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Database access error occurs:", throwables);
            }
        }
        return updateCounts;
    }

    @Override
    public boolean payOrder(long userId, BigDecimal newUserBalance, long orderId, OrderState newOrderState) throws DaoException {
        boolean result;
        Connection connection = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement updateUserBalanceStatement = connection.prepareStatement(UPDATE_USER_BALANCE_BY_ID);
                 PreparedStatement updateOrderStateStatement = connection.prepareStatement(UPDATE_ORDER_STATE_BY_ID)){
                updateUserBalanceStatement.setBigDecimal(1, newUserBalance);
                updateUserBalanceStatement.setLong(2, userId);
                int updateBalanceResult = updateUserBalanceStatement.executeUpdate();

                updateOrderStateStatement.setString(1, newOrderState.name());
                updateOrderStateStatement.setLong(2, orderId);
                int updateOrderStateResult = updateOrderStateStatement.executeUpdate();

                result = updateBalanceResult != 0 && updateOrderStateResult != 0;
                connection.commit();
            }
        }catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Change cancellation error in payOrder transaction:", throwables);
            }
            throw new DaoException(e);
        }finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, "Database access error occurs:", throwables);
            }
        }
        return result;
    }

    @Override
    public int updateOrderState(long orderId, OrderState orderState) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATE_BY_ID)) {
            preparedStatement.setString(1, orderState.name());
            preparedStatement.setLong(2, orderId);
            return preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DaoException("Error in updateOrderState method OrderDao class. Unable to get access to database.", e);
        }
    }

    @Override
    public List<Order> findOrdersByStateAndAssistantIdSince(OrderState orderState, long assistantId, LocalDateTime localDateTime) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_STATE_ASSISTANT_ID_SINCE)) {
            preparedStatement.setString(1, orderState.name());
            preparedStatement.setLong(2, assistantId);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(localDateTime));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    Optional<Order> optionalOrder = OrderMapper.getInstance().rowMap(order, resultSet);
                    optionalOrder.ifPresent(orderList::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findOrdersByStateAndAssistantIdSince method OrderDao class. Unable to get access to database.", e);
        }
        return orderList;
    }
}
