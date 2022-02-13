package by.lozovenko.finalproject.model.pool;

import by.lozovenko.finalproject.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String DATABASE_PROPERTIES = "config/database.properties";
    private static final String PROPERTY_URL = "db.url";
    private static final String PROPERTY_USER = "db.user";
    private static final String PROPERTY_PASSWORD = "db.password";
    private static final String PROPERTY_DRIVER = "db.driver";
    private static final String DATABASE_URL;
    private static final String DATABASE_USER;
    private static final String DATABASE_PASSWORD;
    private static final String DATABASE_DRIVER;

    static {
        try (InputStream inputStream = ConnectionFactory.class.getClassLoader()
                .getResourceAsStream(DATABASE_PROPERTIES)) {
            properties.load(inputStream);
            DATABASE_URL = properties.getProperty(PROPERTY_URL);
            DATABASE_USER = properties.getProperty(PROPERTY_USER);
            DATABASE_PASSWORD = properties.getProperty(PROPERTY_PASSWORD);
            DATABASE_DRIVER = properties.getProperty(PROPERTY_DRIVER);
            Class.forName(DATABASE_DRIVER);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.FATAL, "Properties file:{} not found! {}",
                    DATABASE_PROPERTIES, e.getMessage());
            throw new ExceptionInInitializerError(e);
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Properties load exception in file:{}! {}",
                    DATABASE_PROPERTIES, e.getMessage());
            throw new ExceptionInInitializerError(e);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.FATAL, "Cant find driver {}! Driver not loaded! {}",
                    properties.getProperty(PROPERTY_DRIVER), e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionFactory() {
    }

    static Connection createConnection() throws ConnectionPoolException {
        try {
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Unable to create connection! {}", e.getMessage());
            throw new ConnectionPoolException("Unable to create connection! {}", e);
        }
    }
}
