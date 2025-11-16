package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
