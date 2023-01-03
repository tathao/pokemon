package com.demo.pokemon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PokemonBase {

    protected int pokemonId;
    protected String pokemonName;
    protected String image;
}
