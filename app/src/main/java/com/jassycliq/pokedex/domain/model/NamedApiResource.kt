package com.jassycliq.pokedex.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Keep
@Immutable
@Parcelize
@JsonClass(generateAdapter = true)
data class NamedApiResource(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String,
) : Parcelable {
    companion object
}

fun emptyNamedApiResource() = NamedApiResource(name = "", url = "")
