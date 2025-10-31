package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.AppointmentDto;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final AppointmentService appointmentService;

    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(@PathVariable Long patientId,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ){

        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfPatient(patientId, pageNumber, pageSize));
    }

}
