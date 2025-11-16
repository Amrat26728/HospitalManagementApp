package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
