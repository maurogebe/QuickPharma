package com.example.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.entities.Patient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPatientRepository extends JpaRepository<Patient, Long> {

}
