package com.haryop.mynewsportal.ui.newslistpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.data.repository.NewsApiOrgRepository
import com.haryop.mynewsportal.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsApiOrgRepository
) : ViewModel() {

    fun start(source: String) {
        _source.value = source
    }

    private val _source = MutableLiveData<String>()

    private val _getHeadlines = _source.switchMap { source ->
        repository.getHeadlines(source)
    }

    val getHeadlines: LiveData<Resource<List<NewsListEntity>>> = _getHeadlines

}