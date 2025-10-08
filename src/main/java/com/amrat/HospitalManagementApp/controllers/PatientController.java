package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.AppointmentDto;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ){
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfPatient(pageNumber, pageSize));
    }

}
