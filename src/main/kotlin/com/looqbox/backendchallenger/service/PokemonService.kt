package com.looqbox.backendchallenger.service

import com.looqbox.backendchallenger.model.*
import com.looqbox.backendchallenger.repository.PokemonRepository
import com.looqbox.backendchallenger.utils.SortEnums
import org.springframework.stereotype.Service

@Service
class PokemonService(private val pokemonRepository: PokemonRepository) {

    fun findAllPokemons(sort: String): PokemonResponse {
        val listPokemons = pokemonRepository.findAllPokemons()
        val listNamesPokemon = getListNamesPokemons(listPokemons)

        sortList(listNamesPokemon, sort)

        return PokemonResponse.PokemonResponseDefault(listNamesPokemon)
    }

    fun findPokemonsByName(namePokemon: String, sort: String): PokemonResponse {
        val listPokemons = pokemonRepository.findAllPokemons()
        val listPokemonsFiltered: List<Pokemon> = getListPokemonsFiltered(listPokemons, namePokemon)
        val listNamesPokemonsFiltered = getListNamesPokemons(listPokemonsFiltered)

        sortList(listNamesPokemonsFiltered, sort)

        return PokemonResponse.PokemonResponseDefault(listNamesPokemonsFiltered)
    }

    fun findPokemonsByNameWithHighlights(namePokemon: String, sort: String): PokemonResponse {
        val listPokemons = pokemonRepository.findAllPokemons()
        val listPokemonsFiltered: List<Pokemon> = getListPokemonsFiltered(listPokemons, namePokemon)
        val listNamesPokemonFiltered: MutableList<String> = getListNamesPokemons(listPokemonsFiltered)

        sortList(listNamesPokemonFiltered, sort)

        val listPokemonsHighlight = getListPokemonsHighlight(listNamesPokemonFiltered, namePokemon)

        return PokemonResponse.PokemonResponseHighlight(listPokemonsHighlight)
    }

    // Função para implementar a ordenação de forma alfabética de A a Z utilizando Insertion Sort
    fun sortAlphabeticalAToZ(listNamesPokemon: MutableList<String>) {
        /*
            O algoritmo vai pegar um item da lista e vai comparar com os itens a esquerda

            Se o item atual for menor que o item a esquerda
            desloca o item que está a esquerda para a direita utilizando j + 1
            e depois verifica com o próximo item anterior a esquerda,
            e assim por diante até encontrar um elemento que seja maior que o elemento atual

            Se o item atual não for menor que o item a esquerda
            significa que encontrou a posição correta do elemento
            e insere o elemento em sua posição utilizando j + 1
         */

        // Percorre a lista de nomes de Pokémons partindo do índice 1 (segundo elemento)
        for (i in 1 until listNamesPokemon.size) {
            // Armazena o elemento atual da lista
            val currentElement = listNamesPokemon[i]
            // Inicializa a variável j pegando o índice do primeiro elemento a esquerda da lista
            var j = i - 1

            /*
               Enquanto índice j for maior ou igual a 0
               && o item da lista na posição j for maior que o elemento atual
               Joga o item da lista na posição j para uma posição a frente com j + 1
               e decrementa j para comparar com os outros elementos a esquerda

               O loop while para quando j não for maior ou igual a 0
               ou quando o elemento da lista que está na posição j não for maior que o elemento atual
             */
            while (j >= 0 && listNamesPokemon[j] > currentElement) {
                listNamesPokemon[j + 1] = listNamesPokemon[j]
                j--
            }

            // Insere o elemento na posição correta
            listNamesPokemon[j + 1] = currentElement
        }
    }

    // Função para implementar a ordenação de forma alfabética de Z a A utilizando Insertion Sort
    fun sortAlphabeticalZToA(listNamesPokemon: MutableList<String>) {
        /*
            O algoritmo vai pegar um item da lista e vai comparar com os itens a esquerda

            Se o item atual for menor que o item a esquerda
            significa que encontrou a posição correta do elemento
            e insere o elemento em sua posição utilizando j + 1

            Se o item atual não for menor que o item a esquerda
            desloca o item que está a esquerda para a direita utilizando j + 1
            e depois verifica com o próximo item anterior a esquerda,
            e assim por diante até encontrar um elemento que seja menor que o elemento atual
         */

        // Percorre a lista de nomes de Pokémons partindo do índice 1 (segundo elemento)
        for (i in 1 until listNamesPokemon.size) {
            // Armazena o elemento atual da lista
            val currentElement = listNamesPokemon[i]
            // Inicializa a variável j pegando o índice do primeiro elemento a esquerda da lista
            var j = i - 1

            /*
               Enquanto índice j for maior ou igual a 0
               && o item da lista na posição j for menor que o elemento atual
               Joga o item da lista na posição j para uma posição a frente com j + 1
               e decrementa j para comparar com os outros elementos a esquerda

               O loop while para quando j não for menor ou igual a 0
               ou quando o elemento da lista que está na posição j não for menor que o elemento atual
             */
            while (j >= 0 && listNamesPokemon[j] < currentElement) {
                listNamesPokemon[j + 1] = listNamesPokemon[j]
                j--
            }

            // Insere o elemento na posição correta
            listNamesPokemon[j + 1] = currentElement
        }
    }

    fun sortList(listNamesPokemon: MutableList<String>, sort: String) {
        if (sort == SortEnums.ALPHABETICAL.value) {
            sortAlphabeticalAToZ(listNamesPokemon)
        } else {
            sortAlphabeticalZToA(listNamesPokemon)
        }
    }

    fun getListPokemonsFiltered(listPokemons: List<Pokemon>, namePokemon: String): List<Pokemon> {
        return listPokemons.filter {
            it.name.contains(namePokemon, true)
        }
    }

    fun getListNamesPokemons(listPokemons: List<Pokemon>): MutableList<String> {
        val listNamesPokemons = mutableListOf<String>()

        listPokemons.forEach {
            listNamesPokemons.add(it.name)
        }

        return listNamesPokemons
    }

    fun getListPokemonsHighlight(listNamesPokemon: MutableList<String>, namePokemon: String): List<PokemonHighlight> {
        val listPokemonsHighlight: MutableList<PokemonHighlight> = mutableListOf()

        listNamesPokemon.forEach {
            val index = it.indexOf(namePokemon)
            if (index != -1) {
                val highlight = StringBuilder()
                highlight.append(it.substring(0, index))
                highlight.append("<pre>${it.substring(index, index + namePokemon.length)}</pre>")
                highlight.append(it.substring(index + namePokemon.length))

                listPokemonsHighlight.add(PokemonHighlight(it, highlight.toString()))
            }
        }

        return listPokemonsHighlight
    }

}