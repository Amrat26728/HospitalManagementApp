package com.amrat.HospitalManagementApp.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    private String medicine;

    private String dosage;      // e.g., "500 mg"
    private String frequency;   // e.g., "3 times/day"
    private String duration;    // e.g., "5 days"
    private String instructions;

}
