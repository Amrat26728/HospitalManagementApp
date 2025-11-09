package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.PatientDto;
import com.amrat.HospitalManagementApp.dtos.RequestDoctorDto;
import com.amrat.HospitalManagementApp.dtos.ResponseAppointmentDto;
import com.amrat.HospitalManagementApp.dtos.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import com.amrat.HospitalManagementApp.services.DoctorService;
import com.amrat.HospitalManagementApp.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/create-doctor")
    public ResponseDoctorDto createDoctor(@RequestBody RequestDoctorDto doctorDto){
        return doctorService.createDoctor(doctorDto);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<ResponseDoctorDto>> getDoctors(@RequestParam Boolean active){
        List<ResponseDoctorDto> doctors = doctorService.getDoctors(active);
        return ResponseEntity.ok(doctors);
    }

    @PostMapping("/{doctorId}/delete")
    public ResponseEntity<Void> deleteDoctor(@RequestParam Long doctorId){
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDto>> getAllPatients(@RequestParam(value = "page", defaultValue = "0")  Integer pageNumber,
                                                           @RequestParam(value = "size", defaultValue = "5") Integer pageSize){
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    @GetMapping("/doctors/{doctorId}/appointments")
    public ResponseEntity<List<ResponseAppointmentDto>> getAppointmentsOfDoctor(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                                @RequestParam(value = "size", defaultValue = "5") Integer pageSize, @PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsOfDoctor(pageNumber, pageSize, doctorId));
    }

    @GetMapping("/patients/{patientId}/appointments")
    public ResponseEntity<List<ResponseAppointmentDto>> getAllAppointments(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                           @RequestParam(value = "size", defaultValue = "5") Integer pageSize, @PathVariable Long patientId
    ){
        return ResponseEntity.ok(appointmentService.getAppointmentsOfPatient(pageNumber, pageSize, patientId));
    }

}
