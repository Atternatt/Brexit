package com.sanogueralorenzo.brexit.presentation

import android.app.Application
import com.sanogueralorenzo.brexit.BuildConfig
import com.sanogueralorenzo.brexit.domain.injection.components.DaggerInjector
import com.sanogueralorenzo.brexit.domain.injection.components.Injector
import com.sanogueralorenzo.brexit.domain.injection.modules.AppModule
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    var injector: Injector? = null
        private set

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initTimber()
    }

    private fun initDagger() {
        injector = DaggerInjector
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}