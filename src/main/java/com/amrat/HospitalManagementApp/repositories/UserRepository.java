package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
