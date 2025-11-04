package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.RequestAppointmentDto;
import com.amrat.HospitalManagementApp.dtos.ResponseAppointmentDto;
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

    @GetMapping("/appointments")
    public ResponseEntity<List<ResponseAppointmentDto>> getAllAppointments(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                          @RequestParam(value = "size", defaultValue = "2") Integer pageSize
    ){
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfPatient(pageNumber, pageSize));
    }

    @PostMapping("/appointments/book-appointment")
    public ResponseEntity<ResponseAppointmentDto> bookAppointment(@RequestBody RequestAppointmentDto appointmentDto){
        return ResponseEntity.ok(appointmentService.bookAppointment(appointmentDto));
    }

    @PostMapping("/{appointmentId}/cancel-appointment")
    public ResponseEntity<Boolean> cancelAppointment(@PathVariable Long appointmentId){
        return ResponseEntity.ok(appointmentService.cancelAppointment(appointmentId));
    }

}
