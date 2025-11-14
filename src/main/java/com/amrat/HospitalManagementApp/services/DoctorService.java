package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.doctor.DoctorDepartmentDto;
import com.amrat.HospitalManagementApp.dtos.doctor.RequestDoctorDto;
import com.amrat.HospitalManagementApp.dtos.doctor.ResponseDoctorDto;
import com.amrat.HospitalManagementApp.entities.Department;
import com.amrat.HospitalManagementApp.entities.Doctor;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.entities.types.RoleType;
import com.amrat.HospitalManagementApp.repositories.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;

    // get doctors
    public List<ResponseDoctorDto> getDoctors(Boolean isActive){
        if (isActive == null){
            return doctorRepository.findAll().stream().map(doctor -> modelMapper.map(doctor, ResponseDoctorDto.class)).toList();
        }
        if (isActive){
            return doctorRepository.findByIsActive(true).stream().map(doctor -> modelMapper.map(doctor, ResponseDoctorDto.class)).toList();
        }
        return doctorRepository.findByIsActive(false).stream().map(doctor -> modelMapper.map(doctor, ResponseDoctorDto.class)).toList();
    }

    // get doctor profile
    public ResponseDoctorDto doctorProfile(){
        User user = currentUserInfo.currentUserInfo();

        Doctor doctor = doctorRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Doctor does not exist."));

        return modelMapper.map(doctor, ResponseDoctorDto.class);
    }

    // create doctor
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

        Doctor doctor;

        if (doctorDto.getDepartmentId() != null){
            Department department = departmentRepository.findById(doctorDto.getDepartmentId()).orElseThrow(() -> new IllegalArgumentException("Department does not exist."));
            doctor = new Doctor(user, doctorDto.getName(), doctorDto.getEmail(), doctorDto.getQualifications(), department);
        } else{
            doctor = new Doctor(user, doctorDto.getName(), doctorDto.getEmail(), doctorDto.getQualifications());
        }

        doctor = doctorRepository.save(doctor);

//        emailService.sendCredentials(doctorDto.getEmail(), doctorDto.getEmail(), "1234");

        return modelMapper.map(doctor, ResponseDoctorDto.class);
    }

    // change department of doctor
    @Transactional
    public DoctorDepartmentDto changeDepartment(Long doctorId, Long departmentId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor does not exist."));
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new IllegalArgumentException("Department does not exist."));

        doctor.changeDepartment(department);

        doctorRepository.save(doctor);

        return modelMapper.map(doctor, DoctorDepartmentDto.class);
    }

    // fetch doctors of a department
    public List<ResponseDoctorDto> getDoctorsOfDepartment(Long departmentId){
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new IllegalArgumentException("Department does not exist."));

        return doctorRepository.findByDepartment(department).stream().map(doctor -> modelMapper.map(doctor, ResponseDoctorDto.class)).toList();
    }

}
