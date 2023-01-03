package com.demo.pokemon.controller;

import com.demo.pokemon.dto.*;
import com.demo.pokemon.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pokemon")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class PokemonController {

    private final PokemonService pokemonService;

    @Operation(summary = "Pokemons available for user", description = "Display a list of Pokemon, with each element showing the name, image, and number owned", tags = { "List pokemons" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema( schema = @Schema(implementation = ListPokemonResponse.class))))})
    @GetMapping("/list")
    public ResponseEntity<?> listPokemon() {
        String username = this.userNameFromUserDetail();
        return pokemonService.listAllPokemonAvailableForUser(username);
    }

    @Operation(summary = "Pokemons of an user", description = "Returns a list pokemons that user owned", tags = { "List pokemon of user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema( schema = @Schema(implementation = PokemonInMyBag.class))))})
    @GetMapping("/mybag")
    public ResponseEntity<?> listMyPokemon() {
        String username = this.userNameFromUserDetail();
        return pokemonService.listPokemonInMyBag(username);
    }

    @Operation(summary = "Information of pokemon", description = "Display detailed information about a Pokemon, including its name, image, type, weight,\n" +
            "      height, list of abilities, and list of moves", tags = { "Pokemon information" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PokemonInfo.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<?> pokemon(@PathVariable(value = "id") int pokemonId) {
        return pokemonService.pokemonInfo(pokemonId);
    }

    @Operation(summary = "Catch up a pokemon", description = "Function to catch Pokemon with a 50% success rate", tags = { "Catch Up a pokemon" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MessageResponse.class)))})
    @PatchMapping("/{id}")
    public ResponseEntity<?> catchUp(@PathVariable(value = "id") int pokemonId) {
        String username = this.userNameFromUserDetail();
        return pokemonService.catchUp(pokemonId, username);
    }

    @Operation(summary = "Release a pokemon", description = "Release a pokemon from myBag", tags = { "Release pokemon" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MessageResponse.class)))})
    @DeleteMapping("/{id}/release")
    public ResponseEntity<?> release(@PathVariable(value = "id") int pokemonId) {
        String username = this.userNameFromUserDetail();
        return pokemonService.releasePokemon(pokemonId, username);
    }

    @Operation(summary = "Rename a pokemon", description = "After catchup, you can rename the pokemon", tags = { "Rename a pokemon" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content =
                            { @Content(mediaType = "application/json", schema = @Schema(implementation = PokemonName.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }
            ) })
    @PatchMapping("/{id}/rename")
    public ResponseEntity<?> rename(@PathVariable(value = "id") int pokemonId, @RequestBody PokemonName newName) {
        String username = this.userNameFromUserDetail();
        return pokemonService.rename(pokemonId, username, newName);
    }

    private String userNameFromUserDetail() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }
}
