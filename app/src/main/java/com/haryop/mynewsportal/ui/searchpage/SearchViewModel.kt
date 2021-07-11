package com.haryop.mynewsportal.ui.searchpage

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
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

    val getEverything: LiveData<Resource<NewsListEntities>> = _getEverything

    fun fetchEverythingLiveData(query:String): LiveData<PagingData<NewsListEntity>> {
        return repository.letEverythingLiveData(query)
            .map { it.map { it } }
            .cachedIn(viewModelScope)
    }

}