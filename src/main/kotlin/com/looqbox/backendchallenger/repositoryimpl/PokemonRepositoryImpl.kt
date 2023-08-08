package com.looqbox.backendchallenger.repositoryimpl

import com.looqbox.backendchallenger.model.ApiResponse
import com.looqbox.backendchallenger.model.Pokemon
import com.looqbox.backendchallenger.repository.PokemonRepository
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class PokemonRepositoryImpl(private val restTemplate: RestTemplate) : PokemonRepository {

    override fun findAllPokemons(): List<Pokemon> {
        val apiUrl = "https://pokeapi.co/api/v2/pokemon?limit=100000&offset=0"
        val responseType = object : ParameterizedTypeReference<ApiResponse>(){}
        val apiResponseEntity: ResponseEntity<ApiResponse> = restTemplate.exchange(apiUrl, HttpMethod.GET, null, responseType)

        return apiResponseEntity.body?.results ?: emptyList()
    }

}