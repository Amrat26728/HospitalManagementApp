package com.amrat.HospitalManagementApp.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseAppointmentDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private ResponseDoctorDto doctor;
}
