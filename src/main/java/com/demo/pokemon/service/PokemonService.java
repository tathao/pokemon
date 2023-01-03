package com.demo.pokemon.service;

import com.demo.pokemon.dto.PokemonName;
import org.springframework.http.ResponseEntity;

public interface PokemonService {
    ResponseEntity<?> listAllPokemonAvailableForUser(String username);

    ResponseEntity<?> pokemonInfo(int pokemonId);

    ResponseEntity<?> catchUp(int pokemonId, String userName);

    ResponseEntity<?> listPokemonInMyBag(String username);

    ResponseEntity<?> rename(int pokemonId, String username, PokemonName newName);

    ResponseEntity<?> releasePokemon(int pokemonId, String userName);
}
