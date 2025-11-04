package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Appointment;
import com.amrat.HospitalManagementApp.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.patient = ?1")
    Page<Appointment> findByPatient(@Param("patient") Patient patient, Pageable pageable);
}
