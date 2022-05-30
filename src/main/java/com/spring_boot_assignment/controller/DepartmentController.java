package com.spring_boot_assignment.controller;

import com.spring_boot_assignment.dto.DepartmentDTO;
import com.spring_boot_assignment.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping("/department")
    public DepartmentDTO addDepartment(@RequestBody DepartmentDTO departmentDTO){
        log.info("addDepartment DepartmentDTO: {}",departmentDTO);
        if(departmentDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        DepartmentDTO addDepartmentDTO = departmentService.addDepartment(departmentDTO);
        log.info("addDepartmentDTO: {}",addDepartmentDTO);
        return addDepartmentDTO;
    }

    @GetMapping("/department")
    public List<DepartmentDTO> getAllDepartment(@RequestParam Optional<Integer> page,@RequestParam Optional<Integer> size){
        log.info("getAllDepartment page: {},size: {}",page,size);
        if(page.isPresent()){
            if(page.get() <= 0){
                throw new IllegalArgumentException("Page cannot be less than 0");
            } else {
                page = Optional.of(page.get() - 1);
            }
        } else {
            page = Optional.of(0);
        }
        if(size.isPresent()){
            if(size.get() <= 0){
                throw new IllegalArgumentException("Size cannot be less than 0");
            } else {
                size = Optional.of(size.get());
            }

        } else {
            size = Optional.of(10);        }

        List<DepartmentDTO> departmentDTOList = departmentService.getAllDepartment(page,size);
        log.info("getAllDepartment departmentDTOList: {}",departmentDTOList);
        return  departmentDTOList;
    }

    @GetMapping("/department/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable int id){
        log.info("getDepartmentById id: {}",id);
        try{
            DepartmentDTO departmentDTO = departmentService.getEmployeeById(id);
            log.info("getDepartmentById departmentDTO: {}",departmentDTO);
            return  departmentDTO;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/department/{id}")
    public void updateDepartmentById(@RequestBody DepartmentDTO departmentDTO,@PathVariable int id){
        log.info("updateDepartmentById departmentDTO: {},id: {}",departmentDTO,id);
        boolean department = departmentService.updateDepartmentById(id,departmentDTO);

        if(!department){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/department/{id}")
    public void deleteDepartmentById(@PathVariable int id){
        log.info("deleteDepartmentById id: {}",id);
        boolean department = departmentService.deleteDepartmentById(id);
        if(!department){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
