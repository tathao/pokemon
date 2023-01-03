package com.demo.pokemon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pokemon_id")
    private int id;

    @Column(name = "pokemon_name", nullable = false)
    private String name;

    @Column(name = "height", nullable = false)
    private int height;

    @Column(name = "weight", nullable = false)
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "type")
    private List<PokemonType> types;

    @OneToMany(mappedBy = "pokemon")
    private List<Abilities> abilities;

}
