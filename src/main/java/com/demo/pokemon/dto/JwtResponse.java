package com.demo.pokemon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private static final String TYPE = "Bearer";
    private int id;
    private String userName;

    public JwtResponse(String accessToken, int userId, String userName) {
        this.token = accessToken;
        this.id = userId;
        this.userName = userName;
    }
}
