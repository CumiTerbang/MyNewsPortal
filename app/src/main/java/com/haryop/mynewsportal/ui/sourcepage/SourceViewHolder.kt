package com.haryop.synpulsefrontendchallenge.ui.companylist

import androidx.recyclerview.widget.RecyclerView
import com.haryop.mynewsportal.data.entities.CategoryEntity
import com.haryop.mynewsportal.data.entities.SourceEntity
import com.haryop.mynewsportal.databinding.ItemCategoryLayoutBinding
import com.haryop.mynewsportal.databinding.ItemSourceLayoutBinding

class SourceViewHolder(
    private val itemBinding: ItemSourceLayoutBinding,
    private val listener: SourceAdapter.SourceItemListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: SourceEntity) = with(itemBinding) {
        name.text = item.name
        name.text = item.url
        itemView.setOnClickListener { listener.onClickedSource(item.id) }
    }


}