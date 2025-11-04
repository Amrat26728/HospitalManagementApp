package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.PatientDto;
import com.amrat.HospitalManagementApp.entities.Patient;
import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.repositories.PatientRepository;
import com.amrat.HospitalManagementApp.util.CurrentUserInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

}
