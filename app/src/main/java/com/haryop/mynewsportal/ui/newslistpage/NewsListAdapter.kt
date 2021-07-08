package com.haryop.synpulsefrontendchallenge.ui.companylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.data.entities.SourceEntity
import com.haryop.mynewsportal.databinding.ItemBottomSpaceBinding
import com.haryop.mynewsportal.databinding.ItemNewsHeadlineLayoutBinding
import com.haryop.mynewsportal.databinding.ItemNewsLayoutBinding
import com.haryop.mynewsportal.databinding.ItemSourceLayoutBinding
import com.haryop.mynewsportal.ui.BottomspaceViewHolder

class NewsListAdapter(private val listener: NewsListItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface NewsListItemListener {
        fun onClickedItem(item: NewsListEntity)
    }

    private val items = ArrayList<Any>()

    fun getItems():ArrayList<Any>{
        return items
    }

    fun setItems(items: ArrayList<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    val ITEM_TYPE_NEWS_ITEM_LAYOUT = 0
    val ITEM_TYPE_BOTTOMSPACE_LAYOUT = 1
    val ITEM_TYPE_NEWS_HEADLINE_LAYOUT = 2
    override fun getItemViewType(position: Int): Int {
        if (position == 0 && items[position] is NewsListEntity) {
            return ITEM_TYPE_NEWS_HEADLINE_LAYOUT
        } else if (items[position] is NewsListEntity) {
            return ITEM_TYPE_NEWS_ITEM_LAYOUT
        } else {
            return ITEM_TYPE_BOTTOMSPACE_LAYOUT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_NEWS_HEADLINE_LAYOUT -> {
                val binding: ItemNewsHeadlineLayoutBinding =
                    ItemNewsHeadlineLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return NewsHeadlineViewHolder(binding, listener)
            }
            ITEM_TYPE_NEWS_ITEM_LAYOUT -> {
                val binding: ItemNewsLayoutBinding =
                    ItemNewsLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return NewsItemViewHolder(binding, listener)
            }
            else -> {
                val binding: ItemBottomSpaceBinding =
                    ItemBottomSpaceBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return BottomspaceViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.getItemViewType()) {
            ITEM_TYPE_NEWS_HEADLINE_LAYOUT -> {
                var mHolder = holder as NewsHeadlineViewHolder
                mHolder.bind(items[position] as NewsListEntity)
            }

            ITEM_TYPE_NEWS_ITEM_LAYOUT -> {
                var mHolder = holder as NewsItemViewHolder
                mHolder.bind(items[position] as NewsListEntity)
            }

            ITEM_TYPE_BOTTOMSPACE_LAYOUT -> {
                //do nothing
            }

        }
    }

}