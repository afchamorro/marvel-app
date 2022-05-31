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

    @Query("SELECT * FROM super_heroes")
    fun getSuperHeroesList(): Flow<List<SuperHeroEntity>>
}