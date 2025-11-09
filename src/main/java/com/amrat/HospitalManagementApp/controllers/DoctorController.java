package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.ResponseAppointmentDto;
import com.amrat.HospitalManagementApp.dtos.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import com.amrat.HospitalManagementApp.services.DoctorService;
import com.amrat.HospitalManagementApp.util.CurrentUserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@AllArgsConstructor
public class DoctorController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final CurrentUserInfo currentUserInfo;

    @GetMapping("/appointments")
    public ResponseEntity<List<ResponseAppointmentDto>> getAppointmentsOfDoctor(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber, @RequestParam(value = "size", defaultValue = "2") Integer pageSize) {
        User user = currentUserInfo.currentUserInfo();
        return ResponseEntity.ok(appointmentService.getAppointmentsOfDoctor(pageNumber, pageSize, user.getId()));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDoctorDto> getProfile(){
        return ResponseEntity.ok(doctorService.doctorProfile());
    }

    @PostMapping("/appointments/{appointmentId}/complete")
    public ResponseEntity<ResponseAppointmentDto> completeAppointment(@PathVariable Long appointmentId){
        return ResponseEntity.ok(appointmentService.completeAppointment(appointmentId));
    }

}
