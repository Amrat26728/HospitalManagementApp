package com.amrat.HospitalManagementApp.dtos.doctor;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RequestDoctorDto {
    private String name;
    private String email;
    private Set<String> qualifications = new HashSet<>();
    private Long departmentId;
}
