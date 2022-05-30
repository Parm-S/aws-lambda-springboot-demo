package com.spring_boot_assignment.dao;

import com.spring_boot_assignment.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department , Integer> {
    @Query(value = "Select * from department where department_id=?1",nativeQuery = true)
    Department findById(int id);
}
