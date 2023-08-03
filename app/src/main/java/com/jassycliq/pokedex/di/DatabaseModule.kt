package com.jassycliq.pokedex.di

import android.content.Context
import androidx.room.Room
import com.jassycliq.pokedex.domain.database.PokedexDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class DatabaseModule {

    companion object {
        private const val DATABASE_NAME = "pokedex.db"
    }

    @Singleton
    fun providesDatabase(context: Context): PokedexDatabase {
        return Room.databaseBuilder(context, PokedexDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    fun providesPokemonDao(database: PokedexDatabase) = database.pokemonDao()

    @Singleton
    fun providesRemoteKeysDao(database: PokedexDatabase) = database.remoteKeysDao()

}
