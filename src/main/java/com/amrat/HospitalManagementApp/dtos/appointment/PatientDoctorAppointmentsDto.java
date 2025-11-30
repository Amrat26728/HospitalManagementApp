package com.amrat.HospitalManagementApp.dtos.appointment;

import com.amrat.HospitalManagementApp.entities.types.AppointmentStatus;
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
    private AppointmentStatus status;
}
