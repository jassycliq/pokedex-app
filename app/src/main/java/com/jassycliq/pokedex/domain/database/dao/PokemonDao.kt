package com.jassycliq.pokedex.domain.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jassycliq.pokedex.domain.database.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAll(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon")
    fun getAllPaging(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY id DESC")
    fun loadAllByIds(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun loadAllByIdsReversed(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY name DESC ")
    fun loadAllByName(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY name ASC ")
    fun loadAllByNameReversed(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY height DESC ")
    fun loadAllByHeight(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY height ASC ")
    fun loadAllByHeightReversed(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY weight DESC")
    fun loadAllByWeight(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY weight ASC")
    fun loadAllByWeightReversed(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY `order` DESC")
    fun loadAllByOrder(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon ORDER BY `order` ASC")
    fun loadAllByOrderReversed(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon")
    suspend fun clearPokemon()

}
