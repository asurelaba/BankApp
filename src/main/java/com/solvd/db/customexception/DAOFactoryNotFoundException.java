package com.solvd.db.customexception;

public class DAOFactoryNotFoundException extends Exception{
    public DAOFactoryNotFoundException(String message) {
        super(message);
    }
}
