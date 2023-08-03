package com.jassycliq.pokedex.domain.model

import androidx.navigation.NavController

/**
 * A sealed class that represents the navigation destinations in the app.
 */
interface Navigation {
    /**
     * Uses NavController context to navigate to the destination.
     */
    context(NavController)
    fun navigateTo()
}
