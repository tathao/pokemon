package com.demo.pokemon.repository;

import com.demo.pokemon.dto.AbilitiesInfo;
import com.demo.pokemon.model.Abilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbilitiesRepository extends JpaRepository<Abilities, Integer> {

    @Query("SELECT new com.demo.pokemon.dto.AbilitiesInfo(a.name, a.description) FROM Abilities a " +
            "LEFT OUTER JOIN a.pokemon p WHERE p.id = :pokemonId")
    List<AbilitiesInfo> abilitiesOfAnPokemon(int pokemonId);
}
