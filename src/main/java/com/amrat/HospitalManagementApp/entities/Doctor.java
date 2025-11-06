package com.amrat.HospitalManagementApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Doctor {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private Set<String> qualifications;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Doctor(User user, String name, String email, Set<String> qualifications){
        if (user == null){
            throw new IllegalArgumentException("User is required");
        }

        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name is required");
        }

        if (email == null || email.isEmpty()){
            throw new IllegalArgumentException("Email is required");
        }

        if (qualifications == null || qualifications.isEmpty()){
            throw new IllegalArgumentException("Qualifications are required");
        }

        this.user = user;
        this.name = name;
        this.email = email;
        this.qualifications = new HashSet<>(qualifications);
    }
}
