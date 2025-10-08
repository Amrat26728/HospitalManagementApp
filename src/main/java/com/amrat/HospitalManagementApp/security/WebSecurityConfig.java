package com.amrat.HospitalManagementApp.security;

import com.amrat.HospitalManagementApp.entities.types.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(RoleType.ADMIN.name())
                        .requestMatchers("/patients/**").hasRole(RoleType.PATIENT.name())
                        .requestMatchers("/doctors/**").hasRole(RoleType.DOCTOR.name())
                        .anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
