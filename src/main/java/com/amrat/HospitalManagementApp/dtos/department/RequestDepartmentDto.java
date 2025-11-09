package com.amrat.HospitalManagementApp.dtos.department;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDepartmentDto {
    private String name;
    private String description;
}