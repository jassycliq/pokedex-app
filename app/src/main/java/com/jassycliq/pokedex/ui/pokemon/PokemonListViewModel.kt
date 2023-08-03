package com.jassycliq.pokedex.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jassycliq.pokedex.domain.database.entity.toNamedApiResource
import com.jassycliq.pokedex.domain.model.Colors
import com.jassycliq.pokedex.domain.model.NamedApiResource
import com.jassycliq.pokedex.domain.model.pokemon.Pokemon
import com.jassycliq.pokedex.domain.repository.PokemonRepo
import com.jassycliq.pokedex.extension.toPokemonId
import com.jassycliq.pokedex.ui.pokemon.PokemonListViewModel.SideEffect
import com.jassycliq.pokedex.ui.pokemon.PokemonListViewModel.SideEffect.NavigateToPokemonDetail
import com.jassycliq.pokedex.ui.pokemon.PokemonListViewModel.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@KoinViewModel
class PokemonListViewModel constructor(
    private val pokemonRepo: PokemonRepo,
) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container: Container<State, SideEffect> =
        container(State())

    init {
        intent {
            reduce {
                state.copy(pokemonPagingFlow = getPokemonPagingFlow())
            }
        }
    }

    private fun getPokemonPagingFlow(): Flow<PagingData<NamedApiResource>> {
        return pokemonRepo.getAllPokemon().flow
            .map { pagingData ->
                pagingData.map { it.toNamedApiResource() }
            }
            .cachedIn(viewModelScope)
    }

    suspend fun fetchPokemonDetails(url: String): Pokemon? {
        return pokemonRepo.getPokemonById(url.toPokemonId())
    }

    fun navigateToPokemonDetails(id: Int, cardColors: Colors): Unit = intent {
        postSideEffect(NavigateToPokemonDetail(pokemonId = id, cardColors = cardColors))
    }

    data class State(
        val pokemonPagingFlow: Flow<PagingData<NamedApiResource>> = emptyFlow(),
    )

    sealed class SideEffect {
        data class NavigateToPokemonDetail(
            val pokemonId: Int,
            val cardColors: Colors,
        ) : SideEffect()
    }
}
