package com.solvd.db.customexception;

public class DAONotFoundException extends Exception {
    public DAONotFoundException(String message) {
        super(message);
    }
}
