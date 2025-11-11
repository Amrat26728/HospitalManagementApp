package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.department.RequestDepartmentDto;
import com.amrat.HospitalManagementApp.dtos.department.ResponseDepartmentDto;
import com.amrat.HospitalManagementApp.dtos.doctor.RequestDoctorDto;
import com.amrat.HospitalManagementApp.dtos.doctor.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.dtos.pages.AppointmentResponsePage;
import com.amrat.HospitalManagementApp.dtos.pages.PatientResponsePage;
import com.amrat.HospitalManagementApp.services.AppointmentService;
import com.amrat.HospitalManagementApp.services.DepartmentService;
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
    private final DepartmentService departmentService;

    // create doctor
    @PostMapping("/create-doctor")
    public ResponseDoctorDto createDoctor(@RequestBody RequestDoctorDto doctorDto){
        return doctorService.createDoctor(doctorDto);
    }

    // fetch doctors and filter active or non-active
    @GetMapping("/doctors")
    public ResponseEntity<List<ResponseDoctorDto>> getDoctors(@RequestParam Boolean active){
        List<ResponseDoctorDto> doctors = doctorService.getDoctors(active);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/all-doctors")
    public ResponseEntity<List<ResponseDoctorDto>> getDoctors(){
        List<ResponseDoctorDto> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    // fetch patients
    @GetMapping("/patients")
    public ResponseEntity<PatientResponsePage> getAllPatients(@RequestParam(value = "page", defaultValue = "0")  Integer pageNumber,
                                                              @RequestParam(value = "size", defaultValue = "5") Integer pageSize){
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    // fetch doctor appointments
    @GetMapping("/doctors/{doctorId}/appointments")
    public ResponseEntity<AppointmentResponsePage> getAppointmentsOfDoctor(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                           @RequestParam(value = "size", defaultValue = "5") Integer pageSize, @PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsOfDoctor(pageNumber, pageSize, doctorId));
    }

    // fetch patient appointments
    @GetMapping("/patients/{patientId}/appointments")
    public ResponseEntity<AppointmentResponsePage> getAllAppointments(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                           @RequestParam(value = "size", defaultValue = "5") Integer pageSize, @PathVariable Long patientId
    ){
        return ResponseEntity.ok(appointmentService.getAppointmentsOfPatient(pageNumber, pageSize, patientId));
    }

    // fetch departments
    @GetMapping("/departments")
    public ResponseEntity<List<ResponseDepartmentDto>> getDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // create department
    @PostMapping("/departments/create-department")
    public ResponseEntity<ResponseDepartmentDto> createDepartment(@RequestBody RequestDepartmentDto departmentDto){
        return ResponseEntity.ok(departmentService.createDepartment(departmentDto));
    }

    // change/assign department of a doctor
    @PutMapping("/doctors/{doctorId}/departments/{departmentId}")
    public ResponseEntity<ResponseDoctorDto> changeDepartment(@PathVariable Long doctorId, @PathVariable Long departmentId){
        return ResponseEntity.ok(doctorService.changeDepartment(doctorId, departmentId));
    }

    // add doctor to department
    @PutMapping("doctors/{doctorId}/departments/{departmentId}/add-doctor")
    public ResponseEntity<ResponseDepartmentDto> addDoctor(@PathVariable Long doctorId, @PathVariable Long departmentId){
        return ResponseEntity.ok(departmentService.addDoctor(doctorId, departmentId));
    }

    // fetch doctors of a department
    @GetMapping("/departments/{departmentId}/doctors")
    public ResponseEntity<List<ResponseDoctorDto>> getDoctorsOfDepartment(@PathVariable Long departmentId){
        return ResponseEntity.ok(doctorService.getDoctorsOfDepartment(departmentId));
    }
}
