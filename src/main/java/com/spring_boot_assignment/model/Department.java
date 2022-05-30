package com.spring_boot_assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @Column(name="department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Column(name = "department_name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNo;

    @Column(name = "email_id")
    private String emailId;


    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL},optional = false)
    @JoinColumn(name="employee_id",referencedColumnName = "employee_id")
    private Employee employee;

    public Department() {
    }

    public Department(String name, String phoneNo, String emailId) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", employee=" + employee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return departmentId == that.departmentId && name.equals(that.name) && Objects.equals(phoneNo, that.phoneNo) && emailId.equals(that.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, name, phoneNo, emailId);
    }
}
