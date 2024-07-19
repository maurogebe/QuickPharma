package com.example.demo.domain.service;

import com.example.demo.domain.dtos.PatientResponseDTO;
import com.example.demo.domain.entities.Patient;
import com.example.demo.domain.repositories.IPatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PatientService {

    private IPatientRepository patientRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public PatientService(IPatientRepository patientRepository, ObjectMapper objectMapper) {
        this.patientRepository = patientRepository;
        this.objectMapper = objectMapper;
    }

    public void createPatient(Patient patient){
        this.patientRepository.save(patient);
    }

    public void searchPatient(Long id) {
        patientRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("No se encontró paciente con ID: " + id + "."));
    }

    // consultar como agregar alergias a la lista
    @Transactional
    public void updatePatient(Long id, String newName, String newLastName, String newEmail) {
        searchPatient(id);
        patientRepository.updatePatient(id, newName, newLastName, newEmail);
    }

    public Patient printPatient(Long id) {
        searchPatient(id);
        return patientRepository.findById(id).get();
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

}
