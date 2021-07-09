package com.haryop.mynewsportal.data.remote

import com.haryop.mynewsportal.data.entities.NewsListEntities
import com.haryop.mynewsportal.data.entities.SourceEntities
import com.haryop.mynewsportal.utils.ConstantsObj
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiOrgServices {

    @GET("sources?" + ConstantsObj.NEWSAPIORG_BASEPARAM)
    suspend fun getSources(@Query("category") category: String?): Response<SourceEntities>

    @GET("top-headlines?" + ConstantsObj.NEWSAPIORG_BASEPARAM)
    suspend fun getHeadlines(@Query("sources") sources: String?): Response<NewsListEntities>

    @GET("everything?pageSize=" + ConstantsObj.EVERYTHING_PAGE_SIZE + "&" + ConstantsObj.NEWSAPIORG_BASEPARAM)
    suspend fun getEverything(
        @Query("q") query: String?,
        @Query("page") page: String?
    ): Response<NewsListEntities>

}