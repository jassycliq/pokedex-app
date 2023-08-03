package com.jassycliq.pokedex.data.database

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jassycliq.pokedex.domain.api.PokemonApi
import com.jassycliq.pokedex.domain.database.PokedexDatabase
import com.jassycliq.pokedex.domain.database.entity.PokemonEntity
import com.jassycliq.pokedex.domain.database.entity.RemoteKeysEntity
import com.jassycliq.pokedex.domain.database.entity.toPokemonEntityList
import com.skydoves.sandwich.getOrElse
import com.skydoves.sandwich.mapSuccess
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokedexDatabase: PokedexDatabase,
    private val pokemonApi: PokemonApi,
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val pokemonList = pokemonApi.getPokemonList(offset = page * state.config.pageSize, limit = state.config.pageSize)
                .mapSuccess { results.toPokemonEntityList() }
                .getOrElse(emptyList())

            val endOfPaginationReached = pokemonList.isEmpty()
            pokedexDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokedexDatabase.remoteKeysDao().clearRemoteKeys()
                    pokedexDatabase.pokemonDao().clearPokemon()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = pokemonList.map {
                    RemoteKeysEntity(repoId = it.id.toPage(state.config.pageSize), prevKey = prevKey, nextKey = nextKey)
                }
                pokedexDatabase.remoteKeysDao().insertAll(keys)
                pokedexDatabase.pokemonDao().insertAll(pokemonList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                pokedexDatabase.remoteKeysDao().remoteKeysRepoId(pokemon.id.toPage(state.config.pageSize))
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                pokedexDatabase.remoteKeysDao().remoteKeysRepoId(pokemon.id.toPage(state.config.pageSize))
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokemonEntity>
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { pokemonId ->
                pokedexDatabase.remoteKeysDao().remoteKeysRepoId(pokemonId.toPage(state.config.pageSize))
            }
        }
    }

    private fun Int.toPage(pageSize: Int): Long {
        return Math.floorDiv(this, pageSize).toLong()
    }
}