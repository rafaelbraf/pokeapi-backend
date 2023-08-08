package com.looqbox.backendchallenger.controller

import com.looqbox.backendchallenger.model.*
import com.looqbox.backendchallenger.service.PokemonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemons")
class PokemonController(val pokemonService: PokemonService) {

    @GetMapping
    private fun findAllPokemons(
        @RequestParam(name = "name", required = false) name: String,
        @RequestParam(name = "sort", required = false, defaultValue = "alphabetical") sort: String
    ): ResponseEntity<PokemonResponse> {
        val pokemonsList = if (name.isNullOrEmpty()) {
            pokemonService.findAllPokemons(sort)
        } else {
            pokemonService.findPokemonsByName(name, sort)
        }

        return ResponseEntity.ok().body(pokemonsList)
    }

    @GetMapping("/highlight")
    private fun findPokemonsWithHighlight(
        @RequestParam(name = "name", required = false) name: String,
        @RequestParam(name = "sort", required = false, defaultValue = "alphabetical") sort: String
    ): ResponseEntity<PokemonResponse> {
        val listPokemons = if (name.isNullOrEmpty()) {
            pokemonService.findAllPokemons(sort)
        } else {
            pokemonService.findPokemonsByNameWithHighlights(name, sort)
        }

        return ResponseEntity.ok().body(listPokemons)
    }

}