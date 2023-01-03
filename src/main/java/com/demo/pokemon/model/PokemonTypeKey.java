package com.demo.pokemon.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PokemonTypeKey implements Serializable {

    @Column(name = "pokemon_id")
    int pokemonId;
    @Column(name = "type_id")
    int typeId;
}
