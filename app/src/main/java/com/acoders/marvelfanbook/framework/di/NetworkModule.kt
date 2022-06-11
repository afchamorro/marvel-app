package com.acoders.marvelfanbook.data.di

import com.acoders.marvelfanbook.BuildConfig
import com.acoders.marvelfanbook.core.extensions.md5
import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DOMAIN = "https://gateway.marvel.com/v1/public/"
    private const val API_KEY_PARAM = "apikey"
    private const val TIME_STAMP_PARAM = "ts"
    private const val HASH_PARAM = "hash"

    @Provides
    fun provideDefaultBaseUrl() = DOMAIN

    // DUDAS HACER AÑADIR AQUI EL HASH Y EL TIMESTAMP, yo creo que no al ser singleton
    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().apply {

        addInterceptor { chain: Interceptor.Chain ->
            chain.proceed(getAuthInterceptor(chain))
        }

        if (BuildConfig.DEBUG) {
            addInterceptor(getLoggingInterceptor())
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

    private fun getAuthInterceptor(chain: Interceptor.Chain): Request {

        val ts = System.currentTimeMillis()
        val hash =
            "$ts${BuildConfig.MARVEL_PRIVATE_API_KEY}${BuildConfig.MARVEL_PUBLIC_API_KEY}".md5()
        val request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter(name = API_KEY_PARAM, value = BuildConfig.MARVEL_PUBLIC_API_KEY)
            .addQueryParameter(TIME_STAMP_PARAM, ts.toString())
            .addQueryParameter(HASH_PARAM, hash)
            .build()
        return request.newBuilder().apply {
            url(url)
        }.build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(MarvelEndpoints::class.java)

}
