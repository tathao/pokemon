package com.demo.pokemon.repository;

import com.demo.pokemon.model.Moves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovesRepository extends JpaRepository<Moves, Integer> {

    @Query("SELECT m.moves FROM Moves m LEFT OUTER JOIN m.type t WHERE t.typeId IN ?1")
    List<String> movesOfAnPokemon(List<Integer> typesId);
}
