package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.RequestAppointmentDto;
import com.amrat.HospitalManagementApp.dtos.ResponseAppointmentDto;
import com.amrat.HospitalManagementApp.entities.Appointment;
import com.amrat.HospitalManagementApp.entities.Doctor;
import com.amrat.HospitalManagementApp.entities.Patient;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.repositories.AppointmentRepository;
import com.amrat.HospitalManagementApp.repositories.DoctorRepository;
import com.amrat.HospitalManagementApp.repositories.PatientRepository;
import com.amrat.HospitalManagementApp.util.CurrentUserInfo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    private final CurrentUserInfo currentUserInfo;

    public List<ResponseAppointmentDto> getAllAppointmentsOfPatient(Long patientId, Integer pageNumber, Integer pageSize){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found."));

        int safePage = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;

        Page<Appointment> appointments = appointmentRepository.findByPatient(patient, PageRequest.of(safePage, pageSize));

        return appointments.stream().map(appointment -> modelMapper.map(appointment, ResponseAppointmentDto.class)).toList();
    }

    @Transactional
    public ResponseAppointmentDto bookAppointment(RequestAppointmentDto requestAppointmentDto){

        User user = currentUserInfo.currentUserInfo();

        Patient patient = patientRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Patient does not exist."));

        Doctor doctor = doctorRepository.findById(requestAppointmentDto.getDoctorId()).orElseThrow(() -> new IllegalArgumentException("Doctor does not exist"));
        Appointment appointment = Appointment.builder()
                .appointmentDate(requestAppointmentDto.getAppointmentTime())
                .reason(requestAppointmentDto.getReason())
                .doctor(doctor)
                .patient(patient)
                .build();

        appointment = appointmentRepository.save(appointment);

        return modelMapper.map(appointment, ResponseAppointmentDto.class);
    }
}
