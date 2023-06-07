package com.solvd.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {
    private ArrayBlockingQueue<Connection> pool;
    private ArrayBlockingQueue<Connection> inUseConnections;
    private int maxConnections;
    private static String url;
    private static String username;
    private static String password;

    //load DB properties
    static {
        try {
            FileReader fileReader = new FileReader(".\\src\\main\\resources\\db.properties");
            Properties properties = new Properties();
            properties.load(fileReader);
            url = properties.getProperty("url");
            username = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ConnectionPool() {
        maxConnections = 1;
    }

    public synchronized Connection getConnection() {
        //System.out.println(Thread.currentThread().getName() + " is trying to obtain connection");
        if (pool == null) {
            pool = new ArrayBlockingQueue<>(maxConnections);
        }
        if (!pool.isEmpty()) {
            Connection connection = null;
            try {
                connection = pool.take();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            inUseConnections.add(connection);
            return connection;
        } else if (inUseConnections == null || inUseConnections.size() < maxConnections) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                System.out.println("in getConnection " + url + username + password);
                if (inUseConnections == null) {
                    inUseConnections = new ArrayBlockingQueue<>(maxConnections);
                }
                inUseConnections.add(connection);
                return connection;
            } catch (SQLException e) {
                System.out.println("exception in get Connection");
                System.out.println(e);
            }
        }
        return null;
    }

    public void releaseConnection(Connection connection) {
        pool.add(connection);
        inUseConnections.remove(connection);
    }
}
