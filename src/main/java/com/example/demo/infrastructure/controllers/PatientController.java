package com.example.demo.infrastructure.controllers;

import com.example.demo.domain.dtos.PatientResponseDTO;
import com.example.demo.domain.entities.Patient;
import com.example.demo.domain.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<String> createPatient(@RequestBody Patient patient){
        patientService.createPatient(patient);
        return ResponseEntity.ok("Registro de paciente exitoso.");
    }

    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable("id") Long id){
        return patientService.printPatient(id);
    }

    @PutMapping
    public ResponseEntity<String> updatePatient(@PathVariable("id") Long id, @RequestParam String newName, @RequestParam String newLastName, @RequestParam String newEmail){
        patientService.updatePatient(id, newName, newLastName, newEmail);
        return ResponseEntity.ok("El paciente con ID: " + id + " ha sido actualizado.");
    }
}
