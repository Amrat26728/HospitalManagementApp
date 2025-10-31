package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.AppointmentDto;
import com.amrat.HospitalManagementApp.entities.Appointment;
import com.amrat.HospitalManagementApp.entities.Patient;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.repositories.AppointmentRepository;
import com.amrat.HospitalManagementApp.repositories.PatientRepository;
import com.amrat.HospitalManagementApp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    private final ModelMapper modelMapper;

    public List<AppointmentDto> getAllAppointmentsOfPatient(Long patientId, Integer pageNumber, Integer pageSize){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found."));

        int safePage = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;

        Page<Appointment> appointments = appointmentRepository.findByPatient(patient, PageRequest.of(safePage, pageSize));

        return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentDto.class)).toList();
    }
}
