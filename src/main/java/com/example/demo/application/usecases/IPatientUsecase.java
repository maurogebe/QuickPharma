package com.example.demo.application.usecases;

import com.example.demo.domain.dtos.PatientRequestDTO;
import com.example.demo.domain.dtos.PatientResponseDTO;
import com.example.demo.domain.dtos.PatientUpdateRequestDTO;

import java.util.List;

public interface IPatientUsecase {

    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);

    List<PatientResponseDTO> findAll();

    PatientResponseDTO findById(Long id);

    void deleteById(Long id);

    PatientResponseDTO updatePatient(PatientUpdateRequestDTO patientUpdateRequestDTO);

}
