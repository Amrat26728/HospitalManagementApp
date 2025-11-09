package com.amrat.HospitalManagementApp.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String jwt;
    private Long userId;
}
