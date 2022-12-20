package com.acoders.marvelfanbook.features.superheroes.framework.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSuperHeroes(heroesList: List<SuperHeroEntity>)

    @Query("SELECT * FROM SuperHeroEntity")
    fun getSuperHeroesList(): Flow<List<SuperHeroEntity>>

    @Query("SELECT * FROM SuperHeroEntity WHERE id = :id")
    fun getSuperHeroesById(id: Long): Flow<SuperHeroEntity>

    @Query("SELECT COUNT(id) FROM SuperHeroEntity")
    suspend fun numHeroes(): Int
}