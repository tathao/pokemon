package com.demo.pokemon.service.impl;

import com.demo.pokemon.dto.JwtResponse;
import com.demo.pokemon.dto.LoginRequest;
import com.demo.pokemon.dto.MessageResponse;
import com.demo.pokemon.dto.SignUpRequest;
import com.demo.pokemon.model.User;
import com.demo.pokemon.repository.UserRepository;
import com.demo.pokemon.security.JwtUtils;
import com.demo.pokemon.security.UserDetailsImpl;
import com.demo.pokemon.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> signup(SignUpRequest signUpRequest) {
        if(userRepository.existsByName(signUpRequest.getName())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("User is already exist"));
        }

        //Create new user
        this.createUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User register successfully"));
    }

    @Override
    public ResponseEntity<?> signin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(), userDetails.getUsername()));
    }

    void createUser(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getName(), passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
    }
}
