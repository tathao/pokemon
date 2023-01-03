package com.demo.pokemon.service;

import com.demo.pokemon.dto.LoginRequest;
import com.demo.pokemon.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> signup(SignUpRequest signUpRequest);

    ResponseEntity<?> signin(LoginRequest loginRequest);
}
