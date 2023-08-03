package com.jassycliq.pokedex.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.jassycliq.pokedex.domain.model.Colors
import com.jassycliq.pokedex.domain.model.Navigation
import com.jassycliq.pokedex.extension.navigateTo
import com.jassycliq.pokedex.ui.MainViewModel.SideEffect
import com.jassycliq.pokedex.ui.home.HomeScreen
import com.jassycliq.pokedex.ui.navigation.PokedexNavigation.HomeScreen
import com.jassycliq.pokedex.ui.navigation.PokemonNavigation.PokemonListScreen

@Composable
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun PokedexNavigation(
    navController: NavHostController,
    onNavigateToDestination: (SideEffect, Colors) -> Unit,
) {

    NavHost(navController = navController, startDestination = HomeScreen.route) {

        HomeScreen.composable(navController)

        pokemonNavigation(
            navController = navController,
            onNavigateToDestination = onNavigateToDestination,
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalLayoutApi
sealed class PokedexNavigation : Navigation {

    class HomeScreen : PokedexNavigation() {
        context(NavController)
        override fun navigateTo() {
            navigate(route = route)
        }


        companion object {
            const val route = "homeScreen"

            context(NavGraphBuilder)
            fun composable(navController: NavHostController) {
                composable(route = route) {
                    HomeScreen { navController.navigateTo(PokemonListScreen()) }
                }
            }
        }
    }
}
