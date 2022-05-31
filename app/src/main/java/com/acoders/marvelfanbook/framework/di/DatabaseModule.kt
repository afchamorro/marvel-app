package com.acoders.marvelfanbook.framework.di

import android.app.Application
import androidx.room.Room
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroDao
import com.acoders.marvelfanbook.framework.database.MarvelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMarvelDatabase(app: Application): MarvelDatabase = Room.databaseBuilder(
        app, MarvelDatabase::class.java,
        "marvel-db"
    ).build()

    @Provides
    @Singleton
    fun provideSuperHeroesDao(marvelDatabase: MarvelDatabase) : SuperHeroDao = marvelDatabase.superheroDao()
}