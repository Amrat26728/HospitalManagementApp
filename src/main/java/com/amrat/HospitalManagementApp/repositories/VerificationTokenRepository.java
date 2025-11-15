package com.amrat.HospitalManagementApp.repositories;

import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    void deleteByUser(User user);

}
