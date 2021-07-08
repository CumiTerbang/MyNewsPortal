package com.haryop.mynewsportal.data.entities

import com.google.gson.annotations.SerializedName

data class NewsListEntities(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<NewsListEntity>
)


