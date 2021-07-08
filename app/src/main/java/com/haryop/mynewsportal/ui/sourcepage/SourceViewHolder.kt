package com.haryop.synpulsefrontendchallenge.ui.companylist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.haryop.mynewsportal.data.entities.CategoryEntity
import com.haryop.mynewsportal.data.entities.SourceEntity
import com.haryop.mynewsportal.databinding.ItemCategoryLayoutBinding
import com.haryop.mynewsportal.databinding.ItemSourceLayoutBinding
import okhttp3.internal.notifyAll

class SourceViewHolder(
    private val itemBinding: ItemSourceLayoutBinding,
    private val listener: SourceAdapter.SourceItemListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: SourceEntity, position:Int) = with(itemBinding) {
        name.text = item.name + " (" + item.language + ")"
        desc.text = item.description
        url.text = item.url

        if (item.isExpanded) {
            expandPart.visibility = View.VISIBLE
        } else {
            expandPart.visibility = View.GONE
        }

        itemView.setOnClickListener {
            setUpExpandPart(item)
            listener.onExpandItem(position)
        }
        readMore.setOnClickListener { listener.onClickedSource(item.id, item.name) }
    }

    fun setUpExpandPart(item: SourceEntity) = with(itemBinding) {
        if (item.isExpanded) {
            expandPart.visibility = View.GONE
            item.isExpanded = false
        } else {
            expandPart.visibility = View.VISIBLE
            item.isExpanded = true
        }
    }


}