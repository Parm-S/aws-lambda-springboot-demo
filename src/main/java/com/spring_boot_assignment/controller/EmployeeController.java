package com.spring_boot_assignment.controller;

import com.spring_boot_assignment.dto.EmployeeDTO;
import com.spring_boot_assignment.filter.CognitoIdentityFilter;
import com.spring_boot_assignment.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;

@RestController
@EnableWebMvc
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public EmployeeDTO addEmployeeRequest(@RequestBody EmployeeDTO employeeDTO){

        log.info("EmployeeDTO : {} , addEmployeeRequest Controller",employeeDTO.getFirstName());
        if(employeeDTO.getFirstName() == null || employeeDTO.getLastName() == null || employeeDTO.getEmailId() == null || employeeDTO.getPhoneNo() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
      EmployeeDTO addEmployeeDTO = employeeService.addEmployee(employeeDTO);
      return addEmployeeDTO;
    };

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public List<EmployeeDTO> getAllEmployee(@RequestParam Optional<Integer> page , @RequestParam Optional<Integer> size){
        log.info("getAllEmployee Controller , page :{} , size :{} ", page , size);
        if(page.isPresent()){
            if(page.get()<=0){
                throw new IllegalArgumentException("Page cannot be less than 0");
            } else {
                page = Optional.of(page.get() - 1);
            }
        } else {
            page = Optional.of(0);
        }
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployee(page ,size);
        return employeeDTOList;
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public EmployeeDTO getEmloyeeById(@PathVariable int id){
        log.info("getEmloyeeById Controller , id : {}", id);
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);

            return employeeDTO;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public void updateEmployeeById(@RequestBody EmployeeDTO employeeDTO,@PathVariable int id){
        log.info("updateEmployeeById Controller , id : {}", id);
        boolean employee = employeeService.updateEmployeeByID(id , employeeDTO);

        if(!employee){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public void deleteEmployeeById(@PathVariable int id){
        log.info("deleteEmployeeById Controller , id : {}", id);
        boolean employee = employeeService.deleteEmployeeByID(id);

        if(!employee){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
