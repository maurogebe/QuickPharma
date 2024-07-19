package com.example.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.entities.Patient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPatientRepository extends JpaRepository<Patient, Long> {

    @Modifying
    @Query("UPDATE Patient e SET e.name = :newName, e.lastName = :newLastName, e.email = :newEmail WHERE e.id = :id")
    void updatePatient(
            @Param("id") Long id,
            @Param("newName") String newName,
            @Param("newLastName") String newLastName,
            @Param("newEmail") String newEmail
    );
}
