package com.looqbox.backendchallenger.repository

import com.looqbox.backendchallenger.model.Pokemon

interface PokemonRepository {

    fun findAllPokemons(): List<Pokemon>

}