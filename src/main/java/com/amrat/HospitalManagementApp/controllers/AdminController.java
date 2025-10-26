package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.DoctorDto;
import com.amrat.HospitalManagementApp.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create-doctor")
    public DoctorDto createDoctor(@RequestBody DoctorDto doctorDto){
        return adminService.createDoctor(doctorDto);
    }

}
