package com.solvd.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

    @XmlAttribute(name = "id")
    @JsonProperty("employeeId")
    private int employeeId;

    @XmlElement(name = "person")
    @JsonProperty("person")
    private Person person;

    @XmlTransient
    @JsonProperty("employeeRole")
    private EmployeeRole employeeRole;

    @XmlElement(name = "manager")
    @JsonIgnore
    private Employee manager;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId + "\n" +
                ", person=" + person + "\n" +
                ", employeeRole=" + employeeRole + "\n" +
                ", manager=" + manager + "\n" +
                '}';
    }
}
