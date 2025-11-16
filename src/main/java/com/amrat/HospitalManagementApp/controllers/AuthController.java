package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.auth.LoginRequestDto;
import com.amrat.HospitalManagementApp.dtos.auth.LoginResponseDto;
import com.amrat.HospitalManagementApp.dtos.auth.SignupRequestDto;
import com.amrat.HospitalManagementApp.dtos.auth.SignupResponseDto;
import com.amrat.HospitalManagementApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @PutMapping("/verify/{token}")
    public ResponseEntity<Map<String, String>> verify(@PathVariable String token){
        return ResponseEntity.ok(authService.verify(token));
    }

    @PostMapping("/resend-verification-link/{email}")
    public ResponseEntity<Map<String, String>> resendVerificationToken(@PathVariable String email){
        return ResponseEntity.ok(authService.resendVerificationToken(email));
    }

}

