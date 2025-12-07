package com.amrat.HospitalManagementApp.services;

import com.amrat.HospitalManagementApp.dtos.test.AddTestDto;
import com.amrat.HospitalManagementApp.dtos.test.TestDto;
import com.amrat.HospitalManagementApp.entities.Test;
import com.amrat.HospitalManagementApp.repositories.TestRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final ModelMapper modelMapper;
    private final TestRepository testRepository;

    public List<TestDto> getTests(){
        List<Test> tests = testRepository.findAll();
        return tests.stream().map(test -> modelMapper.map(test, TestDto.class)).toList();
    }

    @Transactional
    public TestDto addTest(AddTestDto testDto){
        Test test = new Test(testDto.getName(), testDto.getDescription());
        test = testRepository.save(test);
        return modelMapper.map(test, TestDto.class);
    }

}
