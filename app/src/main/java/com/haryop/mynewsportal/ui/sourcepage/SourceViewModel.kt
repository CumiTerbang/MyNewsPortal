package com.haryop.mynewsportal.ui.sourcepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.haryop.mynewsportal.data.entities.SourceEntity
import com.haryop.mynewsportal.data.repository.NewsApiOrgRepository
import com.haryop.mynewsportal.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val repository: NewsApiOrgRepository
) : ViewModel() {

    fun start(category: String) {
        _categoryd.value = category
    }

    private val _categoryd = MutableLiveData<String>()

    private val _getSources = _categoryd.switchMap { category ->
        repository.getSources(category)
    }

    val getSource: LiveData<Resource<List<SourceEntity>>> = _getSources

}