package com.haryop.synpulsefrontendchallenge.ui.companylist

import androidx.recyclerview.widget.RecyclerView
import com.haryop.mynewsportal.data.entities.CategoryEntity
import com.haryop.mynewsportal.databinding.ItemCategoryLayoutBinding

class CategoryViewHolder(
    private val itemBinding: ItemCategoryLayoutBinding,
    private val listener: CategoryAdapter.CategoryItemListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: CategoryEntity) = with(itemBinding) {
        categoryTitle.text = item.title
        bgImage.setImageResource(item.image_id)
        itemView.setOnClickListener { listener.onClickedCategory(item.title) }
    }


}