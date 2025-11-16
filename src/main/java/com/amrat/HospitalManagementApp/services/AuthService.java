package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.auth.LoginRequestDto;
import com.amrat.HospitalManagementApp.dtos.auth.LoginResponseDto;
import com.amrat.HospitalManagementApp.dtos.auth.SignupRequestDto;
import com.amrat.HospitalManagementApp.dtos.auth.SignupResponseDto;
import com.amrat.HospitalManagementApp.dtos.password.ChangePasswordDto;
import com.amrat.HospitalManagementApp.entities.Patient;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.entities.VerificationToken;
import com.amrat.HospitalManagementApp.entities.types.RoleType;
import com.amrat.HospitalManagementApp.repositories.PatientRepository;
import com.amrat.HospitalManagementApp.repositories.UserRepository;
import com.amrat.HospitalManagementApp.repositories.VerificationTokenRepository;
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

import java.time.LocalDateTime;
import java.util.*;

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
    private final VerificationTokenRepository verificationTokenRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        if (!user.isVerified()){
            throw new IllegalArgumentException("Account is not verified.");
        }

        if (!user.isActive()){
            throw new IllegalArgumentException("Account is inactivated.");
        }

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    @Transactional
    public Map<String, String> signup(SignupRequestDto signupRequestDto) {
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

        // create verification token and send in email
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user, LocalDateTime.now().plusMinutes(30));
        verificationTokenRepository.save(verificationToken);

        // in future also send user id to user
        System.out.println("verification token: " + token);

//        emailService.sendVerificationEmail(signupRequestDto.getEmail(), token);

        return Map.of("message", "Verification link has been sent to your email.");
    }

    // verify user
    @Transactional
    public Map<String, String> verify(String token) {

        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(token);

        if (verificationToken == null) {
            throw new IllegalArgumentException("You have not created account yet. create your account first.");
        }

        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            // send email on user email and then throw exception
            throw new IllegalArgumentException("Verification token expired. Token is sent to your email.");
        }

        User user = verificationToken.getUser();

        if (user.isVerified()) {
            throw new IllegalStateException("User already verified");
        }

        user.setVerified();
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        return Map.of("message", "Account verified successfully. You can now log in.");
    }

    // resend user verification token
    public Map<String, String> resendVerificationToken(String email) {

        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.isVerified()) {
            throw new IllegalStateException("User already verified");
        }

        // Delete any old tokens for this user
        VerificationToken exists = verificationTokenRepository.findByUser(user);

        if (exists != null){
            verificationTokenRepository.delete(exists);
        }

        // Create new token
        String token = UUID.randomUUID().toString();

        VerificationToken newToken = new VerificationToken(token, user, LocalDateTime.now().plusMinutes(30));

        verificationTokenRepository.save(newToken);

//        emailService.sendVerificationEmail(email, token);

        return Map.of("message", "A verification link has been sent to your email.");
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
