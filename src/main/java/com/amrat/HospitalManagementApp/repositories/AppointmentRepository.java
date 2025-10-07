package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
