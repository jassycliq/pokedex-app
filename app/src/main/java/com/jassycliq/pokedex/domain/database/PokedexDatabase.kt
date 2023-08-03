package com.jassycliq.pokedex.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jassycliq.pokedex.domain.database.dao.PokemonDao
import com.jassycliq.pokedex.domain.database.dao.RemoteKeysDao
import com.jassycliq.pokedex.domain.database.entity.PokemonEntity
import com.jassycliq.pokedex.domain.database.entity.RemoteKeysEntity

@Database(
    entities = [
        PokemonEntity::class,
        RemoteKeysEntity::class,
               ],
    version = 3,
)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
