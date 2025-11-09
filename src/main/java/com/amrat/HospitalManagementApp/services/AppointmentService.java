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
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    private final CurrentUserInfo currentUserInfo;

    // get current patient user appointments
    public List<ResponseAppointmentDto> getAppointmentsOfPatient(Integer pageNumber, Integer pageSize, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found."));

        int safePage = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;

        Page<Appointment> appointments = appointmentRepository.findByPatient(patient, PageRequest.of(safePage, pageSize));

        return appointments.stream().map(appointment -> modelMapper.map(appointment, ResponseAppointmentDto.class)).toList();
    }

    // get current logged-in doctor appointments
    public List<ResponseAppointmentDto> getAppointmentsOfDoctor(Integer pageNumber, Integer pageSize, Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor does not exist"));

        int safePage = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;

        Page<Appointment> appointments = appointmentRepository.findByDoctor(doctor, PageRequest.of(safePage, pageSize));

        return appointments.stream().map(appointment -> modelMapper.map(appointment, ResponseAppointmentDto.class)).toList();
    }

    // patient book appointment
    @Transactional
    public ResponseAppointmentDto bookAppointment(RequestAppointmentDto requestAppointmentDto){

        User user = currentUserInfo.currentUserInfo();

        Patient patient = patientRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Patient does not exist."));

        Doctor doctor = doctorRepository.findById(requestAppointmentDto.getDoctorId()).orElseThrow(() -> new IllegalArgumentException("Doctor does not exist"));

        Appointment appointment = new Appointment(doctor, patient, requestAppointmentDto.getReason(), requestAppointmentDto.getAppointmentDate(), requestAppointmentDto.getAppointmentTime());

        appointment = appointmentRepository.save(appointment);

        return modelMapper.map(appointment, ResponseAppointmentDto.class);
    }

    // patient cancel appointment
    @Transactional
    public ResponseAppointmentDto cancelAppointment(Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new IllegalArgumentException("Appointment Does not exist."));
        User currentUser = currentUserInfo.currentUserInfo();

        // check if appointment belongs to currently logged-in user or not
        if (!Objects.equals(currentUser.getId(), appointment.getPatient().getId())){
            throw new AuthorizationDeniedException("You are not authorized to cancel this appointment.");
        }

        appointment.cancel();
        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, ResponseAppointmentDto.class);
    }

    // doctor make appointment done
    @Transactional
    public ResponseAppointmentDto completeAppointment(Long appointmentId){
        User user = currentUserInfo.currentUserInfo();
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new IllegalArgumentException("Appointment does not exist"));
        if (!user.getId().equals(appointment.getDoctor().getId())){
            throw new IllegalArgumentException("Appointment does not belong to you.");
        }
        appointment.done();
        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, ResponseAppointmentDto.class);
    }
}
