package com.jassycliq.pokedex.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.jassycliq.pokedex.domain.model.Colors

@Composable
fun getDefaultThemeColors(): Colors {
    return Colors(
        surface = MaterialTheme.colorScheme.surface.toArgb(),
        primary = MaterialTheme.colorScheme.primary.toArgb(),
        onPrimary = MaterialTheme.colorScheme.onPrimary.toArgb(),
        text = MaterialTheme.colorScheme.onSurface.toArgb(),
    )
}
