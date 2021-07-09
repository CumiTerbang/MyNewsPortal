package com.haryop.synpulsefrontendchallenge.ui.companylist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.ItemNewsLayoutBinding
import com.haryop.synpulsefrontendchallenge.utils.setDate
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewsItemViewHolder(
    private val itemBinding: ItemNewsLayoutBinding,
    private val listener: NewsListAdapter.NewsListItemListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: NewsListEntity) = with(itemBinding) {
        title.text = item.title
        date.text = itemView.context.setDate(item.publishedAt)
        itemView.setOnClickListener { listener.onClickedItem(item) }

        Glide.with(itemBinding.root)
            .load(item.urlToImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(itemImage)

    }

}