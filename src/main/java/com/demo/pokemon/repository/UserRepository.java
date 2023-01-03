package com.demo.pokemon.repository;

import com.demo.pokemon.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String userName);
    Boolean existsByName(String userName);

}
