package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
