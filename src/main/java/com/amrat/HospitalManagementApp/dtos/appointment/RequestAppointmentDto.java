package com.amrat.HospitalManagementApp.dtos.appointment;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RequestAppointmentDto {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;
    private Long doctorId;
}
