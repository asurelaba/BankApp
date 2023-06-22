package com.solvd.db.factory;

import com.solvd.db.customexception.DAOFactoryNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class DAOFactoryManager {

    private static File mybatisConfig = new File("src/main/resources/DAOFactory-config.properties");
    private static final Logger LOGGER = LogManager.getLogger(DAOFactoryManager.class);
    private static String factory;

    static {
        try {
            Reader reader = new FileReader(mybatisConfig);
            Properties properties = new Properties();
            properties.load(reader);
            factory = properties.getProperty("factory");
            LOGGER.debug("factory from config file: " + factory);
            if (!isValidFactoryName(factory)) {
                throw new DAOFactoryNotFoundException("DaoFactory for " + factory + "is not available");
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (DAOFactoryNotFoundException e) {
            LOGGER.error(e);
        }

    }

    private static boolean isValidFactoryName(String factoryName) {
        switch (factoryName) {
            case "mybatis":
            case "jdbc":
                return true;
            default:
                return false;
        }
    }

    public static AbstractDAOFactory getDAOFactoryInstance() {
        if (factory != null && factory.equals("mybatis")) {
            LOGGER.debug("returning mybatis factory :: " + factory);
            return new MyBatisDAOFactory();
        }
        return new JdbcDAOFactory();
    }

    public static AbstractDAOFactory getDAOFactoryInstance(Class model){
        //return config based on model class from model-daofactory config
        try {
            Reader reader = new FileReader(mybatisConfig);
            Properties properties = new Properties();
            properties.load(reader);
            List<String> mybatisList = List.of(properties.getProperty("mybatis").split(","));
            LOGGER.debug("factory from config file: " + mybatisList + " " + model.getSimpleName());
            if(mybatisList.contains(model.getSimpleName().toLowerCase())){
                LOGGER.debug("valid can return mybatis dao factory");
                return new MyBatisDAOFactory();
            } else{
                return new JdbcDAOFactory();
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
