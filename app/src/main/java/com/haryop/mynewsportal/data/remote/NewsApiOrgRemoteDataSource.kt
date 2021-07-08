package com.haryop.mynewsportal.data.remote

import javax.inject.Inject

class NewsApiOrgRemoteDataSource @Inject constructor(
    private val newsApiOrgServices: NewsApiOrgServices
) : BaseDataSource() {

    suspend fun getSources(category: String) = getResult { newsApiOrgServices.getSources(category) }

    suspend fun getHeadlines(source: String) = getResult { newsApiOrgServices.getHeadlines(source) }

}