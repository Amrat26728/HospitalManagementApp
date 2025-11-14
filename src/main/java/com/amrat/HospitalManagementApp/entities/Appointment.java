package com.amrat.HospitalManagementApp.entities;

import com.amrat.HospitalManagementApp.entities.types.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private boolean canceled;

    @Column(nullable = false)
    private boolean done;

    @Column(nullable = false)
    private AppointmentStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Appointment(Doctor doctor, Patient patient, String reason, LocalDate appointmentDate, LocalTime appointmentTime){
        if (doctor == null){
            throw new IllegalArgumentException("Doctor is required.");
        }

        if (patient == null){
            throw new IllegalArgumentException("Patient is required.");
        }

        if (reason == null || reason.isEmpty()){
            throw new IllegalArgumentException("Reason is required.");
        }

        if (appointmentDate == null || appointmentDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Date and Time should be correct.");
        }

        if (appointmentTime == null || appointmentTime.isBefore(LocalTime.now())){
            throw new IllegalArgumentException("Date and Time should be correct.");
        }

        this.doctor = doctor;
        this.patient = patient;
        this.reason = reason;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.canceled = false;
        this.done = false;
        this.status = AppointmentStatus.PENDING;
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

    public void changeStatus(AppointmentStatus status){
        this.status = status;
    }
}
