package com.jassycliq.pokedex.domain.model.berry

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class BerryFlavor(
    @Json(name = "berries")
    val berries: List<Berry>,
    @Json(name = "contest_type")
    val contestType: ContestType,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "names")
    val names: List<Name>
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Berry(
        @Json(name = "berry")
        val berry: Berry,
        @Json(name = "potency")
        val potency: Int
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Berry(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class ContestType(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Name(
        @Json(name = "language")
        val language: Language,
        @Json(name = "name")
        val name: String
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Language(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }
}