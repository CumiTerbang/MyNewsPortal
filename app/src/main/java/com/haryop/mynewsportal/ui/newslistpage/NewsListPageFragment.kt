package com.haryop.mynewsportal.ui.newslistpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haryop.mynewsportal.data.entities.NewsListEntity
import com.haryop.mynewsportal.databinding.FragmentNewslistPageBinding
import com.haryop.mynewsportal.ui.ToolbarTitleListener
import com.haryop.mynewsportal.utils.Resource
import com.haryop.synpulsefrontendchallenge.ui.companylist.NewsListAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewsListPageFragment : BaseFragmentBinding<FragmentNewslistPageBinding>(),
    NewsListAdapter.NewsListItemListener, SwipeRefreshLayout.OnRefreshListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewslistPageBinding
        get() = FragmentNewslistPageBinding::inflate

    lateinit var viewbinding: FragmentNewslistPageBinding
    var category_name = ""
    var source_name = ""
    var source_id = ""
    override fun setupView(binding: FragmentNewslistPageBinding) {
        viewbinding = binding
        setUpRecyclerView()
        setupToolbar()
    }

    fun setupToolbar() {
        arguments?.getString("category")?.let {
            category_name = it
        }
        arguments?.getString("source_name")?.let {
            source_name = it
        }
        arguments?.getString("source_id")?.let {
            source_id = it
            viewModel.start(it)
            onGetSourcesObserver()
        }
        (activity as ToolbarTitleListener).onUpdateTitle(source_name, category_name, true)
    }

    private lateinit var adapter: NewsListAdapter
    fun setUpRecyclerView() = with(viewbinding) {
        adapter = NewsListAdapter(this@NewsListPageFragment)
        newslistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newslistRecyclerView.adapter = adapter

        newslistSwipeContainer.setOnRefreshListener(this@NewsListPageFragment)
    }

    private val viewModel: NewsListViewModel by viewModels()
    private fun onGetSourcesObserver() = with(viewbinding) {
        viewModel.getHeadlines.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    newslistSwipeContainer.isRefreshing = false
                    if (!it.data.isNullOrEmpty()) {
                        var items = ArrayList<Any>()
                        items.addAll(ArrayList(it.data))
                        items.add(adapter.ITEM_TYPE_BOTTOMSPACE_LAYOUT)
                        adapter.setItems(items)
                        adapter.notifyDataSetChanged()
                    } else {
                        Timber.e("getSearchEndpoint.observe: SUCCESS tapi null")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                Resource.Status.ERROR -> {
                    newslistSwipeContainer.isRefreshing = false
                    Timber.e("getSearchEndpoint.observe: error")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Timber.e("getSearchEndpoint.observe: LOADING")
                    newslistSwipeContainer.isRefreshing = true
                }
            }
        })
    }

    override fun onClickedItem(item: NewsListEntity) {
        comingSoon("\nNews Detail Page")
    }

    override fun onRefresh() {
        adapter.getItems().clear()
        adapter.notifyDataSetChanged()
        viewModel.start(source_id)
        onGetSourcesObserver()
    }


}