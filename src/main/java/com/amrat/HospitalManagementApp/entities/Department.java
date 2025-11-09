package com.amrat.HospitalManagementApp.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors;

    @Column(nullable = false)
    private String name;

    private String description;

    public Department(String name, String description){
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Department name is required.");
        }

        this.name = name;
        this.description = description;
    }

    public void changeDescription(String newDescription){
        this.description = newDescription;
    }

    // METHOD: remove doctor from department

}
