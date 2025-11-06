package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.RequestDoctorDto;
import com.amrat.HospitalManagementApp.dtos.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.entities.Doctor;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.entities.types.RoleType;
import com.amrat.HospitalManagementApp.repositories.DoctorRepository;
import com.amrat.HospitalManagementApp.repositories.UserRepository;
import com.amrat.HospitalManagementApp.util.CurrentUserInfo;
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
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserInfo currentUserInfo;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // get all doctors
    public List<ResponseDoctorDto> getAllDoctors(){
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream().map(doctor -> modelMapper.map(doctor, ResponseDoctorDto.class)).toList();
    }

    // get doctor profile
    public ResponseDoctorDto doctorProfile(){
        User user = currentUserInfo.currentUserInfo();

        Doctor doctor = doctorRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Doctor does not exist."));

        return modelMapper.map(doctor, ResponseDoctorDto.class);
    }

    @Transactional
    public ResponseDoctorDto createDoctor(RequestDoctorDto doctorDto){
        User user = userRepository.findByUsername(doctorDto.getEmail()).orElse(null);
        if (user != null){
            throw new IllegalArgumentException("User already exists.");
        }

        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.DOCTOR);

        user = new User(doctorDto.getEmail(), passwordEncoder.encode("1234"), roles);

        userRepository.save(user);

        Doctor doctor = new Doctor(user, doctorDto.getName(), doctorDto.getEmail(), doctorDto.getQualifications());

        doctor = doctorRepository.save(doctor);

        emailService.sendCredentials(doctorDto.getEmail(), doctorDto.getEmail(), "1234");

        return modelMapper.map(doctor, ResponseDoctorDto.class);
    }

    public List<RequestDoctorDto> allDoctors(){
        List<Doctor> doctors = doctorRepository.allDoctors();

        return doctors.stream().map(doctor -> modelMapper.map(doctor, RequestDoctorDto.class)).toList();
    }

}
