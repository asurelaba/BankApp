package com.solvd.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DbConfiguration {
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
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
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
