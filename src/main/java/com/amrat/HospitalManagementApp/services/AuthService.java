package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.LoginRequestDto;
import com.amrat.HospitalManagementApp.dtos.LoginResponseDto;
import com.amrat.HospitalManagementApp.dtos.SignupRequestDto;
import com.amrat.HospitalManagementApp.dtos.SignupResponseDto;
import com.amrat.HospitalManagementApp.entities.Patient;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.entities.types.RoleType;
import com.amrat.HospitalManagementApp.repositories.PatientRepository;
import com.amrat.HospitalManagementApp.repositories.UserRepository;
import com.amrat.HospitalManagementApp.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = userRepository.findByUsername(signupRequestDto.getEmail()).orElse(null);

        if (user != null){
            throw new IllegalArgumentException("User already exists.");
        }

        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.PATIENT);

        if (signupRequestDto.getPassword().isEmpty()){
            throw new IllegalArgumentException("Password is required.");
        }

        user = new User(signupRequestDto.getEmail(), passwordEncoder.encode(signupRequestDto.getPassword()), roles);
        user = userRepository.save(user);

        Patient patient = new Patient(user, signupRequestDto.getName(), signupRequestDto.getEmail());

        patientRepository.save(patient);

        return modelMapper.map(user, SignupResponseDto.class);
    }

}
