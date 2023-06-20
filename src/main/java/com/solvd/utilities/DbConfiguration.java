package com.solvd.utilities;

import com.solvd.db.service.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DbConfiguration {

    private static final Logger LOGGER = LogManager.getLogger(DbConfiguration.class);
    private static String url;
    private static String username;
    private static String password;

    static {
        try (FileReader fileReader = new FileReader(".\\src\\main\\resources\\db.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            url = properties.getProperty("url");
            username = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
