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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Patient {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // maps user id with patient id
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    public Patient(User user, String name, String email){
        if (user == null){
            throw new IllegalArgumentException("User is required");
        }

        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name is required");
        }

        if (email == null || email.isEmpty()){
            throw new IllegalArgumentException("Email is required");
        }

        this.user = user;
        this.name = name;
        this.email = email;
    }
}
