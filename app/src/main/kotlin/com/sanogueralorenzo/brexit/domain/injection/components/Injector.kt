package com.sanogueralorenzo.brexit.domain.injection.components

import com.sanogueralorenzo.brexit.domain.injection.modules.*
import com.sanogueralorenzo.brexit.presentation.articledetail.GuardianArticleDetailsActivity
import com.sanogueralorenzo.brexit.presentation.articlelist.GuardianArticleListActivity
import dagger.Component
import javax.inject.Singleton

/**
 * I believe there is a better approach than having just 1 Injector for the whole app.
 * The best implementation of this I've seen so far is the use of Component interfaces.
 * These are feature based.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class, SharedPreferencesManagerModule::class, RetrofitModule::class,
        GuardianArticleListModule::class, GuardianArticleDetailsModule::class))
interface Injector {
    fun inject(activity: GuardianArticleListActivity)
    fun inject(activity: GuardianArticleDetailsActivity)
}
