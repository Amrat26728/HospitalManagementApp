package com.amrat.HospitalManagementApp.dtos;

import lombok.Data;

@Data
public class DoctorDto {
    private Long id;
    private String name;
    private String email;
    private String qualifications;
}
