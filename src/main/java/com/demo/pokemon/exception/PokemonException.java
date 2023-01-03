package com.demo.pokemon.exception;

public class PokemonException extends RuntimeException {

    public PokemonException(String message) {
        super(message);
    }

    public PokemonException(String message, Throwable cause) {
        super(message, cause);
    }
}
