package com.sanogueralorenzo.brexit.domain.injection.modules

import com.sanogueralorenzo.brexit.BuildConfig
import com.sanogueralorenzo.brexit.data.remote.ArticleDetailsApi
import com.sanogueralorenzo.brexit.data.remote.ArticleListApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@Singleton
class NetworkModule {
    private val BASE_URL = "http://content.guardianapis.com"
    private val HEADER_API_KEY = "api-key"
    private val GUARDIAN_API_KEY = "enj8pstqu5yat6yesfsdmd39"

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder().addHeader(HEADER_API_KEY, GUARDIAN_API_KEY).build())
    }

    @Provides
    @Singleton
    fun provideHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.addInterceptor(authInterceptor)
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        clientBuilder.readTimeout(10, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
            .add<Date>(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    fun provideArticleListApi(retrofit: Retrofit): ArticleListApi = retrofit.create(ArticleListApi::class.java)

    @Provides
    fun provideArticleDetailsApi(retrofit: Retrofit): ArticleDetailsApi = retrofit.create(ArticleDetailsApi::class.java)
}