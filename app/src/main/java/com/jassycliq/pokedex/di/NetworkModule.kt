package com.jassycliq.pokedex.di

import android.content.Context
import com.jassycliq.pokedex.PokedexOkHttpLogger
import com.jassycliq.pokedex.domain.api.BerryApi
import com.jassycliq.pokedex.domain.api.PokemonApi
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(
    includes = [
        ApiModule::class,
    ]
)
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
        private const val CACHE_INTERCEPTOR = "CacheInterceptor"
        private const val LOGGING_INTERCEPTOR = "HttpLoggingInterceptor"
    }

    @Singleton
    fun providesMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    fun providesCache(context: Context): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @Named(CACHE_INTERCEPTOR)
    fun providesCacheInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder().header("Cache-Control", "public, max-age=" + 60).build()
        chain.proceed(request)
    }

    @Singleton
    @Named(LOGGING_INTERCEPTOR)
    fun providesLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor(PokedexOkHttpLogger()).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    fun providesOkHttpClient(
        cache: Cache,
        @Named(CACHE_INTERCEPTOR) cacheInterceptor: Interceptor,
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(cacheInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    fun providesRetrofitClient(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .build()

}

@Module
class ApiModule {

    @Singleton
    fun providesBerryApi(retrofit: Retrofit): BerryApi {
        return retrofit.create(BerryApi::class.java)
    }

    @Singleton
    fun providesPokemonApi(retrofit: Retrofit): PokemonApi {
        return retrofit.create(PokemonApi::class.java)
    }

}
