package com.jassycliq.pokedex

import android.app.Application
import com.jassycliq.pokedex.di.DatabaseModule
import com.jassycliq.pokedex.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module
import timber.log.Timber

class PokedexApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@PokedexApplication)
            logger(PokedexKoinLogger())
            modules(
                listOf(
                    defaultModule,
                    NetworkModule().module,
                    DatabaseModule().module,
                )
            )
        }
    }
}