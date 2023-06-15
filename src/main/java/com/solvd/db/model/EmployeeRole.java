package com.solvd.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties({"roleId","jobDescription"})
public class EmployeeRole {

    @XmlAttribute(name = "id")
    private int roleId;

    @XmlElement(name = "roleName")
    @JsonProperty("roleName")
    private String roleName;

    @XmlElement(name = "jobDescription")
    private String jobDescription;

    @XmlElement(name = "salary")
    @JsonProperty("salary")
    private int salary;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeRole{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", salary=" + salary +
                '}';
    }
}
