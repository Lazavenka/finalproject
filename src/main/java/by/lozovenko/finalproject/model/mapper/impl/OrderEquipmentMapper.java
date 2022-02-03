package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.OrderEquipment;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.lozovenko.finalproject.model.mapper.impl.AssistantMapper.ASSISTANT_ID;
import static by.lozovenko.finalproject.model.mapper.impl.EquipmentMapper.EQUIPMENT_ID;
import static by.lozovenko.finalproject.model.mapper.impl.OrderMapper.ORDER_ID;

public class OrderEquipmentMapper implements CustomRowMapper<OrderEquipment> {

    public static final String RENT_START = "rent_start";
    public static final String RENT_END = "rent_end";

    private static OrderEquipmentMapper instance;

    public static OrderEquipmentMapper getInstance(){
        if (instance == null){
            instance = new OrderEquipmentMapper();
        }
        return instance;
    }
    @Override
    public Optional<OrderEquipment> rowMap(OrderEquipment orderEquipment, ResultSet resultSet) throws DaoException {
        Optional<OrderEquipment> optionalOrderEquipment;
        try {
            orderEquipment.setId(resultSet.getLong(ORDER_ID));
            orderEquipment.setEquipmentId(resultSet.getLong(EQUIPMENT_ID));
            orderEquipment.setRentStartTime(resultSet.getTimestamp(RENT_START).toLocalDateTime());
            orderEquipment.setRentEndTime(resultSet.getTimestamp(RENT_END).toLocalDateTime());
            orderEquipment.setAssistantId(resultSet.getLong(ASSISTANT_ID));
            optionalOrderEquipment = Optional.of(orderEquipment);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in OrderEquipment mapping class! {}", e.getMessage());
            optionalOrderEquipment = Optional.empty();
        }
        return optionalOrderEquipment;
    }
}
