package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.appointment.ResponseAppointmentDto;
import com.amrat.HospitalManagementApp.dtos.doctor.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.dtos.pages.AppointmentResponsePage;
import com.amrat.HospitalManagementApp.dtos.password.ChangePasswordDto;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import com.amrat.HospitalManagementApp.services.AuthService;
import com.amrat.HospitalManagementApp.services.DoctorService;
import com.amrat.HospitalManagementApp.util.CurrentUserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/doctor")
@AllArgsConstructor
public class DoctorController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final CurrentUserInfo currentUserInfo;
    private final AuthService authService;

    @GetMapping("/appointments")
    public ResponseEntity<AppointmentResponsePage> getAppointmentsOfDoctor(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                           @RequestParam(value = "size", defaultValue = "5") Integer pageSize) {
        User user = currentUserInfo.currentUserInfo();
        return ResponseEntity.ok(appointmentService.getAppointmentsOfDoctor(pageNumber, pageSize, user.getId()));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDoctorDto> getProfile(){
        return ResponseEntity.ok(doctorService.doctorProfile());
    }

    @PutMapping("/appointments/{appointmentId}/complete")
    public ResponseEntity<ResponseAppointmentDto> completeAppointment(@PathVariable Long appointmentId){
        return ResponseEntity.ok(appointmentService.completeAppointment(appointmentId));
    }

    // change password
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return ResponseEntity.ok(authService.changePassword(changePasswordDto));
    }

    // get patient's appointments
    @GetMapping("/patient/{patientId}/appointments")
    public AppointmentResponsePage getPatientsAppointments(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
                                                           @PathVariable Long patientId){
        return appointmentService.getAppointmentsOfPatient(pageNumber, pageSize, patientId);
    }

}
