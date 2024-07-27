package com.example.demo.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LoginRequestDTO {

    private String email;
    private String password;

}
