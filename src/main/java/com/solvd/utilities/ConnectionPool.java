package com.solvd.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private ArrayBlockingQueue<Connection> pool;
    private ArrayBlockingQueue<Connection> inUseConnections;
    private int maxConnections;
    private static ConnectionPool connectionPool;
    private static String url = DbConfiguration.getUrl();
    private static String username = DbConfiguration.getUsername();
    private static String password = DbConfiguration.getPassword();

    private ConnectionPool() {
        maxConnections = 1;
    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public synchronized Connection getConnection() {
        if (pool == null) {
            pool = new ArrayBlockingQueue<>(maxConnections);
        }
        if (!pool.isEmpty()) {
            Connection connection = null;
            try {
                connection = pool.take();
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
            inUseConnections.add(connection);
            return connection;
        } else if (inUseConnections == null || inUseConnections.size() < maxConnections) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                if (inUseConnections == null) {
                    inUseConnections = new ArrayBlockingQueue<>(maxConnections);
                }
                inUseConnections.add(connection);
                return connection;
            } catch (SQLException e) {
                LOGGER.error("exception in get Connection");
                LOGGER.error(e);
            }
        }
        return null;
    }

    public void releaseConnection(Connection connection) {
        pool.add(connection);
        inUseConnections.remove(connection);
    }
}
