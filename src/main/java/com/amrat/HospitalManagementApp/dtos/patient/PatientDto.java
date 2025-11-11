package com.amrat.HospitalManagementApp.dtos.patient;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PatientDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createAt;
}
