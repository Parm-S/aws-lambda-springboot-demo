package com.spring_boot_assignment.dao;

import com.spring_boot_assignment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee , Integer> {

    @Query(value="Select * from employees where employee_id=?1" , nativeQuery = true)
    Employee findById(int id);
}
