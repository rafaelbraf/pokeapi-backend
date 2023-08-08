package com.looqbox.backendchallenger.model

data class ApiResponse(val results: List<Pokemon>)

sealed class PokemonResponse {
    data class PokemonResponseDefault(val result: List<String>) : PokemonResponse()
    data class PokemonResponseHighlight(val result: List<PokemonHighlight>) : PokemonResponse()
}