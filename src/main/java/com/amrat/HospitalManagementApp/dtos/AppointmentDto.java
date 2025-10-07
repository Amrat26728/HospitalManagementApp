package com.amrat.HospitalManagementApp.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private DoctorDto doctor;
    private PatientDto patient;
}
