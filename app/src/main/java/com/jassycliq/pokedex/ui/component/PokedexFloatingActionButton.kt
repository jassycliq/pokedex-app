package com.jassycliq.pokedex.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PokedexFloatingActionButton(
    showFab: Boolean,
    scrollToTop: () -> Unit,
) {
    AnimatedVisibility(visible = showFab) {
        FloatingActionButton(
            onClick = scrollToTop,
            modifier = Modifier
                .size(56.dp),
        ) {
            Icon(
                imageVector = Icons.TwoTone.KeyboardArrowUp,
                contentDescription = "Scroll to top",
            )
        }
    }
}
