package com.haryop.mynewsportal.data.remote

import javax.inject.Inject

class NewsApiOrgRemoteDataSource @Inject constructor(
    private val newsApiOrgServices: NewsApiOrgServices
) : BaseDataSource() {

    suspend fun getSources(category: String) = getResult { newsApiOrgServices.getSources(category) }

    suspend fun getHeadlines(source: String) = getResult { newsApiOrgServices.getHeadlines(source) }

    suspend fun getEverything(query: String, page: String) = getResult { newsApiOrgServices.getEverything(query, page) }

}