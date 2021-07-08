package com.haryop.mynewsportal.data.entities

import com.google.gson.annotations.SerializedName

data class NewsListEntity(
    @SerializedName("source") val source: NewsListEntitySource,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String,
)

data class NewsListEntitySource(
    @SerializedName("id") val author: String,
    @SerializedName("name") val title: String
)

