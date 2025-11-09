package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.PatientDto;
import com.amrat.HospitalManagementApp.entities.Patient;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.repositories.PatientRepository;
import com.amrat.HospitalManagementApp.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final CurrentUserInfo currentUserInfo;
    private final ModelMapper modelMapper;

    public PatientDto profile(){
        User user = currentUserInfo.currentUserInfo();
        Patient patient = patientRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Patient does not exist."));

        return modelMapper.map(patient, PatientDto.class);
    }

    public List<PatientDto> getAllPatients(Integer pageNumber, Integer pageSize){
        int page = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;
        int size = pageSize < 0 ? 0 : 5;
        Page<Patient> patients = patientRepository.findAll(PageRequest.of(page, size));

        return patients.stream().map((patient) -> modelMapper.map(patient, PatientDto.class)).toList();
    }

}
