package com.demo.pokemon.repository;

import com.demo.pokemon.dto.ListPokemonResponse;
import com.demo.pokemon.dto.PokemonBase;
import com.demo.pokemon.dto.PokemonInMyBag;
import com.demo.pokemon.dto.PokemonInfo;
import com.demo.pokemon.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    @Query("SELECT new com.demo.pokemon.dto.ListPokemonResponse(p.id, p.name, p.image, u) FROM Pokemon p " +
            "LEFT OUTER JOIN p.user u WHERE u IS NULL OR (u.id = :userId)")
    List<ListPokemonResponse> findAllPokemonAvailableAndUserOwner(int userId);

    @Query("SELECT new com.demo.pokemon.dto.PokemonInfo(p.id, p.name, p.image, p.height, p.weight) FROM Pokemon p " +
            "WHERE p.id = :id")
    PokemonInfo pokemonInfo(int id);

    @Query("SELECT new com.demo.pokemon.dto.PokemonInMyBag(p.id, p.name, p.image) FROM Pokemon p LEFT OUTER JOIN p.user u WHERE u.id = :userId")
    List<PokemonInMyBag> pokemonInMyBag(int userId);
}
