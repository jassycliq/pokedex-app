@file:OptIn(ExperimentalPagingApi::class)

package com.jassycliq.pokedex.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jassycliq.pokedex.data.database.PokemonRemoteMediator
import com.jassycliq.pokedex.domain.api.PokemonApi
import com.jassycliq.pokedex.domain.database.PokedexDatabase
import com.jassycliq.pokedex.domain.database.entity.PokemonEntity
import com.jassycliq.pokedex.domain.model.pokemon.Pokemon
import com.jassycliq.pokedex.domain.model.pokemon.Species
import com.jassycliq.pokedex.domain.repository.PokemonRepo
import com.skydoves.sandwich.getOrNull
import org.koin.core.annotation.Singleton

private const val PAGE_SIZE = 20

@Singleton(binds = [PokemonRepo::class])
class PokemonRepoImpl(
    private val pokemonApi: PokemonApi,
    private val pokedexDatabase: PokedexDatabase,
) : PokemonRepo {

    override fun getAllPokemon():Pager<Int, PokemonEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE * 3,
                initialLoadSize = PAGE_SIZE * 3,
                enablePlaceholders = true,
            ),
            remoteMediator = PokemonRemoteMediator(
                pokemonApi = pokemonApi,
                pokedexDatabase = pokedexDatabase,
            ),
            pagingSourceFactory = { pokedexDatabase.pokemonDao().getAllPaging() },
        )
    }

    override suspend fun getPokemonById(id: Int): Pokemon? {
        return pokemonApi.getPokemonById(id).getOrNull()
    }

    override suspend fun getSpeciesById(id: Int): Species? {
        return pokemonApi.getSpeciesById(id).getOrNull()
    }
}
