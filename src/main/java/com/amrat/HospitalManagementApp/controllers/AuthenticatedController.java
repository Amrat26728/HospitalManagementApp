package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.entities.types.AppointmentStatus;
import com.amrat.HospitalManagementApp.entities.types.PrescriptionStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticated")
public class AuthenticatedController {

    @GetMapping("/appointment-status")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public AppointmentStatus[] getAppointmentStatus(){
        return AppointmentStatus.values();
    }

    @GetMapping("/prescription-status")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    public PrescriptionStatus[] getPrescriptionStatus(){
        return PrescriptionStatus.values();
    }

}
