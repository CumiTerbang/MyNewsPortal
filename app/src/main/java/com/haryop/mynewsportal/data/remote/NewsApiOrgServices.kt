package com.haryop.mynewsportal.data.remote

import com.haryop.mynewsportal.data.entities.SourceEntities
import com.haryop.mynewsportal.utils.ConstantsObj
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiOrgServices {

    @GET("sources?"+ConstantsObj.NEWSAPIORG_BASEPARAM)
    suspend fun getSources(@Query("category") category: String?): Response<SourceEntities>

}