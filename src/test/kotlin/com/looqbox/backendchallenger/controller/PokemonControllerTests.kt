package com.looqbox.backendchallenger.controller

import com.looqbox.backendchallenger.service.PokemonService
import com.looqbox.backendchallenger.utils.SortEnums
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(PokemonController::class)
class PokemonControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var pokemonService: PokemonService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `testing if return status OK when get all Pokémons`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `testing if return status OK when have a name parameter to get Pokémons By Name`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons?name=pikachu"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `testing if return status OK when have a sort parameter to get Pokémons By Name`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons?sort=${SortEnums.DESCENDING.value}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `testing if return status OK when request with highlight`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/highlight"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `testing if return status OK when request with highlight contain name param`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/highlight?name=togepi"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `testing if return status OK when request with highlight contain name and sort params`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/highlight?name=togepi&sort=descending"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `testing if call function findAllPokemons from service when name is null or empty`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(pokemonService, Mockito.times(1)).findAllPokemons(SortEnums.ALPHABETICAL.value)
        Mockito.verify(pokemonService, Mockito.times(0)).findPokemonsByName("", SortEnums.ALPHABETICAL.value)
    }

    @Test
    fun `testing if call function findPokemonsByName from service when name is not empty`() {
        val namePokemon = "pikachu"

        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons?name=${namePokemon}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(pokemonService, Mockito.times(0)).findAllPokemons(SortEnums.ALPHABETICAL.value)
        Mockito.verify(pokemonService, Mockito.times(1)).findPokemonsByName(namePokemon, SortEnums.ALPHABETICAL.value)
    }

}