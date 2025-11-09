package com.amrat.HospitalManagementApp.dtos.appointment;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PatientDoctorAppointmentsDto {
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;
    private Long doctorId;
    private boolean canceled;
    private boolean done;
}
