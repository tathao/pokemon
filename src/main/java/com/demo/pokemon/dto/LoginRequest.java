package com.demo.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String password;
}
