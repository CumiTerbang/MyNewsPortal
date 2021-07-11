package com.haryop.mynewsportal.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.haryop.mynewsportal.data.remote.NewsApiOrgRemoteDataSource
import com.haryop.mynewsportal.data.remote.NewsApiOrgServices
import com.haryop.mynewsportal.data.repository.NewsApiOrgRepository
import com.haryop.mynewsportal.utils.ConstantsObj
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) //ApplicationComponent ny adeprecated diganti jadi SingletonComponent
object AppModule {

    fun logOkHttplient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return client
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(ConstantsObj.NEWSAPIORG_BASEURL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(logOkHttplient())
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideAPIService(retrofit: Retrofit): NewsApiOrgServices =
        retrofit.create(NewsApiOrgServices::class.java)

    @Singleton
    @Provides
    fun provideNewsApiOrgRemoteDataSource(newsApiOrgServices: NewsApiOrgServices) =
        NewsApiOrgRemoteDataSource(newsApiOrgServices)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: NewsApiOrgRemoteDataSource,
        newsApiOrgServices: NewsApiOrgServices
    ) =
        NewsApiOrgRepository(remoteDataSource, newsApiOrgServices)

}
