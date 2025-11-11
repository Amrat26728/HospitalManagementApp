package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.pages.AppointmentResponsePage;
import com.amrat.HospitalManagementApp.dtos.password.ChangePasswordDto;
import com.amrat.HospitalManagementApp.dtos.patient.PatientDto;
import com.amrat.HospitalManagementApp.dtos.appointment.RequestAppointmentDto;
import com.amrat.HospitalManagementApp.dtos.appointment.ResponseAppointmentDto;
import com.amrat.HospitalManagementApp.dtos.doctor.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import com.amrat.HospitalManagementApp.services.AuthService;
import com.amrat.HospitalManagementApp.services.DoctorService;
import com.amrat.HospitalManagementApp.services.PatientService;
import com.amrat.HospitalManagementApp.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final CurrentUserInfo currentUserInfo;
    private final AuthService authService;

    @GetMapping("/appointments")
    public ResponseEntity<AppointmentResponsePage> getAllAppointments(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                      @RequestParam(value = "size", defaultValue = "5") Integer pageSize
    ){
        User user = currentUserInfo.currentUserInfo();
        return ResponseEntity.ok(appointmentService.getAppointmentsOfPatient(pageNumber, pageSize, user.getId()));
    }

    @PostMapping("/appointments/book-appointment")
    public ResponseEntity<ResponseAppointmentDto> bookAppointment(@RequestBody RequestAppointmentDto appointmentDto){
        return ResponseEntity.ok(appointmentService.bookAppointment(appointmentDto));
    }

    @PutMapping("/{appointmentId}/cancel-appointment")
    public ResponseEntity<ResponseAppointmentDto> cancelAppointment(@PathVariable Long appointmentId){
        return ResponseEntity.ok(appointmentService.cancelAppointment(appointmentId));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<ResponseDoctorDto>> getActiveDoctors(){
        return ResponseEntity.ok(doctorService.getDoctors(true));
    }

    @GetMapping("/me")
    public PatientDto profile(){
        return patientService.profile();
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return ResponseEntity.ok(authService.changePassword(changePasswordDto));
    }

}
