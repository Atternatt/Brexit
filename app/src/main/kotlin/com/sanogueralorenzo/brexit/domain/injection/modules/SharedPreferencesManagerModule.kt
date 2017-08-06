package com.sanogueralorenzo.brexit.domain.injection.modules

import com.sanogueralorenzo.brexit.data.local.SharedPreferencesManager
import com.sanogueralorenzo.brexit.presentation.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesManagerModule {
    @Provides @Singleton
    fun provideSharedPreferencesManager(app: App): SharedPreferencesManager = SharedPreferencesManager(app.applicationContext)
}