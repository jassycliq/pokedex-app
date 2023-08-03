package com.jassycliq.pokedex.domain.model.pokemon

import androidx.annotation.Keep
import com.jassycliq.pokedex.domain.model.NamedApiResource
import com.jassycliq.pokedex.domain.model.emptyNamedApiResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Species(
    @Json(name = "base_happiness")
    val baseHappiness: Int = 0,
    @Json(name = "capture_rate")
    val captureRate: Int = 0,
    @Json(name = "color")
    val color: NamedApiResource = emptyNamedApiResource(),
    @Json(name = "egg_groups")
    val eggGroups: List<NamedApiResource> = listOf(),
    @Json(name = "evolution_chain")
    val evolutionChain: EvolutionChain = EvolutionChain(),
    @Json(name = "evolves_from_species")
    val evolvesFromSpecies: EvolvesFromSpecies? = EvolvesFromSpecies(),
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry> = listOf(),
    @Json(name = "form_descriptions")
    val formDescriptions: List<FormDescription> = listOf(),
    @Json(name = "forms_switchable")
    val formsSwitchable: Boolean = false,
    @Json(name = "gender_rate")
    val genderRate: Int = 0,
    @Json(name = "genera")
    val genera: List<Genera> = listOf(),
    @Json(name = "generation")
    val generation: NamedApiResource? = emptyNamedApiResource(),
    @Json(name = "growth_rate")
    val growthRate: NamedApiResource? = emptyNamedApiResource(),
    @Json(name = "habitat")
    val habitat: NamedApiResource? = emptyNamedApiResource(),
    @Json(name = "has_gender_differences")
    val hasGenderDifferences: Boolean = false,
    @Json(name = "hatch_counter")
    val hatchCounter: Int = 0,
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "is_baby")
    val isBaby: Boolean = false,
    @Json(name = "is_legendary")
    val isLegendary: Boolean = false,
    @Json(name = "is_mythical")
    val isMythical: Boolean = false,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "names")
    val names: List<Name> = listOf(),
    @Json(name = "order")
    val order: Int = 0,
    @Json(name = "pokedex_numbers")
    val pokedexNumbers: List<PokedexNumber> = listOf(),
    @Json(name = "shape")
    val shape: NamedApiResource = emptyNamedApiResource(),
    @Json(name = "varieties")
    val varieties: List<Variety> = listOf(),
) {

    @Keep
    @JsonClass(generateAdapter = true)
    data class EvolutionChain(
        @Json(name = "url")
        val url: String = "",
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class EvolvesFromSpecies(
        @Json(name = "name")
        val name: String = "",
        @Json(name = "url")
        val url: String = "",
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class FlavorTextEntry(
        @Json(name = "flavor_text")
        val flavorText: String = "",
        @Json(name = "language")
        val language: NamedApiResource = emptyNamedApiResource(),
        @Json(name = "version")
        val version: NamedApiResource = emptyNamedApiResource(),
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class FormDescription(
        @Json(name = "description")
        val description: String = "",
        @Json(name = "language")
        val language: NamedApiResource = emptyNamedApiResource(),
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Genera(
        @Json(name = "genus")
        val genus: String = "",
        @Json(name = "language")
        val language: NamedApiResource = emptyNamedApiResource(),
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Name(
        @Json(name = "language")
        val language: NamedApiResource = emptyNamedApiResource(),
        @Json(name = "name")
        val name: String = "",
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class PokedexNumber(
        @Json(name = "entry_number")
        val entryNumber: Int = 0,
        @Json(name = "pokedex")
        val pokedex: NamedApiResource = emptyNamedApiResource(),
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Variety(
        @Json(name = "is_default")
        val isDefault: Boolean = false,
        @Json(name = "pokemon")
        val pokemon: NamedApiResource = emptyNamedApiResource(),
    )
}
