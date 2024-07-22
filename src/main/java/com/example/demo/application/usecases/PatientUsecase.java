package com.example.demo.application.usecases;

import com.example.demo.domain.dtos.PatientRequestDTO;
import com.example.demo.domain.dtos.PatientResponseDTO;
import com.example.demo.domain.dtos.PatientUpdateRequestDTO;
import com.example.demo.domain.entities.Allergy;
import com.example.demo.domain.entities.Patient;
import com.example.demo.domain.exeptions.NotFoundException;
import com.example.demo.domain.repositories.IPatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientUsecase implements IPatientUsecase{

    private final IPatientRepository patientRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PatientUsecase(IPatientRepository patientRepository, ObjectMapper objectMapper) {
        this.patientRepository = patientRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        Patient patient = mapToEntity(patientRequestDTO);
        this.patientRepository.save(patient);
        return mapToDTO(patient);
    }

    @Override
    public List<PatientResponseDTO> findAll() {
        return patientRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public PatientResponseDTO findById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No se encontró paciente con ID: " + id));
        return mapToDTO(patient);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        patientRepository.deleteById(id);
    }

    @Override
    public PatientResponseDTO updatePatient(PatientUpdateRequestDTO patientUpdateRequestDTO) {
        Patient existingPatient = patientRepository.findById(patientUpdateRequestDTO.getId())
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));

        // Actualizar los campos del paciente existente con los datos del DTO
        existingPatient.setName(patientUpdateRequestDTO.getName());
        existingPatient.setLastName(patientUpdateRequestDTO.getLastName());
        existingPatient.setEmail(patientUpdateRequestDTO.getEmail());
        existingPatient.setHealthInsuranceNumber(patientUpdateRequestDTO.getHealthInsuranceNumber());
        existingPatient.setBirthDate(patientUpdateRequestDTO.getBirthDate());

        List<Allergy> updatedAllergies = patientUpdateRequestDTO.getAllergies();
        existingPatient.setAllergies(updatedAllergies);  // Aquí asumiendo que allergies es una lista de alergias actualizada

        Patient updatedPatient = patientRepository.save(existingPatient);

        return mapToDTO(updatedPatient);
    }

    private PatientResponseDTO mapToDTO(Patient patient){
        return objectMapper.convertValue(patient, PatientResponseDTO.class);
    }

    private Patient mapToEntity(PatientRequestDTO patientRequestDTO){
        return objectMapper.convertValue(patientRequestDTO, Patient.class);
    }

    private Patient mapToEntity(PatientUpdateRequestDTO patientUpdateRequestDTO){
        return objectMapper.convertValue(patientUpdateRequestDTO, Patient.class);
    }
}
