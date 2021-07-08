package com.haryop.synpulsefrontendchallenge.ui.companylist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.ItemNewsHeadlineLayoutBinding
import com.haryop.mynewsportal.databinding.ItemNewsLayoutBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewsHeadlineViewHolder(
    private val itemBinding: ItemNewsHeadlineLayoutBinding,
    private val listener: NewsListAdapter.NewsListItemListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: NewsListEntity) = with(itemBinding) {
        title.text = item.title
        date.text = setDate(item.publishedAt)
        itemView.setOnClickListener { listener.onClickedItem(item) }

        Glide.with(itemBinding.root)
            .load(item.urlToImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(headlineImage)

    }

    fun setDate(publishedAt: String): String {
        var date: String = publishedAt
        try {
            //publishedAt = 2021-07-08T10:30:11+00:00
            if (publishedAt.contains("Z")) {
                publishedAt.replace("Z","")
            }

            var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val newDate: Date = format.parse(publishedAt)
            format = SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm")
            date = format.format(newDate)

        } catch (e: Exception) {
            if (date.contains("Z")) {
                date.replace("Z","")
            }

            date.replace("T", " ")
        }

        return date
    }


}