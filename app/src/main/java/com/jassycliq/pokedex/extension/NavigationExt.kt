package com.jassycliq.pokedex.extension

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.jassycliq.pokedex.domain.model.Navigation

fun NavController.navigateWithArgs(route: String, navOptions:  (NavOptionsBuilder.() -> Unit)? = null, args: Bundle.() -> Unit) {
    navigate(route) { navOptions?.invoke(this) }
    currentBackStackEntry?.arguments?.apply {
        args()
    }
}

/**
 * Extension function to navigate to a destination using a Navigation object.
 * @param navigation The navigation object that represents the destination.
 */
fun NavController.navigateTo(navigation: Navigation) {
    navigation.navigateTo()
}
