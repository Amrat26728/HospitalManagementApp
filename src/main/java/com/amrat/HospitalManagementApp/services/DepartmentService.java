package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.department.RequestDepartmentDto;
import com.amrat.HospitalManagementApp.dtos.department.ResponseDepartmentDto;
import com.amrat.HospitalManagementApp.entities.Department;
import com.amrat.HospitalManagementApp.entities.Doctor;
import com.amrat.HospitalManagementApp.repositories.DepartmentRepository;
import com.amrat.HospitalManagementApp.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;

    // fetch all departments
    public List<ResponseDepartmentDto> getAllDepartments(){
        return departmentRepository.findAll().stream().map((department -> modelMapper.map(department, ResponseDepartmentDto.class))).toList();
    }

    // create department
    @Transactional
    public ResponseDepartmentDto createDepartment(RequestDepartmentDto departmentDto){
        Department dept = departmentRepository.findByName(departmentDto.getName());

        if (dept != null){
            throw new IllegalArgumentException("Department with "+departmentDto.getName()+" already exists.");
        }

        Department department = new Department(departmentDto.getName(), departmentDto.getDescription());

        departmentRepository.save(department);

        return modelMapper.map(department, ResponseDepartmentDto.class);
    }

    // add doctor to department
    @Transactional
    public ResponseDepartmentDto addDoctor(Long doctorId, Long departmentId){
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new IllegalArgumentException("Department does not exist."));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor does not exist."));

        List<Doctor> doctors = department.getDoctors();
        if (doctors.contains(doctor)){
            throw new IllegalArgumentException("Doctor already exist in "+department.getName()+" department.");
        }
        doctors.add(doctor);

        departmentRepository.save(department);
        doctor.changeDepartment(department);
        doctorRepository.save(doctor);

        return modelMapper.map(department, ResponseDepartmentDto.class);
    }

}
