package com.demo.pokemon.service.impl;

import com.demo.pokemon.dto.*;
import com.demo.pokemon.exception.PokemonException;
import com.demo.pokemon.model.Pokemon;
import com.demo.pokemon.model.User;
import com.demo.pokemon.repository.*;
import com.demo.pokemon.service.PokemonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PokemonServiceImpl implements PokemonService {

    private final UserRepository userRepository;

    private final PokemonRepository pokemonRepository;
    private final PokemonTypeRepository pokemonTypeRepository;
    private final AbilitiesRepository abilitiesRepository;
    private final MovesRepository movesRepository;

    @Override
    public ResponseEntity<?> listAllPokemonAvailableForUser(String username) {
        Optional<User> optionalUser = this.findUserByName(username);
        int userId = optionalUser.get().getId();
        List<ListPokemonResponse> pokemonResponses = pokemonRepository.findAllPokemonAvailableAndUserOwner(userId);
        log.info("There are {} pokemons can saw by user with id: {}", pokemonResponses.size(), userId);
        return ResponseEntity.ok(pokemonResponses);
    }

    @Override
    public ResponseEntity<?> pokemonInfo(int pokemonId) {

        PokemonInfo pokemonInfo = pokemonRepository.pokemonInfo(pokemonId);
        if (pokemonInfo == null)
            throw new PokemonException("Pokemon doesn't exist");
        List<TypeInfo> types = pokemonTypeRepository.typesOfAnPokemon(pokemonId);
        if (!types.isEmpty()) {
            pokemonInfo.getTypes().addAll(types.parallelStream().map(TypeInfo::getTypeName).collect(Collectors.toList()));
            List<Integer> typeIds = types.parallelStream().map(TypeInfo::getTypeId).collect(Collectors.toList());
            List<String> moves = movesRepository.movesOfAnPokemon(typeIds);
            if (!moves.isEmpty()) {
                pokemonInfo.getMoves().addAll(moves);
            }
        }
        List<AbilitiesInfo> abilitiesInfos = abilitiesRepository.abilitiesOfAnPokemon(pokemonId);
        if (!abilitiesInfos.isEmpty()) {
            pokemonInfo.getAbilities().addAll(abilitiesInfos);
        }
        log.info("There are some information of pokemon with id: {}", pokemonId);
        return ResponseEntity.ok(pokemonInfo);
    }

    @Override
    public ResponseEntity<?> catchUp(int pokemonId, String username) {
        Random r = new Random();
        float chance = r.nextFloat();
        if (chance >= 0.50f) {
            Optional<User> optionalUser = this.findUserByName(username);
            User user = optionalUser.get();
            Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemonId);
            if (optionalPokemon.isEmpty()) {
                throw new PokemonException("Pokemon doesn't exist");
            }
            Pokemon pokemon = optionalPokemon.get();
            pokemon.setUser(user);
            pokemonRepository.save(pokemon);
            return ResponseEntity.ok(new MessageResponse("Catch Up the pokemon with id:" + pokemonId));
        }
        return ResponseEntity.ok(new MessageResponse("You are not lucky. Try to next time!"));
    }

    @Override
    public ResponseEntity<?> listPokemonInMyBag(String username) {
        Optional<User> optionalUser = this.findUserByName(username);
        int userId = optionalUser.get().getId();
        List<PokemonInMyBag> pokemonInMyBag = pokemonRepository.pokemonInMyBag(userId);
        return ResponseEntity.ok(pokemonInMyBag);
    }

    @Override
    public ResponseEntity<?> rename(int pokemonId, String username, PokemonName newName) {
        Optional<User> optionalUser = this.findUserByName(username);
        User user = optionalUser.get();
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemonId);
        if (optionalPokemon.isEmpty()) {
            throw new PokemonException("Pokemon doesn't exist");
        }
        Pokemon pokemon = optionalPokemon.get();
        User owner = pokemon.getUser();
        if (user.getId() != owner.getId())
            throw new PokemonException("You cannot rename this pokemon");
        pokemon.setName(newName.getName());
        pokemonRepository.save(pokemon);
        return ResponseEntity.ok(new MessageResponse("Pokemon with id" + pokemonId + " has renamed to " + newName.getName()));
    }

    @Override
    public ResponseEntity<?> releasePokemon(int pokemonId, String username) {
        Optional<User> optionalUser = this.findUserByName(username);
        User user = optionalUser.get();
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemonId);
        if (optionalPokemon.isEmpty()) {
            throw new PokemonException("Pokemon doesn't exist");
        }
        Pokemon pokemon = optionalPokemon.get();
        User owner = pokemon.getUser();
        if (user.getId() != owner.getId())
            throw new PokemonException("You cannot release this pokemon");
        pokemon.setUser(null);
        pokemonRepository.save(pokemon);
        return ResponseEntity.ok(new MessageResponse("Pokemon with id" + pokemonId + " has been released !"));
    }

    private Optional<User> findUserByName(String username) {
        Optional<User> optionalUser = userRepository.findByName(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        return optionalUser;
    }
}
