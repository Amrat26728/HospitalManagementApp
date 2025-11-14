package com.amrat.HospitalManagementApp.dtos.doctor;

import com.amrat.HospitalManagementApp.dtos.department.ResponseDepartmentDto;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DoctorDepartmentDto {
    private Long id;
    private String name;
    private String email;
    private Set<String> qualifications = new HashSet<>();
    private ResponseDepartmentDto department;
}
