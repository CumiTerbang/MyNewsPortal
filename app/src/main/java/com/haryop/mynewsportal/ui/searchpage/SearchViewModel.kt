package com.haryop.mynewsportal.ui.searchpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.haryop.mynewsportal.data.entities.NewsListEntities
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.data.repository.NewsApiOrgRepository
import com.haryop.mynewsportal.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: NewsApiOrgRepository
) : ViewModel() {

    fun start(map:HashMap<String, String>) {
        _map.value = map
    }

    private val _map = MutableLiveData<HashMap<String,String>>()

    private val _getEverything = _map.switchMap { map ->
        repository.getEverything(map)
    }

    fun getTotal_articles():Int{
        return repository.getEverything_total_articles()
    }

    val getEverything: LiveData<Resource<NewsListEntities>> = _getEverything

}