package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
