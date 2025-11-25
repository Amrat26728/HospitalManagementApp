package com.amrat.HospitalManagementApp.dtos.test;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
