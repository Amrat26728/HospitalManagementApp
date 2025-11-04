package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.entities.Doctor;
import com.amrat.HospitalManagementApp.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    // get all doctors
    public List<ResponseDoctorDto> getAllDoctors(){
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream().map(doctor -> modelMapper.map(doctor, ResponseDoctorDto.class)).toList();
    }

}
