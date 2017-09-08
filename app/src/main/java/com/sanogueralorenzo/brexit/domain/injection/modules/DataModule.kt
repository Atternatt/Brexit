package com.sanogueralorenzo.brexit.domain.injection.modules

import com.sanogueralorenzo.brexit.data.local.Local
import com.sanogueralorenzo.brexit.presentation.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideData(app: App): Local = Local(app.applicationContext)
}