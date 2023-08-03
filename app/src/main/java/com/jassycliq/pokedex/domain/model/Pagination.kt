package com.jassycliq.pokedex.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@Immutable
@JsonClass(generateAdapter = true)
data class Pagination(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: String? = null,
    @Json(name = "previous")
    val previous: String? = null,
    @Json(name = "results")
    val results: List<NamedApiResource>,
)
