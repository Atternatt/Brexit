package com.sanogueralorenzo.brexit.domain.injection.components

import com.sanogueralorenzo.brexit.domain.injection.modules.*
import com.sanogueralorenzo.brexit.presentation.articledetail.GuardianArticleDetailsActivity
import com.sanogueralorenzo.brexit.presentation.articlelist.GuardianArticleListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, SharedPreferencesManagerModule::class, RetrofitModule::class,
        GuardianArticleListModule::class, GuardianArticleDetailsModule::class))
interface Injector {
    fun inject(activity: GuardianArticleListActivity)
    fun inject(activity: GuardianArticleDetailsActivity)
}