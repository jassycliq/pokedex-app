package com.jassycliq.pokedex

import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.logger.Level
import org.koin.core.logger.Level.DEBUG
import org.koin.core.logger.Level.ERROR
import org.koin.core.logger.Level.INFO
import org.koin.core.logger.Level.NONE
import org.koin.core.logger.Level.WARNING
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class PokedexKoinLogger(level: Level = DEBUG) : Logger(level) {
    override fun display(level: Level, msg: MESSAGE) {
        when (level) {
            DEBUG -> Timber.d(msg)
            INFO -> Timber.i(msg)
            WARNING -> Timber.w(msg)
            ERROR -> Timber.e(msg)
            NONE -> Unit
        }
    }
}

class PokedexOkHttpLogger(private val level: Level = Level.DEBUG) : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        when (level) {
            Level.DEBUG -> Timber.tag("OkHttp").d(message)
            Level.INFO -> Timber.tag("OkHttp").i(message)
            Level.WARNING -> Timber.tag("OkHttp").w(message)
            Level.ERROR -> Timber.tag("OkHttp").e(message)
            Level.NONE -> Unit
        }
    }

    sealed class Level {
        object NONE : Level()
        object INFO : Level()
        object DEBUG : Level()
        object WARNING : Level()
        object ERROR : Level()
    }
}
