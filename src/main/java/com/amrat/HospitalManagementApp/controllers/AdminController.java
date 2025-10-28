package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.DoctorDto;
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
    public DoctorDto createDoctor(@RequestBody DoctorDto doctorDto){
        return adminService.createDoctor(doctorDto);
    }

    @GetMapping("/all-doctors")
    public ResponseEntity<List<DoctorDto>> allDoctors(){
        List<DoctorDto> doctors = adminService.allDoctors();
        return ResponseEntity.ok(doctors);
    }

}
