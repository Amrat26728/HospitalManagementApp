package com.amrat.HospitalManagementApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String email;
    private String name;
    private String password;
}
