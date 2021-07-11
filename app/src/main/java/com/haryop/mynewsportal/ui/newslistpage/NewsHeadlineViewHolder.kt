package com.haryop.synpulsefrontendchallenge.ui.companylist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.ItemNewsHeadlineLayoutBinding
import com.haryop.synpulsefrontendchallenge.utils.setDate
import com.haryop.synpulsefrontendchallenge.utils.setImageGlide
import java.util.*

class NewsHeadlineViewHolder(
    private val itemBinding: ItemNewsHeadlineLayoutBinding,
    private val listener: NewsListAdapter.NewsListItemListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: NewsListEntity) = with(itemBinding) {
        title.text = item.title
        date.text = itemView.context.setDate(item.publishedAt)
        itemView.setOnClickListener { listener.onClickedItem(item) }

        itemView.context.setImageGlide(item.urlToImage, itemBinding.root, headlineImage)

    }



}