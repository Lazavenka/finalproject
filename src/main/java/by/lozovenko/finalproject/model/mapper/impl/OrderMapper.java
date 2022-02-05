package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Order;
import by.lozovenko.finalproject.model.entity.OrderState;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.lozovenko.finalproject.model.mapper.impl.AssistantMapper.ASSISTANT_ID;
import static by.lozovenko.finalproject.model.mapper.impl.ClientMapper.CLIENT_ID;
import static by.lozovenko.finalproject.model.mapper.impl.EquipmentMapper.EQUIPMENT_ID;

public class OrderMapper implements CustomRowMapper<Order> {
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_STATE = "order_state";
    public static final String ORDER_TOTAL_COST = "order_total_cost";
    public static final String ORDER_ASSISTANT_ID = "order_assistant_id";
    public static final String RENT_START = "order_rent_start";
    public static final String RENT_END = "order_rent_end";
    public static final String ORDER_EQUIPMENT_ID = "order_equipment_id";


    private static OrderMapper instance;

    public static OrderMapper getInstance(){
        if (instance == null){
            instance = new OrderMapper();
        }
        return instance;
    }
    @Override
    public Optional<Order> rowMap(Order order, ResultSet resultSet) throws DaoException {
        Optional<Order> optionalOrder;
        try {
            order.setId(resultSet.getLong(ORDER_ID));
            order.setClientId(resultSet.getLong(CLIENT_ID));
            order.setAssistantId(resultSet.getLong(ORDER_ASSISTANT_ID));
            order.setEquipmentId(resultSet.getLong(ORDER_EQUIPMENT_ID));
            order.setState(OrderState.valueOf(resultSet.getString(ORDER_STATE)));
            order.setTotalCost(resultSet.getBigDecimal(ORDER_TOTAL_COST));
            order.setRentStartTime(resultSet.getTimestamp(RENT_START).toLocalDateTime());
            order.setRentEndTime(resultSet.getTimestamp(RENT_END).toLocalDateTime());

            optionalOrder = Optional.of(order);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in Order mapping class! {}", e.getMessage());
            optionalOrder = Optional.empty();
        }
        return optionalOrder;
    }
}
