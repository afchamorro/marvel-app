package com.acoders.marvelfanbook.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroDao
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroEntity

@Database(entities = [SuperHeroEntity::class], version = 1, exportSchema = false)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun superheroDao(): SuperHeroDao
}