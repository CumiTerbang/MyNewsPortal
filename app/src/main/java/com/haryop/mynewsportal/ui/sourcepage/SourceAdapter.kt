package com.haryop.synpulsefrontendchallenge.ui.companylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haryop.mynewsportal.data.entities.SourceEntity
import com.haryop.mynewsportal.databinding.ItemBottomSpaceBinding
import com.haryop.mynewsportal.databinding.ItemSourceLayoutBinding
import com.haryop.mynewsportal.ui.BottomspaceViewHolder

class SourceAdapter(private val listener: SourceItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface SourceItemListener {
        fun onClickedSource(sources: String)
    }

    private val items = ArrayList<Any>()

    fun setItems(items: ArrayList<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    val ITEM_TYPE_SOURCE_ITEM_LAYOUT = 0
    val ITEM_TYPE_BOTTOMSPACE_LAYOUT = 1
    override fun getItemViewType(position: Int): Int {
        if (items[position] is SourceEntity) {
            return ITEM_TYPE_SOURCE_ITEM_LAYOUT
        } else {
            return ITEM_TYPE_BOTTOMSPACE_LAYOUT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_SOURCE_ITEM_LAYOUT -> {
                val binding: ItemSourceLayoutBinding =
                    ItemSourceLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return SourceViewHolder(binding, listener)
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
            ITEM_TYPE_SOURCE_ITEM_LAYOUT -> {
                var mHolder = holder as SourceViewHolder
                mHolder.bind(items[position] as SourceEntity)
            }

            ITEM_TYPE_BOTTOMSPACE_LAYOUT -> {
                //do nothing
            }

        }
    }

}