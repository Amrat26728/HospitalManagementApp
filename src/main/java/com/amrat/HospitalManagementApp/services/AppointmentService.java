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

    public List<AppointmentDto> getAllAppointmentsOfPatient(Integer pageNumber, Integer pageSize){
        // check user in context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // check user exists
        Boolean userExists = userRepository.existsById(user.getId());
        // check patient exists
        Boolean patientExists = patientRepository.existsById(user.getId());
        // get appointments
        if (userExists && patientExists){
            Patient patient = patientRepository.findById(user.getId()).orElse(null);
            Page<Appointment> appointments= appointmentRepository.findByPatient(patient, PageRequest.of(pageNumber, pageSize));
            return appointments.stream().map((element) -> modelMapper.map(element, AppointmentDto.class)).toList();
        } else {
            throw new EntityNotFoundException("User does not exist.");
        }
    }
}
