package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.RequestDoctorDto;
import com.amrat.HospitalManagementApp.dtos.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create-doctor")
    public ResponseDoctorDto createDoctor(@RequestBody RequestDoctorDto doctorDto){
        return adminService.createDoctor(doctorDto);
    }

    @GetMapping("/all-doctors")
    public ResponseEntity<List<RequestDoctorDto>> allDoctors(){
        List<RequestDoctorDto> doctors = adminService.allDoctors();
        return ResponseEntity.ok(doctors);
    }

}
