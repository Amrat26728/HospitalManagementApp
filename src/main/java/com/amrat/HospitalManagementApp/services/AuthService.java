package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.auth.LoginRequestDto;
import com.amrat.HospitalManagementApp.dtos.auth.LoginResponseDto;
import com.amrat.HospitalManagementApp.dtos.auth.SignupRequestDto;
import com.amrat.HospitalManagementApp.dtos.auth.SignupResponseDto;
import com.amrat.HospitalManagementApp.dtos.password.ChangePasswordDto;
import com.amrat.HospitalManagementApp.entities.Patient;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.entities.types.RoleType;
import com.amrat.HospitalManagementApp.repositories.PatientRepository;
import com.amrat.HospitalManagementApp.repositories.UserRepository;
import com.amrat.HospitalManagementApp.util.AuthUtil;
import com.amrat.HospitalManagementApp.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
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
    private final CurrentUserInfo currentUserInfo;
    private final EmailService emailService;

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

//        emailService.sendVerificationEmail(signupRequestDto.getEmail());

        return modelMapper.map(user, SignupResponseDto.class);
    }

    public Map<String, String> changePassword(ChangePasswordDto passwordDto){
        User currentUser = currentUserInfo.currentUserInfo();

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Incorrect current password");
        }

        if (passwordDto.getNewPassword().length() < 6){
            throw new IllegalArgumentException("Password must have at least 6 characters.");
        }

        user.changePassword(passwordEncoder.encode(passwordDto.getNewPassword()));

        userRepository.save(user);

        return Map.of("message", "Password changed successfully.");
    }

}
