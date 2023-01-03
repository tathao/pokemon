package com.demo.pokemon.dto;

import com.demo.pokemon.model.User;
import lombok.Getter;

@Getter
public class ListPokemonResponse extends PokemonBase {

    private Integer userId;

    public ListPokemonResponse(int id, String name, String img, User user) {
        this.pokemonId = id;
        this.pokemonName = name;
        this.image = img;
        if (user != null)
            this.userId = user.getId();
    }
}
