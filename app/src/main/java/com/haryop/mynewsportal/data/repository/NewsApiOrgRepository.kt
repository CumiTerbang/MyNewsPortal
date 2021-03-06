package com.haryop.mynewsportal.data.repository

import android.util.Log
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

    fun getEverything(map: HashMap<String, String>) = performGetEverythingBodyOperation(
        networkCall = {
            remoteDataSource.getEverything(
                map.get("query").toString(),
                map.get("page").toString()
            )
        }
    )

//    fun getEverything(map: HashMap<String, String>) = performGetHeadlinesOperation(
//        networkCall = {
//            remoteDataSource.getEverything(
//                map.get("query").toString(),
//                map.get("page").toString()
//            )
//        }
//    )

    private var everything_total_articles = 0
    fun getEverything_total_articles(): Int {
        return everything_total_articles
    }

    fun <A> performGetHeadlinesOperation(
        networkCall: suspend () -> Resource<A>
    ): LiveData<Resource<List<NewsListEntity>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                val listSource = (responseStatus.data!! as NewsListEntities).articles
                everything_total_articles = (responseStatus.data!! as NewsListEntities).totalResults
                Log.e("repo", "everything_total_articles: " + everything_total_articles)
                emit(Resource.success(listSource))

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
        }

    fun <A> performGetEverythingBodyOperation(
        networkCall: suspend () -> Resource<A>
    ): LiveData<Resource<NewsListEntities>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                val newsListEntities = responseStatus.data!! as NewsListEntities
                emit(Resource.success(newsListEntities))

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
        }

}