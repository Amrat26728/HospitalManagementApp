package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.DoctorDto;
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
    public DoctorDto createDoctor(DoctorDto doctorDto){
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

        doctorRepository.save(doctor);

        return modelMapper.map(doctor, DoctorDto.class);
    }

    public List<DoctorDto> allDoctors(){
        List<Doctor> doctors = doctorRepository.allDoctors();

        return doctors.stream().map(doctor -> modelMapper.map(doctor, DoctorDto.class)).toList();
    }

}
