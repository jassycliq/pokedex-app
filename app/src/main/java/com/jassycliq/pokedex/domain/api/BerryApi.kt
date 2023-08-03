package com.jassycliq.pokedex.domain.api

import com.jassycliq.pokedex.domain.model.Pagination
import com.jassycliq.pokedex.domain.model.berry.Berry
import com.jassycliq.pokedex.domain.model.berry.BerryFirmness
import com.jassycliq.pokedex.domain.model.berry.BerryFlavor
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BerryApi {

    @GET("berry")
    suspend fun getBerryList(): ApiResponse<Pagination>

    @GET("berry/{id}")
    suspend fun getBerry(@Path("id") id: Int): ApiResponse<Berry>

    @GET("berry-firmness/{id}")
    suspend fun getBerryFirmness(@Path("id") id: Int): ApiResponse<BerryFirmness>

    @GET("berry-flavor/{id}")
    suspend fun getBerryFlavor(@Path("id") id: Int): ApiResponse<BerryFlavor>

}
