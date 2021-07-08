package com.haryop.mynewsportal.ui.categorypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.haryop.mynewsportal.R
import com.haryop.mynewsportal.data.entities.CategoryEntity
import com.haryop.mynewsportal.databinding.FragmentCategoryPageBinding
import com.haryop.synpulsefrontendchallenge.ui.companylist.CategoryAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding

class CategoryPageFragment : BaseFragmentBinding<FragmentCategoryPageBinding>(),
    CategoryAdapter.CategoryItemListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryPageBinding
        get() = FragmentCategoryPageBinding::inflate

    lateinit var viewBinding: FragmentCategoryPageBinding
    override fun setupView(binding: FragmentCategoryPageBinding) {
        viewBinding = binding
        setUpCategory()
    }

    private lateinit var adapter: CategoryAdapter
    fun setUpCategory() = with(viewBinding) {
        adapter = CategoryAdapter(this@CategoryPageFragment)
        categoryRecyclerView.layoutManager = GridLayoutManager(context, 2)

        var category_names = resources.getStringArray(R.array.news_categories_array)
        var category_images = resources.obtainTypedArray(R.array.news_categories_images_array)
        var items = ArrayList<Any>()
        for (i in 0..(category_names.size - 1)) {
            val item = CategoryEntity(category_names[i], category_images.getResourceId(i, 0))
            items.add(item)
        }

        adapter.setItems(items)
        categoryRecyclerView.adapter = adapter

    }

    override fun onClickedCategory(category: String) {
        findNavController().navigate(
            R.id.action_category_to_source,
            bundleOf("category" to category)
        )

    }

}