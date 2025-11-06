package com.amrat.HospitalManagementApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private boolean canceled;

    @Column(nullable = false)
    private boolean done;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Appointment(Doctor doctor, Patient patient, String reason, LocalDateTime appointmentDate){
        if (doctor == null){
            throw new IllegalArgumentException("Doctor is required.");
        }

        if (patient == null){
            throw new IllegalArgumentException("Patient is required.");
        }

        if (reason == null || reason.isEmpty()){
            throw new IllegalArgumentException("Reason is required.");
        }

        if (appointmentDate == null || appointmentDate.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Date and Time should be correct.");
        }

        this.doctor = doctor;
        this.patient = patient;
        this.reason = reason;
        this.appointmentDate = appointmentDate;
        this.canceled = false;
        this.done = false;
    }

    public void cancel(){
        if (this.done){
            throw new IllegalArgumentException("Completed appointment can not be canceled.");
        }
        this.canceled = true;
    }

    public void done() {
        if (this.canceled){
            throw new IllegalArgumentException("Canceled appointment can not be marked complete.");
        }
        this.done = true;
    }
}
