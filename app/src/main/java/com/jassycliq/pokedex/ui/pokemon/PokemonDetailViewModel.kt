package com.jassycliq.pokedex.ui.pokemon

import android.os.Parcelable
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jassycliq.pokedex.domain.model.Colors
import com.jassycliq.pokedex.domain.repository.PokemonRepo
import com.jassycliq.pokedex.extension.toDisplayHeight
import com.jassycliq.pokedex.extension.toDisplayName
import com.jassycliq.pokedex.extension.toDisplayNumber
import com.jassycliq.pokedex.extension.toDisplayWeight
import com.jassycliq.pokedex.ui.pokemon.PokemonDetailViewModel.SideEffect
import com.jassycliq.pokedex.ui.pokemon.PokemonDetailViewModel.State
import kotlinx.parcelize.Parcelize
import org.koin.android.annotation.KoinViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

const val POKEMON_DETAIL_POKEMON_ID = "com.jassycliq.pokedex-daadd64f-3844-4abe-87c8-af8a317b0126"
const val POKEMON_DETAIL_CARD_COLORS = "com.jassycliq.pokedex-1290cc7e-a0f9-421a-89e9-7c64c9c841bc"

@KoinViewModel
class PokemonDetailViewModel constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepo: PokemonRepo,
) : ViewModel(), ContainerHost<State, SideEffect> {
    private val pokemonId: Int = checkNotNull(savedStateHandle[POKEMON_DETAIL_POKEMON_ID])
    private val cardColors: Colors = checkNotNull(savedStateHandle[POKEMON_DETAIL_CARD_COLORS])

    override val container: Container<State, SideEffect> =
        container(State(id = pokemonId.toString(), cardColors = cardColors))

    init {
        fetchPokemonDetails()
    }

    fun fetchPokemonDetails() = intent {
        pokemonRepo.getPokemonById(pokemonId)?.apply {
            reduce {
                state.copy(
                    name = name.toDisplayName(),
                    id = id.toDisplayNumber(),
                    types = types.map { it.type.name.capitalize(Locale.current) },
                    height = height.toDisplayHeight(),
                    weight = weight.toDisplayWeight(),
                    avatar = sprites.other.officialArtwork.frontDefault,
                )
            }
        }
        pokemonRepo.getSpeciesById(pokemonId)?.apply {
            reduce {
                state.copy(
                    species = flavorTextEntries
                        .filter { it.language.name == Locale.current.language }
                        .distinctBy { it.flavorText }
                        .take(3)
                        .joinToString { it.flavorText }
                        .replace("\n", " ")
                        .replace(".,", "."),
                    genus = genera
                        .first { it.language.name == Locale.current.language }
                        .genus,
                )
            }
        }
    }

    //region State
    @Parcelize
    data class State(
        val name: String? = null,
        val id: String,
        val types: List<String>? = null,
        val genus: String? = null,
        val avatar: String? = null,
        val height: String? = null,
        val weight: String? = null,
        val species: String? = null,
        val cardColors: Colors,
    ) : Parcelable

    sealed class SideEffect {
        object NavigateBack : SideEffect()
    }
    //endregion
}