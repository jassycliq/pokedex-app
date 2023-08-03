package com.jassycliq.pokedex

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokedexState(
    val forceTopAppBar: Boolean,
    val showFab: Boolean,
    val showBackIcon: Boolean,
    val showSearchIcon: Boolean,
    val colors: PokedexColors,
    val selectedPokemonId: Int? = null,
) : Parcelable {

    @Parcelize
    data class PokedexColors(
        val surface: Int,
        val background: Int,
        val vector: Int,
        val title: Int,
        val body: Int,
    ) : Parcelable
}
