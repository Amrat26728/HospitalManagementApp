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

    @ManyToOne
    private Department department;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private boolean isActive;

    public Doctor(User user, String name, String email, Set<String> qualifications){
        validateFields(user, name, email, qualifications);

        this.user = user;
        this.name = name;
        this.email = email;
        this.qualifications = new HashSet<>(qualifications);
        this.isActive = true;
    }

    public Doctor(User user, String name, String email, Set<String> qualifications, Department department){
        validateFields(user, name, email, qualifications);

        if (department == null){
            throw new IllegalArgumentException("Department is not selected.");
        }

        this.user = user;
        this.name = name;
        this.email = email;
        this.department = department;
        this.qualifications = new HashSet<>(qualifications);
        this.isActive = true;
    }

    public void changeDepartment(Department department){
        if (department == null){
            throw new IllegalArgumentException("Select a department.");
        }

        if (this.department != null){
            this.department.getDoctors().remove(this);
        }

        this.department = department;
        department.getDoctors().add(this);
    }

    public void inactive(){
        this.isActive = false;
    }

    public void validateFields(User user, String name, String email, Set<String> qualifications){
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
    }
}
