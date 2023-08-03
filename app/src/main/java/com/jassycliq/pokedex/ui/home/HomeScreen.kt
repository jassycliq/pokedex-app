package com.jassycliq.pokedex.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun HomeScreen(
    navigateToPokemonList: () -> Unit,
) {
    LaunchedEffect(Unit) {
        navigateToPokemonList()
    }
}
