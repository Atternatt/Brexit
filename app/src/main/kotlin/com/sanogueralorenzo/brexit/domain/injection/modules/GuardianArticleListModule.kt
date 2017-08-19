package com.sanogueralorenzo.brexit.domain.injection.modules

import com.sanogueralorenzo.brexit.data.local.Local
import com.sanogueralorenzo.brexit.data.remote.ArticleListApi
import com.sanogueralorenzo.brexit.domain.repositories.ArticleListRepository
import com.sanogueralorenzo.brexit.presentation.articlelist.GuardianArticleListPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class GuardianArticleListModule {
    @Provides
    fun provideArticleListApi(retrofit: Retrofit): ArticleListApi = retrofit.create(ArticleListApi::class.java)

    @Provides
    fun provideArticleListRepository(articleListApi: ArticleListApi, local: Local): ArticleListRepository = ArticleListRepository(articleListApi, local)

    @Provides
    fun provideArticleListPresenter(articleListRepository: ArticleListRepository): GuardianArticleListPresenter = GuardianArticleListPresenter(articleListRepository)
}