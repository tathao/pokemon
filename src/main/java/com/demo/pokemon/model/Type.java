package com.demo.pokemon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type")
@Getter
@Setter
@NoArgsConstructor
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int typeId;

    @Column(name = "type_name", length = 100, nullable = false)
    private String typeName;

    @OneToMany(mappedBy = "type")
    private List<PokemonType> types;

    @OneToMany(mappedBy = "type")
    private List<Moves> moves;
}
