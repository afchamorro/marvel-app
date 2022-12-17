package com.acoders.marvelfanbook.di

import com.acoders.marvelfanbook.data.di.NetworkModule
import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [NetworkModule::class])
object TestNetWorkModule {

    @Provides
    @Singleton
    fun provideDefaultBaseUrl() = "http://localhost:8080"

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().apply {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        addInterceptor(loggingInterceptor)

    }.build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MarvelEndpoints =
        retrofit.create(MarvelEndpoints::class.java)
}