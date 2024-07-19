package com.example.demo.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientResponseDTO {
    private Long id;
    private String name;
    private String email;
}
