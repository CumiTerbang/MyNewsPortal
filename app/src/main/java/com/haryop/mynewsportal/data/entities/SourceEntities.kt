package com.haryop.mynewsportal.data.entities

import com.google.gson.annotations.SerializedName

data class SourceEntities(
    @SerializedName("status") val status: String,
    @SerializedName("sources") val sources: List<SourceEntity>
)
