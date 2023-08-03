@file:OptIn(ExperimentalMaterial3Api::class)

package com.jassycliq.pokedex.ui

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.lifecycle.ViewModel
import com.jassycliq.pokedex.domain.model.Colors
import com.jassycliq.pokedex.ui.MainViewModel.SideEffect
import com.jassycliq.pokedex.ui.MainViewModel.State
import org.koin.android.annotation.KoinViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@KoinViewModel
class MainViewModel(
    themeColors: Colors,
) : ViewModel(), ContainerHost<State, SideEffect> {

    fun onNavigateToDestination(destination: SideEffect, colors: Colors) = intent {
        postSideEffect(destination)
        reduce {
            state.copy(themeColors = colors)
        }
    }

    fun setQuery(newQuery: String) = intent {
        reduce {
            state.copy(query = newQuery)
        }
    }

    fun setScrollToTop(block: LazyGridState.() -> Unit) = intent {
        reduce { state.copy(scrollToTop = block) }
    }

    fun setLazyGridState(lazyGridState: LazyGridState) = intent {
        reduce { state.copy(lazyGridState = lazyGridState) }
    }

    fun setState(
        forceTopAppBar: Boolean? = null,
        showFab: Boolean? = null,
        showBackIcon: Boolean? = null,
        showSearchIcon: Boolean? = null,
    ): Unit = intent {
        reduce {
            var newState = state
            forceTopAppBar?.let { newState = newState.copy(forceTopAppBar = it) }
            showFab?.let { newState = newState.copy(showFab = it) }
            showBackIcon?.let { newState = newState.copy(showBackIcon = it) }
            showSearchIcon?.let { newState = newState.copy(showSearchIcon = it) }
            newState
        }
    }

    override val container: Container<State, SideEffect> = container(State.defaultState(themeColors))

    data class State(
        val forceTopAppBar: Boolean,
        val showFab: Boolean,
        val showBackIcon: Boolean,
        val showSearchIcon: Boolean,
        val selectedPokemonId: Int,
        val themeColors: Colors,
        val lazyGridState: LazyGridState = LazyGridState(),
        val scrollToTop: (LazyGridState.() -> Unit)? = null,
        val topAppBarState: TopAppBarState = TopAppBarState(-Float.MAX_VALUE, 0f, 0f),
        val canScrollBackwards: Boolean = lazyGridState.canScrollBackward,
        val query: String = "",
    ) {
        companion object {
            fun defaultState(themeColors: Colors) = State(
                forceTopAppBar = false,
                showFab = true,
                showBackIcon = false,
                showSearchIcon = true,
                selectedPokemonId = 0,
                themeColors = themeColors,
            )
        }
    }

    sealed class SideEffect {
        object NavigateToPokemonList : SideEffect()
        object NavigateToPokemonDetails : SideEffect()
    }
}