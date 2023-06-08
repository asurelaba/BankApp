package com.solvd.db.model;

public class EmployeeRole {

    private int roleId;
    private String roleName;
    private String jobDescription;
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
