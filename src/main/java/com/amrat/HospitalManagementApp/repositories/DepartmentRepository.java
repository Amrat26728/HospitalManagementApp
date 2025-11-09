package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByName(String name);

}
