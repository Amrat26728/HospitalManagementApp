package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.Department;
import com.amrat.HospitalManagementApp.entities.Doctor;
import com.amrat.HospitalManagementApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByIsActive(boolean isActive);

    List<Doctor> findByDepartment(Department department);

    Doctor findByUser(User user);

}
