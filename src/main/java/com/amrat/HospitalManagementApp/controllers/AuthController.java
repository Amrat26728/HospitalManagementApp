package com.amrat.HospitalManagementApp.controllers;

import com.amrat.HospitalManagementApp.dtos.auth.LoginRequestDto;
import com.amrat.HospitalManagementApp.dtos.auth.LoginResponseDto;
import com.amrat.HospitalManagementApp.dtos.auth.SignupRequestDto;
import com.amrat.HospitalManagementApp.dtos.auth.SignupResponseDto;
import com.amrat.HospitalManagementApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

}

