package com.spring_boot_assignment.service;

import com.spring_boot_assignment.commonConstants.CommonConstant;
import com.spring_boot_assignment.controller.EmployeeController;
import com.spring_boot_assignment.dao.EmployeeRepository;

import com.spring_boot_assignment.dto.EmployeeDTO;
import com.spring_boot_assignment.model.Employee;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository empRepo;

    public List<EmployeeDTO> getAllEmployee(Optional<Integer> page, Optional<Integer> size){
        log.info("Inside getAllEmployee");
        Page<Employee> employeePage = empRepo.findAll(PageRequest.of(page.orElse(CommonConstant.PAGE),size.orElse(CommonConstant.size) ));
        log.info("employeePage : "+employeePage);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for(Employee employee : employeePage.getContent()){
            EmployeeDTO employeeDTO = modelMapper.map(employee ,EmployeeDTO.class);
            employeeDTOList.add(employeeDTO);
        }

        log.info("employeeDTOList : "+employeeDTOList);
        return employeeDTOList;
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO){
        Employee employee = modelMapper.map(employeeDTO , Employee.class);
        Employee employee1 = empRepo.save(employee);
        return modelMapper.map(employee1 , EmployeeDTO.class);
    }

    public EmployeeDTO getEmployeeById(int employeeId){
        Employee employee = empRepo.findById(employeeId);

        EmployeeDTO employeeDTO = modelMapper.map(employee,EmployeeDTO.class);

        return employeeDTO;
    }

    public Boolean updateEmployeeByID(int employeeId , EmployeeDTO employeeDTO){
        Employee employee = modelMapper.map(employeeDTO ,Employee.class);

        Employee employeeDb = empRepo.findById(employeeId);

        if(employeeDb == null){
            return false;
        }
        employeeDb.setFirstName(employee.getFirstName());
        employeeDb.setLastName(employee.getLastName());
        employeeDb.setEmailId(employee.getEmailId());
        employeeDb.setPhoneNo(employee.getPhoneNo());

        empRepo.save(employeeDb);
        return true;
    }

    public Boolean deleteEmployeeByID(int employeeId){
        Employee employee = empRepo.findById(employeeId);

        if(employee == null){
            return false;
        }

        empRepo.delete(employee);

        return true;
    }


}
