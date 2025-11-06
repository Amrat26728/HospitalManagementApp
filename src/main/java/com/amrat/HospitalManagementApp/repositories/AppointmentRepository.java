package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Appointment;
import com.amrat.HospitalManagementApp.entities.Doctor;
import com.amrat.HospitalManagementApp.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByPatient(Patient patient, Pageable pageable);

    Page<Appointment> findByDoctor(Doctor doctor, Pageable pageable);
}
