package com.demo.pokemon.repository;

import com.demo.pokemon.dto.TypeInfo;
import com.demo.pokemon.model.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonTypeRepository extends JpaRepository<PokemonType, Integer> {

    @Query("SELECT new com.demo.pokemon.dto.TypeInfo(t.typeId, t.typeName)  FROM PokemonType pt LEFT OUTER JOIN pt.type t LEFT OUTER JOIN pt.pokemon p " +
            "WHERE p.id = :pokemonId")
    List<TypeInfo> typesOfAnPokemon(int pokemonId);
}
