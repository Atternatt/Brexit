package com.sanogueralorenzo.brexit.domain.injection.components

import com.sanogueralorenzo.brexit.domain.injection.modules.AppModule
import com.sanogueralorenzo.brexit.domain.injection.modules.DataModule
import com.sanogueralorenzo.brexit.domain.injection.modules.NetworkModule
import com.sanogueralorenzo.brexit.presentation.articledetail.GuardianArticleDetailsActivity
import com.sanogueralorenzo.brexit.presentation.articlelist.GuardianArticleListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, NetworkModule::class))
interface Injector {
    fun inject(activity: GuardianArticleListActivity)
    fun inject(activity: GuardianArticleDetailsActivity)
}
