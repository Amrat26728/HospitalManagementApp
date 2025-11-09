package com.amrat.HospitalManagementApp.dtos.appointment;

import com.amrat.HospitalManagementApp.dtos.doctor.ResponseDoctorDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ResponseAppointmentDto {
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;
    private ResponseDoctorDto doctor;
    private boolean canceled;
    private boolean done;
}
