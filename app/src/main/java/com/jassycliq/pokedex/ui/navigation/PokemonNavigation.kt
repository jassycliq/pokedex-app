package com.jassycliq.pokedex.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jassycliq.pokedex.domain.model.Colors
import com.jassycliq.pokedex.domain.model.Navigation
import com.jassycliq.pokedex.extension.navigateTo
import com.jassycliq.pokedex.extension.navigateWithArgs
import com.jassycliq.pokedex.extension.parcelable
import com.jassycliq.pokedex.extension.rememberLazyGridState
import com.jassycliq.pokedex.ui.MainViewModel.SideEffect
import com.jassycliq.pokedex.ui.MainViewModel.SideEffect.NavigateToPokemonDetails
import com.jassycliq.pokedex.ui.MainViewModel.SideEffect.NavigateToPokemonList
import com.jassycliq.pokedex.ui.component.getDefaultThemeColors
import com.jassycliq.pokedex.ui.navigation.PokemonNavigation.PokemonDetailsScreen
import com.jassycliq.pokedex.ui.navigation.PokemonNavigation.PokemonListScreen
import com.jassycliq.pokedex.ui.pokemon.POKEMON_DETAIL_CARD_COLORS
import com.jassycliq.pokedex.ui.pokemon.POKEMON_DETAIL_POKEMON_ID
import com.jassycliq.pokedex.ui.pokemon.PokemonDetailScreen
import com.jassycliq.pokedex.ui.pokemon.PokemonDetailViewModel
import com.jassycliq.pokedex.ui.pokemon.PokemonDetailViewModel.SideEffect.NavigateBack
import com.jassycliq.pokedex.ui.pokemon.PokemonListScreen
import com.jassycliq.pokedex.ui.pokemon.PokemonListViewModel
import com.jassycliq.pokedex.ui.pokemon.PokemonListViewModel.SideEffect.NavigateToPokemonDetail
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun NavGraphBuilder.pokemonNavigation(
    navController: NavController,
    onNavigateToDestination: (SideEffect, Colors) -> Unit,
    ) {
    navigation(startDestination = PokemonListScreen.route, route = "pokemon") {
        PokemonListScreen.composable(navController, onNavigateToDestination)
        PokemonDetailsScreen.composable(navController, onNavigateToDestination)
    }
}

@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
sealed class PokemonNavigation : Navigation {

    class PokemonListScreen : PokemonNavigation() {
        context(NavController)
        override fun navigateTo() {
            navigate(route = route)
        }

        companion object {
            const val route = "pokemonListScreen"

            context(NavGraphBuilder)
            fun composable(
                navController: NavController,
                onNavigateToDestination: (SideEffect, Colors) -> Unit,
                ) {
                composable(route = route) {
                    onNavigateToDestination(NavigateToPokemonList, getDefaultThemeColors())

                    // TODO: Fix the issue with the LazyGridState not being restored when navigating back to the screen.
                    //  This is a known issue with LazyPagingItems. See https://issuetracker.google.com/issues/177245496
                    val viewModel: PokemonListViewModel = koinViewModel()
                    val state by viewModel.collectAsState()
                    val pagingItems = state.pokemonPagingFlow.collectAsLazyPagingItems()
                    val lazyGridState = pagingItems.rememberLazyGridState()

                    viewModel.collectSideEffect { sideEffect ->
                        when (sideEffect) {
                            is NavigateToPokemonDetail -> navController.navigateTo(
                                PokemonDetailsScreen(sideEffect.pokemonId, sideEffect.cardColors)
                            )
                        }
                    }

                    PokemonListScreen(
                        lazyGridState = lazyGridState,
                        pokemonPagingItems = pagingItems,
                        fetchPokemonDetails = viewModel::fetchPokemonDetails,
                        navigateToPokemonDetails = viewModel::navigateToPokemonDetails,
                    )
                }
            }
        }
    }

    class PokemonDetailsScreen(
        private val pokemonId: Int,
        private val cardColors: Colors,
    ) : PokemonNavigation() {

        context(NavController)
        override fun navigateTo() {
            navigateWithArgs(route = route) {
                putInt(POKEMON_DETAIL_POKEMON_ID, pokemonId)
                putParcelable(POKEMON_DETAIL_CARD_COLORS, cardColors)
            }
        }

        companion object {
            const val route = "pokemonDetailsScreen/{${POKEMON_DETAIL_POKEMON_ID}}/{${POKEMON_DETAIL_CARD_COLORS}}"

            context(NavGraphBuilder)
            fun composable(
                navController: NavController,
                onNavigateToDestination: (SideEffect, Colors) -> Unit,
            ) {
                composable(
                    route = route,
                    arguments = listOf(
                        navArgument(name = POKEMON_DETAIL_POKEMON_ID) { NavType.IntType },
                        navArgument(name = POKEMON_DETAIL_CARD_COLORS) { NavType.ParcelableType(Colors::class.java) },
                    ),
                ) { backStackEntry ->
                    backStackEntry.arguments?.parcelable<Colors>(POKEMON_DETAIL_CARD_COLORS)?.let { cardColors ->
                        onNavigateToDestination(NavigateToPokemonDetails, cardColors)
                    }
                    val viewModel: PokemonDetailViewModel = koinViewModel()
                    val state by viewModel.collectAsState()

                    viewModel.collectSideEffect { sideEffect ->
                        when (sideEffect) {
                            is NavigateBack -> navController.popBackStack()
                        }
                    }

                    PokemonDetailScreen(
                        fetchPokemon = viewModel::fetchPokemonDetails,
                        state = state,
                    )
                }
            }
        }
    }
}
