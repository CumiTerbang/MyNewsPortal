package com.haryop.mynewsportal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.haryop.mynewsportal.data.entities.NewsListEntities
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.data.entities.SourceEntities
import com.haryop.mynewsportal.data.entities.SourceEntity
import com.haryop.mynewsportal.data.remote.NewsApiOrgRemoteDataSource
import com.haryop.mynewsportal.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NewsApiOrgRepository @Inject constructor(
    private val remoteDataSource: NewsApiOrgRemoteDataSource
) {

    fun getSources(category: String) = performGetSourcesOperation(
        networkCall = { remoteDataSource.getSources(category) }
    )

    fun <A> performGetSourcesOperation(
        networkCall: suspend () -> Resource<A>
    ): LiveData<Resource<List<SourceEntity>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                val listSource = (responseStatus.data!! as SourceEntities).sources
                emit(Resource.success(listSource))

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
        }

    fun getHeadlines(source: String) = performGetHeadlinesOperation(
        networkCall = { remoteDataSource.getHeadlines(source) }
    )

    fun <A> performGetHeadlinesOperation(
        networkCall: suspend () -> Resource<A>
    ): LiveData<Resource<List<NewsListEntity>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                val listSource = (responseStatus.data!! as NewsListEntities).articles
                emit(Resource.success(listSource))

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
        }

}