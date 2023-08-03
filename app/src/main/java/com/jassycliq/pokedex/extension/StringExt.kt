package com.jassycliq.pokedex.extension

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import kotlin.math.round

fun String.toPokemonId(): Int {
    return substringAfter("pokemon/").removeSuffix("/").toInt()
}

fun String.toDisplayName(): String {
    return replace("-", " ").capitalize(Locale.current).split(" ").reduce { acc, s ->
        "${acc.capitalize(Locale.current)} ${s.capitalize(Locale.current)}"
    }
}

fun String?.orEmptyThreeLine(): String {
    return when (this) {
        null -> "\n\n\n"
        else -> this
    }
}

fun Int?.toDisplayNumber(): String {
    return "#${toString().padStart(3, '0')}"
}

fun Int?.toDisplayHeight(): String {
    if (this == null) return "N/A"
    val centimeters = times(10)
    val inches = times(3.937f)
    return "$centimeters cm / ${round(inches * 100.00) / 100.00} in"
}

fun Int?.toDisplayWeight(): String {
    if (this == null) return "N/A"
    val kilogram = div(10)
    val lbs = div(4.536f)
    return "$kilogram kg / ${round(lbs * 100.00) / 100.00} lbs"
}
