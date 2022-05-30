package com.spring_boot_assignment.service;

import com.spring_boot_assignment.commonConstants.CommonConstant;
import com.spring_boot_assignment.dao.DepartmentRepository;
import com.spring_boot_assignment.dto.DepartmentDTO;
import com.spring_boot_assignment.model.Department;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO){
        Department department = modelMapper.map(departmentDTO,Department.class);

        Department department1 = departmentRepository.save(department);

        return modelMapper.map(department1 , DepartmentDTO.class);
    }

    public List<DepartmentDTO> getAllDepartment(Optional<Integer> page, Optional<Integer> size){
        Page<Department> departments = departmentRepository.findAll(PageRequest.of(page.orElse(CommonConstant.PAGE),size.orElse(CommonConstant.size)));

        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        for(Department department : departments.getContent()){
            DepartmentDTO departmentDTO = modelMapper.map(department ,DepartmentDTO.class);
            departmentDTOList.add(departmentDTO);
        }

        return departmentDTOList;
    }

    public DepartmentDTO getEmployeeById(int departmentId){
        Department department = departmentRepository.findById(departmentId);

        DepartmentDTO departmentDTO = modelMapper.map(department ,DepartmentDTO.class);

        return departmentDTO;
    }

    public Boolean updateDepartmentById(int departmentId , DepartmentDTO departmentDTO){
        Department department = modelMapper.map(departmentDTO , Department.class);

        Department departmentDb = departmentRepository.findById(departmentId);

        if(departmentDb == null){
            return  false;
        }

        departmentDb.setName(department.getName());
        departmentDb.setEmailId(department.getEmailId());
        departmentDb.setPhoneNo(department.getPhoneNo());
        departmentDb.setEmployee(department.getEmployee());

        departmentRepository.save(departmentDb);

        return true;
    }

    public Boolean deleteDepartmentById(int departmentId){
        Department department= departmentRepository.findById(departmentId);

        if(department == null){
            return false;
        }

        departmentRepository.delete(department);

        return true;
    }
}
