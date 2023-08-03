package com.jassycliq.pokedex.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.jassycliq.pokedex.domain.model.Colors

@ExperimentalMaterial3Api
@Composable
fun PokedexTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior?,
    colors: Colors?,
    title: String? = "Pokédex",
    navigationIcon:  @Composable () -> Unit,
    actions: @Composable (RowScope.() -> Unit),
) {
    val topAppBarColors = when (colors) {
        null -> topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
        else -> topAppBarColors(
            containerColor = Color(colors.primary),
            scrolledContainerColor = Color(colors.primary),
            navigationIconContentColor = Color(colors.onPrimary),
            titleContentColor = Color(colors.onPrimary),
            actionIconContentColor = Color(colors.onPrimary)
        )
    }

    TopAppBar(
        title = {
            Text(
                text = title ?: "Pokédex",
                color = Color(colors?.onPrimary ?: MaterialTheme.colorScheme.onPrimary.toArgb()),
            )
        },
        navigationIcon = { navigationIcon() },
        actions = { actions() },
        scrollBehavior = scrollBehavior,
        colors = topAppBarColors,
    )
}
