package com.spring_boot_assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring_boot_assignment.model.Employee;

public class DepartmentDTO {

    private  int departmentId;
    private String name;
    private String emailId;
    private String phoneNo;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee employee;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
