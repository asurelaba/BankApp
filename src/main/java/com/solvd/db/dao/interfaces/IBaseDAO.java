package com.solvd.db.dao.interfaces;

import com.solvd.utilities.ConnectionPool;

import java.util.List;

public interface IBaseDAO<T> {

    ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    void insert(T t);

    T getById(int id);

    void update(T t);

    void delete(T t);

    List<T> getAll();
}
