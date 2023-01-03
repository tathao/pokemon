package com.demo.pokemon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "user_password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Pokemon> pokemons;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
