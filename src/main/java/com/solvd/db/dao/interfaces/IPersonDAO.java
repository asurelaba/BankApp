package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Person;

import java.util.List;

public interface IPersonDAO extends IBaseDAO<Person> {

    String TABLE_NAME = "persons";

    public List<Person> getPersonByLastName(String lastName);
}
