package com.amrat.HospitalManagementApp.dtos.changepassword;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
