package com.demo.pokemon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pokemon_type")
@Getter
@Setter
@NoArgsConstructor
public class PokemonType {

    @EmbeddedId
    private PokemonTypeKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id")
    @MapsId("pokemonId")
    private Pokemon pokemon;

    @JoinColumn(name = "type_id")
    @MapsId("typeId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Type type;
}
