package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
