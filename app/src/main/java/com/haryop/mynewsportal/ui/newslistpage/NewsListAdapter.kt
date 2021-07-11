package com.haryop.synpulsefrontendchallenge.ui.companylist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.ItemBottomSpaceBinding
import com.haryop.mynewsportal.databinding.ItemNewsHeadlineLayoutBinding
import com.haryop.mynewsportal.databinding.ItemNewsLayoutBinding
import com.haryop.mynewsportal.ui.BottomspaceViewHolder

class NewsListAdapter(private val listener: NewsListItemListener) :
    PagingDataAdapter<NewsListEntity, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<NewsListEntity>() {
            override fun areItemsTheSame(oldItem: NewsListEntity, newItem: NewsListEntity) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: NewsListEntity, newItem: NewsListEntity) =
                oldItem.url == newItem.url
        }
    }

    interface NewsListItemListener {
        fun onClickedItem(item: NewsListEntity)
    }

    private val items = ArrayList<Any>()

    fun getItems(): ArrayList<Any> {
        return items
    }

    fun setItems(items: ArrayList<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(_items: ArrayList<Any>) {
        this.items.addAll(_items)
        notifyItemRangeInserted(this.items.size-_items.size, this.items.size)
    }

    val SEARCH_PAGE = 0
    val NEWSLIST_PAGE = 1
    private var current_page = 0
    fun setCurrent_page(current_page:Int){
        this.current_page = current_page
    }

    val BOTTOMSPACE_LAYOUT = "BOTTOMSPACE_LAYOUT"
    val ITEM_TYPE_NEWS_ITEM_LAYOUT = 0
    val ITEM_TYPE_BOTTOMSPACE_LAYOUT = 1
    val ITEM_TYPE_NEWS_HEADLINE_LAYOUT = 2
    override fun getItemViewType(position: Int): Int {
        if(current_page==SEARCH_PAGE){
            if (position == 0 && super.getItem(position) is NewsListEntity && current_page == NEWSLIST_PAGE) {
                return ITEM_TYPE_NEWS_HEADLINE_LAYOUT
            } else if (super.getItem(position) is NewsListEntity) {
                return ITEM_TYPE_NEWS_ITEM_LAYOUT
            } else {
                return ITEM_TYPE_BOTTOMSPACE_LAYOUT
            }
        }else{
            if (position == 0 && items[position] is NewsListEntity && current_page == NEWSLIST_PAGE) {
                return ITEM_TYPE_NEWS_HEADLINE_LAYOUT
            } else if (items[position] is NewsListEntity) {
                return ITEM_TYPE_NEWS_ITEM_LAYOUT
            } else {
                return ITEM_TYPE_BOTTOMSPACE_LAYOUT
            }
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

    override fun getItemCount(): Int{
        if(current_page==SEARCH_PAGE){
            return super.getItemCount()
        }else{
            //if (current_page == NEWSLIST_PAGE){
            return items.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.getItemViewType()) {
            ITEM_TYPE_NEWS_HEADLINE_LAYOUT -> {
                var mHolder = holder as NewsHeadlineViewHolder
                if(current_page==SEARCH_PAGE){
                    Log.e("search adapter", (super.getItem(position) as NewsListEntity).title)
                    mHolder.bind(super.getItem(position) as NewsListEntity)
                }else if (current_page == NEWSLIST_PAGE){
                    mHolder.bind(items[position] as NewsListEntity)
                }
            }

            ITEM_TYPE_NEWS_ITEM_LAYOUT -> {
                var mHolder = holder as NewsItemViewHolder
                if(current_page==SEARCH_PAGE){
                    Log.e("search adapter", (super.getItem(position) as NewsListEntity).title)
                    mHolder.bind(super.getItem(position) as NewsListEntity)
                }else if (current_page == NEWSLIST_PAGE){
                    mHolder.bind(items[position] as NewsListEntity)
                }
            }

            ITEM_TYPE_BOTTOMSPACE_LAYOUT -> {
                //do nothing
            }

        }
    }

}