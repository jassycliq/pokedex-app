package com.jassycliq.pokedex.domain.api

import com.jassycliq.pokedex.domain.model.Pagination
import com.jassycliq.pokedex.domain.model.pokemon.Pokemon
import com.jassycliq.pokedex.domain.model.pokemon.Species
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
    ): ApiResponse<Pagination>

    @GET("pokemon/{id}/")
    suspend fun getPokemonById(@Path("id") id: Int): ApiResponse<Pokemon>

    @GET("pokemon-species/{id}/")
    suspend fun getSpeciesById(@Path("id") id: Int): ApiResponse<Species>

}
