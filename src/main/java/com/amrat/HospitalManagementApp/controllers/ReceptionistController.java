package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.pages.AppointmentResponsePage;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receptionist")
@RequiredArgsConstructor
public class ReceptionistController {

    private final AppointmentService appointmentService;

    @GetMapping("/pending-appointments")
    public ResponseEntity<AppointmentResponsePage> getPendingAppointments(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber){
        return ResponseEntity.ok(appointmentService.getPendingAppointments(pageNumber));
    }

}
