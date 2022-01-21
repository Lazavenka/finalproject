package by.lozovenko.finalproject.model.mapper.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Client;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClientMapper implements CustomRowMapper<User> {
    public static final String CLIENT_ID = "client_id";
    public static final String BALANCE = "balance";

    private static ClientMapper instance;

    public static ClientMapper getInstance(){
        if (instance == null){
            instance = new ClientMapper();
        }
        return instance;
    }

    @Override
    public Optional<User> rowMap(User entity, ResultSet resultSet) throws DaoException {
        Optional<User> optionalUser;
        try {
            Client client = new Client(entity);
            client.setBalance(resultSet.getBigDecimal(BALANCE));
            client.setClientId(resultSet.getLong(CLIENT_ID));
            optionalUser = Optional.of(client);
        }catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Mapping error in ClientMapper class!", e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
