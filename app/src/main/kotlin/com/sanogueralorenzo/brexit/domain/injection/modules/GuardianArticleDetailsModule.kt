package com.sanogueralorenzo.brexit.domain.injection.modules

import com.sanogueralorenzo.brexit.data.local.SharedPreferencesManager
import com.sanogueralorenzo.brexit.data.remote.ArticleDetailsApi
import com.sanogueralorenzo.brexit.domain.repositories.ArticleDetailsRepository
import com.sanogueralorenzo.brexit.presentation.articledetail.GuardianArticleDetailsPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class GuardianArticleDetailsModule {

    @Provides
    fun provideArticleDetailsApi(retrofit: Retrofit): ArticleDetailsApi = retrofit.create(ArticleDetailsApi::class.java)

    @Provides
    fun provideArticleDetailsRepository(articleDetailsApi: ArticleDetailsApi, sharedPreferencesManager: SharedPreferencesManager): ArticleDetailsRepository = ArticleDetailsRepository(articleDetailsApi, sharedPreferencesManager)

    @Provides
    fun provideArticleDetailsPresenter(articleDetailsRepository: ArticleDetailsRepository): GuardianArticleDetailsPresenter = GuardianArticleDetailsPresenter(articleDetailsRepository)

}
