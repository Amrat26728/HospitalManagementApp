package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.RequestDoctorDto;
import com.amrat.HospitalManagementApp.dtos.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.entities.Doctor;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.entities.types.RoleType;
import com.amrat.HospitalManagementApp.repositories.DoctorRepository;
import com.amrat.HospitalManagementApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public ResponseDoctorDto createDoctor(RequestDoctorDto doctorDto){
        User user = userRepository.findByUsername(doctorDto.getEmail()).orElse(null);
        if (user != null){
            throw new IllegalArgumentException("User already exists.");
        }

        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.DOCTOR);

        user = User.builder()
                .username(doctorDto.getEmail())
                .password(passwordEncoder.encode("12345"))
                .roles(roles)
                .build();

        userRepository.save(user);

        Doctor doctor = Doctor.builder()
                .user(user)
                .email(doctorDto.getEmail())
                .name(doctorDto.getName())
                .qualifications(doctorDto.getQualifications())
                .build();

        doctor = doctorRepository.save(doctor);

        return modelMapper.map(doctor, ResponseDoctorDto.class);
    }

    public List<RequestDoctorDto> allDoctors(){
        List<Doctor> doctors = doctorRepository.allDoctors();

        return doctors.stream().map(doctor -> modelMapper.map(doctor, RequestDoctorDto.class)).toList();
    }

}
