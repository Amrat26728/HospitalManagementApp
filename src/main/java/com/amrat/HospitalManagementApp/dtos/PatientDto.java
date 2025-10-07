package com.amrat.HospitalManagementApp.dtos;

import lombok.Data;

@Data
public class PatientDto {
    private Long id;
    private String name;
    private String email;
    private int age;
}
