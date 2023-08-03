package com.jassycliq.pokedex.domain.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jassycliq.pokedex.domain.model.NamedApiResource

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "height") val height: Int? = null,
    @ColumnInfo(name = "weight") val weight: Int? = null,
    @ColumnInfo(name = "order") val order: Int? = null,
)

fun PokemonEntity.toNamedApiResource(): NamedApiResource = NamedApiResource(
    name = name,
    url = "https://pokeapi.co/api/v2/pokemon/$id",
)

fun List<NamedApiResource>.toPokemonEntityList(): List<PokemonEntity> = map {
    PokemonEntity(
        id = it.url.substringAfterLast("pokemon/").removeSuffix("/").toInt(),
        name = it.name,
    )
}
