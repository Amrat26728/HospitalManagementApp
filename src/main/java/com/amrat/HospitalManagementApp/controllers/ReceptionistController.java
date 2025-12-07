package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.appointment.ResponseAppointmentDto;
import com.amrat.HospitalManagementApp.dtos.pages.AppointmentResponsePage;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receptionist")
@RequiredArgsConstructor
public class ReceptionistController {

    private final AppointmentService appointmentService;

    @GetMapping("/pending-appointments")
    public ResponseEntity<AppointmentResponsePage> getPendingAppointments(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber){
        return ResponseEntity.ok(appointmentService.getPendingAppointments(pageNumber));
    }

    @PutMapping("/pending-appointments/{appointmentId}")
    public ResponseEntity<ResponseAppointmentDto> confirmAppointment(Long appointmentId){
        return ResponseEntity.ok(appointmentService.confirmAppointment(appointmentId));
    }

}
