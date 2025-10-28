package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = "SELECT * FROM doctor", nativeQuery = true)
    List<Doctor> allDoctors();

}
