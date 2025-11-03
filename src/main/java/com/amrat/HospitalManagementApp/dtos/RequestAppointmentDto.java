package com.amrat.HospitalManagementApp.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestAppointmentDto {
    private LocalDateTime appointmentTime;
    private String reason;
    private Long doctorId;
}
