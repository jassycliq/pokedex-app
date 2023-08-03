package com.jassycliq.pokedex.domain.model

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class Colors(
    @ColorInt val surface: Int,
    @ColorInt val primary: Int,
    @ColorInt val onPrimary: Int,
    @ColorInt val text: Int,
): Parcelable
