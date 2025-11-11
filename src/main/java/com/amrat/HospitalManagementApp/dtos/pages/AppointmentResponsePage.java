package com.amrat.HospitalManagementApp.dtos.pages;

import com.amrat.HospitalManagementApp.dtos.appointment.PatientDoctorAppointmentsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AppointmentResponsePage {
    private List<PatientDoctorAppointmentsDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;
}

