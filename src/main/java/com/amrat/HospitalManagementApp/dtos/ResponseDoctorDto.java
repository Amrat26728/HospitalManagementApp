package com.amrat.HospitalManagementApp.dtos;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ResponseDoctorDto {
    private Long id;
    private String name;
    private String email;
    private Set<String> qualifications = new HashSet<>();
}
