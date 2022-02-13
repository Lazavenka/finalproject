package by.lozovenko.finalproject.model.pool;

import by.lozovenko.finalproject.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();

    private static CustomConnectionPool INSTANCE;

    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final Lock createPoolLock = new ReentrantLock(true);
    private static final int DEFAULT_POOL_SIZE = 8;
    private static final int ADDITIONAL_CREATION_ATTEMPTS = 3;

    private final BlockingDeque<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnections;

    private CustomConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                ProxyConnection connection = new ProxyConnection(ConnectionFactory.createConnection());
                freeConnections.add(connection);
            } catch (ConnectionPoolException e) {
                LOGGER.log(Level.ERROR, "Connection was not created. {}", e.getMessage());
            }
        }
        int actualPoolSize = freeConnections.size();
        if (actualPoolSize == 0) {
            LOGGER.log(Level.FATAL, "No connection created!");
            throw new ExceptionInInitializerError("Unable to create any connection to database.");
        } else if (actualPoolSize < DEFAULT_POOL_SIZE) {
            for (int i = 0; i < ADDITIONAL_CREATION_ATTEMPTS; i++) {
                actualPoolSize = freeConnections.size();
                int connectionsLack = DEFAULT_POOL_SIZE - actualPoolSize;
                for (int j = 0; j < connectionsLack; j++) {
                    try {
                        ProxyConnection connection = new ProxyConnection(ConnectionFactory.createConnection());
                        freeConnections.add(connection);
                    } catch (ConnectionPoolException e) {
                        LOGGER.log(Level.ERROR, "Connection was not created. {}", e.getMessage());
                    }
                }
            }
        }
    }

    public static CustomConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                createPoolLock.lock();
                if (INSTANCE == null) {
                    INSTANCE = new CustomConnectionPool();
                }
            } finally {
                createPoolLock.unlock();
            }
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;

        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Thread interrupted exception! {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean success = false;
        try {
            if (connection.getClass() != ProxyConnection.class) {
                throw new ConnectionPoolException("Incorrect connection type!");
            }
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            givenAwayConnections.remove(proxyConnection);
            freeConnections.put(proxyConnection);
            success = true;
        } catch (ConnectionPoolException | InterruptedException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            Thread.currentThread().interrupt();
        }
        return success;
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
            }
        });
    }
}
