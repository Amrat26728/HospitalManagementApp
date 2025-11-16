package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
