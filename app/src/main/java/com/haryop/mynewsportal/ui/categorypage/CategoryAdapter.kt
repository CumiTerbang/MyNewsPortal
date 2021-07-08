package com.haryop.synpulsefrontendchallenge.ui.companylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haryop.mynewsportal.data.entities.CategoryEntity
import com.haryop.mynewsportal.databinding.ItemCategoryLayoutBinding

class CategoryAdapter(private val listener: CategoryItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface CategoryItemListener {
        fun onClickedCategory(category: String)
    }

    private val items = ArrayList<Any>()

    fun setItems(items: ArrayList<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemCategoryLayoutBinding =
            ItemCategoryLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CategoryViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var mHolder = holder as CategoryViewHolder
        mHolder.bind(items[position] as CategoryEntity)
    }

}