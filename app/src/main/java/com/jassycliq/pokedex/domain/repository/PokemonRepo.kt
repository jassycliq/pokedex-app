package com.jassycliq.pokedex.domain.repository

import androidx.paging.Pager
import com.jassycliq.pokedex.domain.database.entity.PokemonEntity
import com.jassycliq.pokedex.domain.model.pokemon.Pokemon
import com.jassycliq.pokedex.domain.model.pokemon.Species

interface PokemonRepo {

    fun getAllPokemon(): Pager<Int, PokemonEntity>

    suspend fun getPokemonById(id: Int): Pokemon?

    suspend fun getSpeciesById(id: Int): Species?

}
