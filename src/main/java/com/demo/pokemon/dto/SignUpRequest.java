package com.demo.pokemon.dto;

import lombok.Getter;

@Getter
public class SignUpRequest {

    private String name;
    private String password;

    public SignUpRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
