package com.acoders.marvelfanbook.data.di

import com.acoders.marvelfanbook.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DOMAIN = "https://gateway.marvel.com/"
    private const val API_KEY_PARAM = "apikey"

    @Provides
    fun provideDefaultBaseUrl() = DOMAIN

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val request = chain.request()
            val url = request.url
                .newBuilder()
                .addQueryParameter(name = API_KEY_PARAM, value = BuildConfig.MARVEL_PUBLIC_API_KEY)
                .build()
            val updated = request.newBuilder()
                .url(url)
                .build()

            chain.proceed(updated)
        }
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            addInterceptor(loggingInterceptor)
        }
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
}
