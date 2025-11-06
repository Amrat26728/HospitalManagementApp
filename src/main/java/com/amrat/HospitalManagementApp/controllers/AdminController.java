package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.RequestDoctorDto;
import com.amrat.HospitalManagementApp.dtos.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final DoctorService doctorService;

    @PostMapping("/create-doctor")
    public ResponseDoctorDto createDoctor(@RequestBody RequestDoctorDto doctorDto){
        return doctorService.createDoctor(doctorDto);
    }

    @GetMapping("/all-doctors")
    public ResponseEntity<List<RequestDoctorDto>> allDoctors(){
        List<RequestDoctorDto> doctors = doctorService.allDoctors();
        return ResponseEntity.ok(doctors);
    }

}
