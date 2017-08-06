package com.sanogueralorenzo.brexit.domain.injection.modules

import com.sanogueralorenzo.brexit.presentation.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var app: App) {
    @Provides @Singleton
    fun provideApp(): App = app
}