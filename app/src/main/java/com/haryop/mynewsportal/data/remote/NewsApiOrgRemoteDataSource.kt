package com.haryop.mynewsportal.data.remote

import javax.inject.Inject

class NewsApiOrgRemoteDataSource @Inject constructor(
    private val newsApiOrgServices: NewsApiOrgServices
) : BaseDataSource() {

    suspend fun getSearchEndpoint(category: String) = getResult { newsApiOrgServices.getSources(category) }

}