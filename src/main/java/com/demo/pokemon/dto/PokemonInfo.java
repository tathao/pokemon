package com.demo.pokemon.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PokemonInfo extends PokemonBase {

    private List<String> types = new ArrayList<>();
    private List<AbilitiesInfo> abilities = new ArrayList<>();
    private List<String> moves = new ArrayList<>();
    private int height = 0;
    private int weight = 0;

    public PokemonInfo(int id, String name, String image, int height, int weight) {
        this.pokemonId = id;
        this.pokemonName = name;
        this.image = image;
        this.height = height;
        this.weight = weight;
    }
}
