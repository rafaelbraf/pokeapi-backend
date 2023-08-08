package com.looqbox.backendchallenger.service

import com.looqbox.backendchallenger.model.Pokemon
import com.looqbox.backendchallenger.model.PokemonHighlight
import com.looqbox.backendchallenger.model.PokemonResponse
import com.looqbox.backendchallenger.repository.PokemonRepository
import com.looqbox.backendchallenger.utils.SortEnums
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate

@SpringBootTest
class PokemonServiceTests {

    private lateinit var pokemonService: PokemonService

    @Autowired
    private lateinit var pokemonRepository: PokemonRepository

    @Mock
    private lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        pokemonService = PokemonService(pokemonRepository)
    }

    @Test
    fun `testing if returns response JSON in correct format and alphabetical sort when get all pokemons`() {
        val expectedResponseJson = """
            {
                "result": [
                    "abomasnow",
                    "abomasnow-mega",
                    "abra"
                ]
            }
        """.trimIndent()

        Mockito.`when`(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/", String::class.java)).thenReturn(expectedResponseJson)

        val pokemonResponse = pokemonService.findAllPokemons(SortEnums.ALPHABETICAL.value) as PokemonResponse.PokemonResponseDefault

        Assertions.assertEquals(pokemonResponse.result[0], "abomasnow")
        Assertions.assertEquals(pokemonResponse.result[1], "abomasnow-mega")
        Assertions.assertEquals(pokemonResponse.result[2], "abra")
        Assertions.assertEquals(pokemonResponse.result.size, 1281)
    }

    @Test
    fun `testing if returns response JSON in correct format and descending sort when get all pokemons`() {
        val expectedResponseJson = """
            {
                "result": [
                    "zygarde-complete",
                    "zygarde-50-power-construct",
                    "zygarde-50"
                ]
            }
        """.trimIndent()

        Mockito.`when`(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/", String::class.java)).thenReturn(expectedResponseJson)

        val pokemonResponse = pokemonService.findAllPokemons(SortEnums.DESCENDING.value) as PokemonResponse.PokemonResponseDefault

        Assertions.assertEquals(pokemonResponse.result[0], "zygarde-complete")
        Assertions.assertEquals(pokemonResponse.result[1], "zygarde-50-power-construct")
        Assertions.assertEquals(pokemonResponse.result[2], "zygarde-50")
        Assertions.assertEquals(pokemonResponse.result.size, 1281)
    }

    @Test
    fun `testing if return response JSON in correct format, correct pokemons and sort alphabetical when have a name parameter`() {
        val expectedResponseJson = """
            {
                "result": [
                    "charcadet",
                    "charizard",
                    "charizard-gmax"
                ]
            }
        """.trimIndent()

        Mockito.`when`(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/", String::class.java)).thenReturn(expectedResponseJson)

        val pokemonResponse = pokemonService.findPokemonsByName("char", SortEnums.ALPHABETICAL.value) as PokemonResponse.PokemonResponseDefault

        Assertions.assertEquals(pokemonResponse.result[0], "charcadet")
        Assertions.assertEquals(pokemonResponse.result[1], "charizard")
        Assertions.assertEquals(pokemonResponse.result[2], "charizard-gmax")
        Assertions.assertEquals(pokemonResponse.result.size, 9)
    }

    @Test
    fun `testing if return response JSON in correct format, correct pokemons and sort descending when have a name parameter`() {
        val expectedResponseJson = """
            {
                "result": [
                    "chimchar",
                    "charmeleon",
                    "charmander"
                ]
            }
        """.trimIndent()

        Mockito.`when`(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/", String::class.java)).thenReturn(expectedResponseJson)

        val pokemonResponse = pokemonService.findPokemonsByName("char", SortEnums.DESCENDING.value) as PokemonResponse.PokemonResponseDefault

        Assertions.assertEquals(pokemonResponse.result[0], "chimchar")
        Assertions.assertEquals(pokemonResponse.result[1], "charmeleon")
        Assertions.assertEquals(pokemonResponse.result[2], "charmander")
        Assertions.assertEquals(pokemonResponse.result.size, 9)
    }

    @Test
    fun `testing get list of pokemons filtered`() {
        val pokemon1 = Pokemon("pikachu")
        val pokemon2 = Pokemon("charizard")
        val pokemon3 = Pokemon("blastoise")

        val listPokemons = listOf(pokemon1, pokemon2, pokemon3)

        val listPokemonsFiltered = pokemonService.getListPokemonsFiltered(listPokemons, "pikachu")

        Assertions.assertEquals(listOf(pokemon1), listPokemonsFiltered)
        Assertions.assertNotEquals(listPokemons, listPokemonsFiltered)
    }

    @Test
    fun `testing get list names of pokemons`() {
        val pokemon1 = Pokemon("pikachu")
        val pokemon2 = Pokemon("charizard")
        val pokemon3 = Pokemon("blastoise")

        val listPokemons = listOf(pokemon1, pokemon2, pokemon3)

        val listNamesPokemon = pokemonService.getListNamesPokemons(listPokemons)

        val listExpected = listOf("pikachu", "charizard", "blastoise")

        Assertions.assertEquals(listExpected, listNamesPokemon)
    }

    @Test
    fun `testing get list highlight pokemons`() {
        val namePokemon = "char"
        val listNamesPokemon: MutableList<String> = mutableListOf("charmander", "charmeleon", "charizard")

        val listHighlightPokemons: List<PokemonHighlight> = pokemonService.getListPokemonsHighlight(listNamesPokemon, namePokemon)

        val pokemonHighlight1 = PokemonHighlight("charmander", "<pre>char</pre>mander")
        val pokemonHighlight2 = PokemonHighlight("charmeleon", "<pre>char</pre>meleon")
        val pokemonHighlight3 = PokemonHighlight("charizard", "<pre>char</pre>izard")

        val listExpectedHighlightPokemons: List<PokemonHighlight> = listOf(pokemonHighlight1, pokemonHighlight2, pokemonHighlight3)

        Assertions.assertEquals(listExpectedHighlightPokemons, listHighlightPokemons)
    }

    @Test
    fun `testing if sort alphabetical A to Z correctly`() {
        val listNamesPokemon = mutableListOf("pikachu", "togepi", "eevee", "charizard")

        pokemonService.sortAlphabeticalAToZ(listNamesPokemon)

        val listNamesPokemonExpected = listOf("charizard", "eevee", "pikachu", "togepi")

        Assertions.assertEquals(listNamesPokemonExpected, listNamesPokemon)
    }

    @Test
    fun `testing if sort alphabetical Z to A correctly`() {
        val listNamesPokemon = mutableListOf("pikachu", "togepi", "eevee", "charizard")

        pokemonService.sortAlphabeticalZToA(listNamesPokemon)

        val listNamesPokemonExpected = listOf("togepi", "pikachu", "eevee", "charizard")

        Assertions.assertEquals(listNamesPokemonExpected, listNamesPokemon)
    }
}