package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByIsActive(boolean isActive);

}
