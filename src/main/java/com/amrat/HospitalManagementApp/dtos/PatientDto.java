package com.amrat.HospitalManagementApp.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PatientDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private List<ResponseAppointmentDto> appointments;
}
