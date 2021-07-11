package com.haryop.mynewsportal.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.utils.ConstantsObj
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsListPagingSource @Inject constructor(
    private val newsApiOrgServices: NewsApiOrgServices,
    private val query: String
) : PagingSource<Int, NewsListEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsListEntity> {
        val page = params.key ?: ConstantsObj.EVERYTHING_PAGE_DEFAULT_INDEX
        return try {
            val response = newsApiOrgServices.getPagingEverything(query, page.toString())
            Log.e("NewsListPagingSource", "${response.totalResults}")
            LoadResult.Page(
                response.articles,
                prevKey = if (page == ConstantsObj.EVERYTHING_PAGE_DEFAULT_INDEX) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            Log.e("NewsListPagingSource", "error IOException")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("NewsListPagingSource", "error HttpException")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsListEntity>): Int? {
        TODO("Not yet implemented")
    }
}